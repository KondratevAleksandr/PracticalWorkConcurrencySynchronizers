package com.company;

import java.util.concurrent.ThreadLocalRandom;

public class ComplexTask {
    private final int taskGroupId;

    public ComplexTask(int taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    private static final int MAX_RANGE = 100;

    public int execute() {
        int random = ThreadLocalRandom.current().nextInt(0, MAX_RANGE);
        System.out.println("Task executed, task groupId = " + taskGroupId + " +  value =  " + random);
        return random;
    }
}
