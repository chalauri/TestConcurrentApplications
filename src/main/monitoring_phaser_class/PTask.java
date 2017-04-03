package main.monitoring_phaser_class;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Created by G.Chalauri on 04/03/17.
 */
public class PTask implements Runnable {

    private int time;
    private Phaser phaser;

    public PTask(int time, Phaser phaser) {
        this.time = time;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        phaser.arrive();
        System.out.printf("%s: Entering phase 1.\n", Thread.
                currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: Finishing phase 1.\n", Thread.
                currentThread().getName());
        phaser.arriveAndAwaitAdvance();

        System.out.printf("%s: Entering phase 2.\n", Thread.
                currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: Finishing phase 2.\n", Thread.
                currentThread().getName());
        phaser.arriveAndAwaitAdvance();


        System.out.printf("%s: Entering phase 3.\n", Thread.
                currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s: Finishing phase 3.\n", Thread.
                currentThread().getName());
        phaser.arriveAndDeregister();
    }
}
