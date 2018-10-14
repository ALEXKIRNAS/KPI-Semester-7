package lab4.producer_consumer;

import java.util.Random;

public class Consumer extends Thread {

    private static Random random = new Random(System.currentTimeMillis());
    private String name;
    private Queue queue;

    Consumer(String name, Queue queue) {
        this.name = name;
        this.queue = queue;
    }

    public void run() {
        while (!isInterrupted()) {
            String string;
            try {
                string = queue.pollFirst();
                System.out.println(name + " consuming: " + string);
                sleep(random.nextInt(700) + 700);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
