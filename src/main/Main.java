package main;

import main.monitoring_executor_framework.ETask;
import main.monitoring_forkJoin_pool.FJTask;
import main.monitoring_lock_interface.LTask;
import main.monitoring_lock_interface.MyLock;
import main.monitoring_phaser_class.PTask;
import main.writing_effective_log_messages.FTask;
import main.writing_effective_log_messages.MyLogger;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by G.Chalauri on 04/03/17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // monitoringLockInterfaceExample();
        // monitoringPhaserClassExample();
        // monitoringExecutorFramework();
        // monitoringForkJoinPoolExample();
        // writingEffectiveLogMessagesExample(args);
    }

    private static void writingEffectiveLogMessagesExample(String[] args) {
        Logger logger = MyLogger.getLogger("Core");
        logger.entering("Core", "main()", args);
        Thread threads[] = new Thread[5];

        for (int i = 0; i < threads.length; i++) {
            logger.log(Level.INFO, "Launching thread: " + i);
            FTask task = new FTask();
            threads[i] = new Thread(task);
            logger.log(Level.INFO, "Thread created: " + threads[i].getName());
            threads[i].start();
        }

        logger.log(Level.INFO, "Five Threads created." + "Waiting for its finalization");

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
                logger.log(Level.INFO, "Thread has finished its execution", threads[i]);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Exception", e);
            }
        }

        logger.exiting("Core", "main()");
    }

    private static void monitoringForkJoinPoolExample() throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        int array[] = new int[10000];
        FJTask task1 = new FJTask(array, 0, array.length);
        pool.execute(task1);

        while (!task1.isDone()) {
            showLog(pool);
            TimeUnit.SECONDS.sleep(1);
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);

        showLog(pool);
        System.out.printf("Main: End of the program.\n");
    }

    private static void monitoringExecutorFramework() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            ETask task = new ETask(random.nextInt(10000));
            executor.submit(task);
        }

        for (int i = 0; i < 5; i++) {
            showLog(executor);
            TimeUnit.SECONDS.sleep(1);
        }

        executor.shutdown();

        for (int i = 0; i < 5; i++) {
            showLog(executor);
            TimeUnit.SECONDS.sleep(1);
        }

        executor.awaitTermination(1, TimeUnit.DAYS);

        System.out.printf("Main: End of the program.\n");
    }

    private static void monitoringPhaserClassExample() throws InterruptedException {
        Phaser phaser = new Phaser(3);
        for (int i = 0; i < 3; i++) {
            PTask task = new PTask(i + 1, phaser);
            Thread thread = new Thread(task);
            thread.start();
        }

        for (int i = 0; i < 10; i++) {
            System.out.printf("********************\n");
            System.out.printf("Main: Phaser Log\n");
            System.out.printf("Main: Phaser: Phase: %d\n", phaser.getPhase());
            System.out.printf("Main: Phaser: Registered Parties: %d\n", phaser.getRegisteredParties());
            System.out.printf("Main: Phaser: Arrived Parties: %d\n", phaser.getArrivedParties());
            System.out.printf("Main: Phaser: Unarrived Parties: %d\n", phaser.getUnarrivedParties());
            System.out.printf("********************\n");
            TimeUnit.SECONDS.sleep(3);
        }

    }


    private static void monitoringLockInterfaceExample() throws InterruptedException {
        MyLock lock = new MyLock();

        Thread threads[] = new Thread[5];


        for (int i = 0; i < 5; i++) {
            LTask task = new LTask(lock);
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (int i = 0; i < 15; i++) {
            System.out.printf("Main: Logging the Lock\n");
            System.out.printf("************************\n");
            System.out.printf("Lock: Owner : %s\n", lock.getOwnerName());
            System.out.printf("Lock: Queued Threads: %s\n", lock.hasQueuedThreads());
            if (lock.hasQueuedThreads()) {
                System.out.printf("Lock: Queue Length: %d\n", lock.getQueueLength());
                System.out.printf("Lock: Queued Threads: ");
                Collection<Thread> lockedThreads = lock.getThreads();
                for (Thread lockedThread : lockedThreads) {
                    System.out.printf("%s ", lockedThread.getName());
                }
                System.out.printf("\n");
            }

            System.out.printf("Lock: Fairness: %s\n", lock.isFair());
            System.out.printf("Lock: Locked: %s\n", lock.isLocked());
            System.out.printf("************************\n");

            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void showLog(ThreadPoolExecutor executor) {
        System.out.printf("*********************");
        System.out.printf("Main: Executor Log");
        System.out.printf("Main: Executor: Core Pool Size: %d\n", executor.getCorePoolSize());
        System.out.printf("Main: Executor: Pool Size: %d\n", executor.getPoolSize());
        System.out.printf("Main: Executor: Active Count: %d\n", executor.getActiveCount());
        System.out.printf("Main: Executor: Task Count: %d\n", executor.getTaskCount());
        System.out.printf("Main: Executor: Completed Task Count: %d\n", executor.getCompletedTaskCount());
        System.out.printf("Main: Executor: Shutdown: %s\n", executor.isShutdown());
        System.out.printf("Main: Executor: Terminating: %s\n", executor.isTerminating());
        System.out.printf("Main: Executor: Terminated: %s\n", executor.isTerminated());
        System.out.printf("*********************\n");
    }

    private static void showLog(ForkJoinPool pool) {
        System.out.printf("**********************\n");
        System.out.printf("Main: Fork/Join Pool log\n");
        System.out.printf("Main: Fork/Join Pool: Parallelism: %d\n", pool.getParallelism());
        System.out.printf("Main: Fork/Join Pool: Pool Size: %d\n", pool.getPoolSize());
        System.out.printf("Main: Fork/Join Pool: Active Thread Count: %d\n", pool.getActiveThreadCount());
        System.out.printf("Main: Fork/Join Pool: Running Thread Count: %d\n", pool.getRunningThreadCount());
        System.out.printf("Main: Fork/Join Pool: Queued Submission: %d\n", pool.getQueuedSubmissionCount());
        System.out.printf("Main: Fork/Join Pool: Queued Tasks: %d\n", pool.getQueuedTaskCount());
        System.out.printf("Main: Fork/Join Pool: Queued Submissions: %s\n", pool.hasQueuedSubmissions());
        System.out.printf("Main: Fork/Join Pool: Steal Count: %d\n", pool.getStealCount());
        System.out.printf("Main: Fork/Join Pool: Terminated : %s\n", pool.isTerminated());
        System.out.printf("**********************\n");
    }
}
