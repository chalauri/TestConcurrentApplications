package main.monitoring_executor_framework;

import java.util.concurrent.TimeUnit;

/**
 * Created by G.Chalauri on 04/03/17.
 */
public class ETask implements Runnable {

    private long milliseconds;

    public ETask(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    @Override
    public void run() {
        System.out.printf("%s: Begin\n", Thread.currentThread().getName());
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: End\n", Thread.currentThread().getName());
    }
}
