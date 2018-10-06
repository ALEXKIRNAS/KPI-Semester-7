package lab4.producer_consumer;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Producer extends Thread {

    private static Random random = new Random(System.currentTimeMillis());
    private String name;
    private LinkedList<String> queue;
    private ReentrantLock locker;

    Producer(String name,  LinkedList<String> queue, ReentrantLock locker) {
        this.name = name;
        this.queue = queue;
        this.locker = locker;
    }

    public void run() {
        for (int i = 0; i < 10; ++i) {

            locker.lock();
            String producedString = name + "-" + "String-" + (i + 1);
            System.out.println(name + " produced: " + producedString);
            queue.push(producedString);
            locker.unlock();

            try {
                sleep(random.nextInt(300) + 300);
            } catch (InterruptedException e) {
                break;
            }
        }

    }
}
