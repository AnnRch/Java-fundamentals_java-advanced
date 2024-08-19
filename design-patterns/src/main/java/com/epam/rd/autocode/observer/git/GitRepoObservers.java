package com.epam.rd.autocode.observer.git;

import java.util.*;

public class GitRepoObservers {
    public static Repository newRepository(){

        //throw new UnsupportedOperationException();
        return new Repository() {

            List<WebHook> observsers = new ArrayList<>();
            List<Commit> commits = new ArrayList<>();
            Map<String, List<Commit>> commitMap = new HashMap<>();

            @Override
            public void addWebHook(WebHook webHook) {
                observsers.add(webHook);
            }

            private void aggregateToMap(Map<String, List<Commit>> map,String key, Commit valueItem){
                List<Commit> values = map.getOrDefault(key, new ArrayList<>());
                values.add(valueItem);
                map.put(key, values);
            }

            @Override
            public Commit commit(String branch, String author, String[] changes) {
                Commit currentCommit = new Commit(author, changes);
                for (WebHook webHook : observsers) {
                    if(webHook.type().equals(Event.Type.COMMIT) && Objects.equals(webHook.branch(), branch)){
                        commits.add(currentCommit);

                        aggregateToMap(commitMap, branch, currentCommit);

//                        List<Commit> actualCommits = commits.stream()
//                                        .filter(c -> c.author().equals(author) && Arrays.equals(c.changes(), changes))
//                                                .toList();

                        List<Commit> actualCommits = new ArrayList<>();
                        commits.forEach(commit -> {
                            if(commit.author().equals(author) && Arrays.equals(commit.changes(), changes)){
                                actualCommits.add(commit);
                            }
                        });

                        webHook.onEvent(new Event(Event.Type.COMMIT, branch, actualCommits));
                    }
                }
                return currentCommit;
            }

            @Override
            public void merge(String sourceBranch, String targetBranch) {

                for(WebHook webHook : observsers){
                    if(webHook.type().equals(Event.Type.MERGE) && webHook.branch().equals(targetBranch)){

                        List<Commit> commitsList = commitMap.entrySet().stream()
                                        .filter(entry -> entry.getKey().equals(sourceBranch))
                                        .map(Map.Entry::getValue)
                                        .reduce(new ArrayList<>(),(acc, value) -> {
                                            acc.addAll(value);
                                            return acc;
                                        });

                        webHook.onEvent(
                                new Event(Event.Type.MERGE,
                                targetBranch,
                                       commitsList
                                )
                        );
                    }
                }
            }

        };
    }

    public static WebHook mergeToBranchWebHook(String branchName){

        //throw new UnsupportedOperationException();
        return new WebHook() {

            List<Event> events = new ArrayList<>();


            @Override
            public String branch() {
                return branchName;
            }

            @Override
            public Event.Type type() {
                return Event.Type.MERGE;
            }

            @Override
            public List<Event> caughtEvents() {
                return events;
            }

            @Override
            public void onEvent(Event event) {
                if(event.type().equals(Event.Type.MERGE) && event.branch().equals(branch())){
                    if(!events.contains(event)){
                        events.add(event);
                    }
                }
            }
        };
    }

    public static WebHook commitToBranchWebHook(String branchName){

        //throw new UnsupportedOperationException();
        return new WebHook() {

            List<Event> events = new ArrayList<>();

            @Override
            public String branch() {
                return branchName;
            }

            @Override
            public Event.Type type() {
                return Event.Type.COMMIT;
            }

            @Override
            public List<Event> caughtEvents() {
                return events;
            }

            @Override
            public void onEvent(Event event) {
                if(event.type().equals(type()) && event.branch().equals(branchName)){
                    events.add(event);
                }
            }
        };
    }


}
