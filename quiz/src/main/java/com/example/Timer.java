package com.example;

public class Timer {
    private long startTime;
    private int durationInSeconds;

    public Timer(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public int getRemainingTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = (currentTime - this.startTime) / 1000;
        System.out.println("Elapsed time: "+ elapsedTime);
        return (int) (this.durationInSeconds - elapsedTime);
    }

    public boolean isTimeUp() {
        return getRemainingTime() <= 0;
    }
}
