package com.efimchick.ifmo.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class CourseResult {
    private final Person person;
    private final Map<String, Integer> taskResults;

    public CourseResult(final Person person, final Map<String, Integer> taskResults) {
        this.person = person;
        this.taskResults = formatTask(taskResults);
    }

   private Map<String,Integer> formatTask(final Map<String,Integer> taskResults) {
        Map<String, Integer> formattedTaskResults = new LinkedHashMap<>();
        taskResults.forEach((k, v) -> {
            formattedTaskResults.put(" "+k.trim() + " ",v);
        });
        return formattedTaskResults;
   }

    public Person getPerson() {
        return person;
    }

    public Map<String, Integer> getTaskResults() {
        return taskResults;
    }
}
