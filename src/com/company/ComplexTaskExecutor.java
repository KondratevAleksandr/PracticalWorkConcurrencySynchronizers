package com.company;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ComplexTaskExecutor {
    private static final AtomicInteger TASK_GROUP_ID_GENERATOR = new AtomicInteger();

    public void executeTasks(int numberOfTasks) {
        int taskGroupId = TASK_GROUP_ID_GENERATOR.incrementAndGet();
        List<Integer> values = new CopyOnWriteArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfTasks);
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () -> {
            int sum = values.stream().reduce(0, Integer::sum);
            System.out.println("Complex tasks completed, values = " + values + ", sum = " + sum);
        });

        for (int i = 0; i < numberOfTasks; i++) {
            executor.submit(() -> {
                ComplexTask task = new ComplexTask(taskGroupId);
                values.add(task.execute());
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}