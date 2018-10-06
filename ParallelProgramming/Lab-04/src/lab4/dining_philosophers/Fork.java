package lab4.dining_philosophers;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private ReentrantLock lock = new ReentrantLock();
    private String name;
    private volatile boolean free;

    Fork(String name) {
        this.name = name;
        free = true;
    }

    boolean take(boolean look) {
        boolean status = false;
        if (free || look) {
            lock.lock();
            free = false;
            status = true;
        }

        return status;
    }

    void put() {
        lock.unlock();
        free = true;
    }

    public String toString() {
        return name;
    }

}
