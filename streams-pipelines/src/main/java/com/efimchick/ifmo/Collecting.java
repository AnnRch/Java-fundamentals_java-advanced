package com.efimchick.ifmo;


import com.efimchick.ifmo.util.CourseResult;
import com.efimchick.ifmo.util.Person;

import javax.print.DocFlavor;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


public class Collecting {

    private SortedSet<String> allTasks = new TreeSet<>();
    private Map<String,Double> taskToScore = new LinkedHashMap<>();
    private List<String> tasksNames;
    private final List<CourseResult> courseResults = new ArrayList<>();
    private List<CourseResult> sortedCourseResults = new ArrayList<>();
    private Map<Person,Double> totalScores = new LinkedHashMap<>();
    private final Map<String,Double> tasksScores = new LinkedHashMap<>();;
    private final Set<Person> persons =  new HashSet<>();
    private final Map<String, Integer> headerToMaxWidth = new HashMap<>();
    private Double averageTotalScore = 0.0;
    private final Map<Person,String> personToMark = new LinkedHashMap<>();


    public Collecting(){}

    public int sum(IntStream intStream) {
        return intStream.reduce(0, Integer::sum);
    }

    public int production(IntStream intStream) {
            return intStream.reduce(1, (acc, i) -> acc * i);
    }

    public int oddSum(IntStream intStream) {
        return intStream
                .filter(i -> Math.abs(i) % 2 == 1)
                .reduce(0, Integer::sum);
    }

    public Map<Integer, Integer> sumByRemainder(int divider, IntStream intStream) {

        Map<Integer, Integer> map = new HashMap<>();
        intStream.forEach(i -> map.put(
                                            i % divider,
                                            map.getOrDefault(i % divider, 0) + i)
                                        );

        return map;
    }

