package com.labs.practice02;

public class WorkerUpdated extends Thread {

    private int id;
    private DataUpdated data;
    private int state;


    public WorkerUpdated(int id, DataUpdated d, int state) {
        this.id = id;
        this.data = d;
        this.state = state;
        this.start();
    }


    @Override
    public void run() {

        for (int i = 0; i < 5; i++) {
            try {
                synchronized (data) {
                    data.wait();
                }
                if (state == 1) { data.Tic();   }
                else if (state == 2) { data.Tak(); }
                else if (state == 3) { data.Toe(); }
                synchronized (data) {
                    data.notify();
                }

            } catch (InterruptedException e) { throw new RuntimeException(e); }
        }
    }

}
