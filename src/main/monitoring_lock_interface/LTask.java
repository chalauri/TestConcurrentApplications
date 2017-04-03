package main.monitoring_lock_interface;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * Created by G.Chalauri on 04/03/17.
 */
public class LTask implements Runnable {

    private Lock lock;

    public LTask(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            System.out.printf("%s: Get the Lock.\n", Thread.
                    currentThread().getName());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.printf("%s: Free the Lock.\n", Thread.
                        currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }
}
