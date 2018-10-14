package lab4;

import lab4.dining_philosophers.DiningPhilosophersApp;
import lab4.producer_consumer.ProducerConsumerApp;
import lab4.readers_writers.ReadersWritersApp;
import lab4.sleeping_barber.SleepingBarberApp;

public class App {

    public static void main(String[] args) throws InterruptedException {

//        System.out.println("PRODUCERS-CONSUMERS");
//        ProducerConsumerApp producerConsumerApp = new ProducerConsumerApp();
//        producerConsumerApp.modelProducesConsumer();
//        System.out.println();

//        System.out.println("READERS-WRITERS");
//        ReadersWritersApp readersWritersApp = new ReadersWritersApp();
//        readersWritersApp.modelReadersWriters();
//        System.out.println();
//
        System.out.println("DINING PHILOSOPHERS");
        DiningPhilosophersApp diningPhilosophersApp = new DiningPhilosophersApp();
        diningPhilosophersApp.modelDiningPhilosophers();
        System.out.println();

//        System.out.println("SLEEPING BARBER");
//        SleepingBarberApp sleepingBarberApp = new SleepingBarberApp();
//        sleepingBarberApp.modelSleepingBarberApp();
    }
}
