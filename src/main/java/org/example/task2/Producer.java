package org.example.task2;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    private final Random random = new Random();
    private int[] importantInfo;

    public Producer(Drop drop, int arrSize) {
        this.drop = drop;
        initArray(arrSize);
    }

    public void run() {
        for (int j : importantInfo) {
            drop.put(j);
            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {
                System.err.println("Producer interrupted - " + e);
            }
        }
        drop.put("DONE");
    }

    private void initArray(int size) {
        importantInfo = new int[size];
        for(int i=0; i<size; i++) {
            importantInfo[i] = i;
        }
    }
}
