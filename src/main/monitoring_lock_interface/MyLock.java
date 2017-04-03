package main.monitoring_lock_interface;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by G.Chalauri on 04/03/17.
 */
public class MyLock extends ReentrantLock {

    public String getOwnerName() {
        if (this.getOwner() == null) {
            return "NONE";
        }

        return this.getOwner().getName();
    }

    public Collection<Thread> getThreads() {
        return this.getQueuedThreads();
    }

}
