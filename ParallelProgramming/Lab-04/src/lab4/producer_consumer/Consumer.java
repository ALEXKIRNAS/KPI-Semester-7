package lab4.producer_consumer;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer extends Thread {

    private static Random random = new Random(System.currentTimeMillis());
    private String name;
    private LinkedList<String> queue;
    private ReentrantLock locker;

    Consumer(String name, LinkedList<String> queue, ReentrantLock locker) {
        this.name = name;
        this.queue = queue;
        this.locker = locker;
    }

    public void run() {
        while (!isInterrupted()) {
            String string;

            locker.lock();
            string = queue.pollFirst();
            locker.unlock();

            if (string == null) {
                continue;
            }

            System.out.println(name + " consuming: " + string);

            try {
                sleep(random.nextInt(700) + 700);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
