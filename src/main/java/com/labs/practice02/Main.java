package com.labs.practice02;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        DataUpdated d = new DataUpdated();

        WorkerUpdated w1 = new WorkerUpdated(2, d, 2);
        WorkerUpdated w2 = new WorkerUpdated(1, d, 1);

        w1.join();
        w2.join();
        System.out.println("end of main...");




    }
}
