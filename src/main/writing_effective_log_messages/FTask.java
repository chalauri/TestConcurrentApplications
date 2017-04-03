package main.writing_effective_log_messages;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by G.Chalauri on 04/03/17.
 */
public class FTask implements Runnable {


    @Override
    public void run() {
        Logger logger = MyLogger.getLogger(this.getClass().getName());
        logger.entering(Thread.currentThread().getName(), "run()");

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.exiting(Thread.currentThread().getName(), "run()", Thread.currentThread());
    }
}
