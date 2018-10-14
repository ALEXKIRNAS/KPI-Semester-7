package lab4.producer_consumer;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Producer extends Thread {

    private static Random random = new Random(System.currentTimeMillis());
    private String name;
    private Queue queue;

    Producer(String name,  Queue queue) {
        this.name = name;
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 10; ++i) {

            String producedString = name + "-" + "String-" + (i + 1);
            System.out.println(name + " produced: " + producedString);

            try {
                queue.push(producedString);
                sleep(random.nextInt(300) + 300);
            } catch (InterruptedException e) {
                break;
            }
        }

    }
}