    public Map<Person, Double> totalScores(Stream<CourseResult> results) {
        Map<Person, Double> scores = new LinkedHashMap<>();
        results.forEach(result -> {
            double sum = result.getTaskResults()
                    .values()
                    .stream()
                    .mapToDouble(Integer::intValue)
                    .sum();

            allTasks.addAll(result.getTaskResults().keySet());
            scores.put(result.getPerson(), sum);
        });

        this.totalScores = scores
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new
                ));

    return  scores.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() / allTasks.size()));

    }

    public double averageTotalScore(Stream<CourseResult> results) {
        return totalScores(results)
                .values()
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public Map<String, Double> averageScoresPerTask(Stream<CourseResult> results) {

        Map<String,Integer> taskPerScore = new LinkedHashMap<>();
        Set<Person> persons = new HashSet<>();
        results.forEach(result -> {
            result.getTaskResults().forEach((key, value) -> {
                taskPerScore.put(key.trim(), taskPerScore.getOrDefault(key.trim(), 0) + value);
            });
            persons.add(result.getPerson());
        });


        this.taskToScore =  taskPerScore
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double)entry.getValue() / persons.size())
                );
        return taskToScore;
    }

    private String defineMarkByScore(Double score){

        Map<String, List<Integer>> markMap = new TreeMap<>();
        markMap.put("A",IntStream.rangeClosed(90,100).boxed().collect(toList()));
        markMap.put("B",IntStream.range(83,90).boxed().collect(toList()));
        markMap.put("C",IntStream.range(75,83).boxed().collect(toList()));
        markMap.put("D",IntStream.range(68,75).boxed().collect(toList()));
        markMap.put("E",IntStream.range(60,68).boxed().collect(toList()));
        markMap.put("F",IntStream.range(0,60).boxed().collect(toList()));


        return markMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(score.intValue()))
                .map(Map.Entry::getKey)
                .reduce((acc,item) -> item)
                .orElse("X");
    }

    public Map<Person, String> defineMarks(Stream<CourseResult> results) {
        Map<Person, String> resMap = new HashMap<>();
        totalScores(results).forEach((key, value) ->
                                        resMap.put(key,defineMarkByScore(value)));
        return resMap;
    }


    public String easiestTask(Stream<CourseResult> results) {
      Double highestScore = averageScoresPerTask(results)
                            .values()
                            .stream()
                            .mapToDouble(Double::doubleValue)
                            .max()
                            .orElse(0.0);

      return this.taskToScore
                    .entrySet()
                    .stream()
                    .filter(entry ->  (highestScore - entry.getValue()) <= 0.0001)
                    .map(entry -> entry.getKey().trim())
                    .reduce((acc, key) -> key)
                    .orElse("");
    }

    public Collector<CourseResult, ?, String> printableStringCollector() {
        return Collector.of(
                StringBuilder::new,
                (acc, res) -> acc.append(line(res)),
                StringBuilder::append,
                str -> table().toString()
        );
    }

    private StringBuilder line(CourseResult res){
        this.allTasks.addAll(res.getTaskResults().keySet());
        this.courseResults.add(res);
        return new StringBuilder();
    }

    private int getMaxColumnValue(List<String> values){
        return values.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }
    private void mapHeaderToMaxWidth(List<String> values, String title){
        this.headerToMaxWidth.put(title,getMaxColumnValue(values));
    }

    private String formatLeft(String value, String title){
        int maxWidth = this.headerToMaxWidth.get(title);
        int offset = maxWidth - value.length();
        return value.concat(" ".repeat(offset)).concat(" |");
    }

    private String formatRight(String value, String title){
        int maxWidth = this.headerToMaxWidth.get(title);
        int offset = maxWidth - value.length() - 1;
        return " ".repeat(offset).concat(value).concat(" |");
    }

    private String formatCenter(String value, String title){
        int maxWidth = this.headerToMaxWidth.get(title);
        int offset = maxWidth - value.length();
        return " ".repeat(offset).concat(value).concat(" ".repeat(offset)).concat("|");
    }

    private void studColumnToMap(){
        List<String> studColumn  = new ArrayList<>();
        studColumn.add("Student");
        this.persons.forEach(person -> {
            studColumn.add(person.getFirstName() + " " + person.getLastName());
        });
        studColumn.add("Average");
        mapHeaderToMaxWidth(studColumn,"Student");
    }

    private void tasksColumnsToMap(){
        this.tasksScores.forEach((key, value) -> {
            List<String> taskColumn  = new ArrayList<>();
            taskColumn.add(key);
            this.courseResults.forEach(res -> {
                taskColumn.add(Integer.toString(res.getTaskResults().get(key)));
            });
            taskColumn.add(String.format("%.2f",this.tasksScores.get(key)));
            mapHeaderToMaxWidth(taskColumn,key);
        });
    }

    private void totalColumnToMap(){
        List<String> totalColumn  = new ArrayList<>();
        totalColumn.add("Total");
        this.totalScores.forEach((key, value) -> {
            totalColumn.add(String.format("%.2f", value));
        });
        totalColumn.add(String.format("%.2f",this.averageTotalScore));
        mapHeaderToMaxWidth(totalColumn,"Total");
    }

    private void markColumnToMap(){
        List<String> markColumn  = new ArrayList<>();
        markColumn.add(" Mark ");
        this.personToMark.forEach((key, value) -> markColumn.add(value));
        markColumn.add(defineMarkByScore(this.averageTotalScore));
        mapHeaderToMaxWidth(markColumn," Mark ");
    }

    private void preProcessTable(){
        List<CourseResult> results = new ArrayList<>();
        Map<String,Integer> tasks = this.allTasks
                                        .stream()
                                        .collect(Collectors.toMap(entry -> entry, entry -> 0));
        this.courseResults.forEach(res -> {

            //this.persons ->
            this.persons.add(res.getPerson());

            CourseResult courseResult = new CourseResult(res.getPerson(), tasks);
            res.getTaskResults().forEach((task, score) -> {
                courseResult.getTaskResults().put(task, score);
            });
                results.add(courseResult);

            //this.totalScores
            Double totalScore = courseResult
                                    .getTaskResults()
                                    .values()
                                    .stream()
                                    .mapToDouble(Integer::intValue)
                                    .average()
                                    .orElse(0.0);
             this.totalScores.put(courseResult.getPerson(), totalScore);

             //this personToMark
             this.personToMark.put(courseResult.getPerson(),defineMarkByScore(totalScore));

        });

        this.courseResults.clear();
        this.courseResults.addAll(results);

        //this.taskScores ->

        this.allTasks.forEach(task -> {
            this.courseResults.forEach(res -> {
                double avgTaskScore = res.getTaskResults()
                                            .entrySet()
                                            .stream()
                                            .filter(entry -> Objects.equals(entry.getKey(), task))
                                            .mapToDouble(Map.Entry::getValue)
                                            .average()
                                            .orElse(0.0);

                this.tasksScores.put(
                        task,
                        this.tasksScores.getOrDefault(task,0.0) + avgTaskScore / this.persons.size()
                );
            });
        });

        //this.averageTotalScore ->
        this.averageTotalScore = this.totalScores
                                    .values()
                                    .stream()
                                    .mapToDouble(Double::doubleValue)
                                    .average()
                                    .orElse(0.0);

    }

    private StringBuilder table(){

        preProcessTable();

        studColumnToMap();
        tasksColumnsToMap();
        totalColumnToMap();
        markColumnToMap();

        //header line
        StringBuilder line1 = new StringBuilder(formatLeft("Student","Student"));
        this.tasksNames = new ArrayList<>(allTasks);
//
        tasksNames.sort(Comparator.naturalOrder());
//
        this.tasksNames.forEach(task -> {
            line1.append(formatCenter(task,task));
        });

        line1.append(" Total |");
        line1.append(formatCenter(" Mark "," Mark "));
        line1.append("\n");

        //sort course results by person last name
        courseResults.sort(Comparator.comparing(o -> o.getPerson().getLastName()));

       courseResults.forEach(res -> {
        LinkedHashMap<String,Integer> sortedMap = res.getTaskResults().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

           sortedCourseResults.add(new CourseResult(res.getPerson(), sortedMap));
       });

//
//        //content lines
        StringBuilder line2 = new StringBuilder();
        this.sortedCourseResults.forEach(res -> {
            line2.append(formatLeft(
                                res.getPerson().getLastName() + " " + res.getPerson().getFirstName(),
                            "Student"));

            res.getTaskResults().forEach((key,value) -> {
                line2.append(formatRight(Integer.toString(value),key));
            });
            Double totalScore = this.totalScores.get(res.getPerson());
            line2.append(" ").append(String.format("%.2f",totalScore)).append(" |");
            line2.append(formatRight(defineMarkByScore(totalScore)," Mark "));
            line2.append("\n");
        });
//
//
//        //footer line
        StringBuilder line3 = new StringBuilder(formatLeft("Average","Student"));
        this.tasksScores.forEach((key,value) -> {
            line3.append(formatRight(String.format("%.2f",value),key));
        });

        Double avgTotalScore = this.tasksScores
                                    .values()
                                    .stream()
                                    .mapToDouble(Double::doubleValue)
                                    .average()
                                    .orElse(0.0);

        line3.append(" ").append(String.format("%.2f",avgTotalScore)).append(" |");
        line3.append(formatRight(defineMarkByScore(avgTotalScore)," Mark "));

        return line1.append(line2).append(line3);
    }
}

