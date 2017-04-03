package main.monitoring_forkJoin_pool;

import java.util.concurrent.RecursiveAction;

/**
 * Created by G.Chalauri on 04/03/17.
 */
public class FJTask extends RecursiveAction {

    private int array[];

    private int start;
    private int end;

    public FJTask(int array[], int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    protected void compute() {
        if (end - start > 100) {
            int mid = (start + end) / 2;
            FJTask task1 = new FJTask(array, start, mid);
            FJTask task2 = new FJTask(array, mid, end);
            task1.fork();
            task2.fork();

            task1.join();
            task2.join();
        } else {
            for (int i = start; i < end; i++) {
                array[i]++;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
