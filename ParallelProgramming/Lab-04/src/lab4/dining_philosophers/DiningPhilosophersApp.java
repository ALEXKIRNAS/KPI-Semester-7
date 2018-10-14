package lab4.dining_philosophers;

import java.util.ArrayList;

public class DiningPhilosophersApp {

    public void modelDiningPhilosophers() throws InterruptedException {

        ArrayList<Fork> forks = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            forks.add(new Fork("Fork-" + (i + 1)));
        }

        ArrayList<Philosopher> philosophers = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            philosophers.add(new Philosopher("Philosopher-" + (i + 1) ,forks.get(i), forks.get(i + 1)));
        }

        philosophers.add(new Philosopher("Philosopher-5", forks.get(0),forks.get(4)));

        for (Philosopher philosopher : philosophers) {
            philosopher.start();
        }

        for (Philosopher philosopher : philosophers) {
            philosopher.join();
        }
    }

}
