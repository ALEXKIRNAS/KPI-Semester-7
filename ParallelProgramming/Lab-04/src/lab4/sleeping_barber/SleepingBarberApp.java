package lab4.sleeping_barber;

import java.util.ArrayList;
import java.util.List;

public class SleepingBarberApp {

    public void modelSleepingBarberApp() throws InterruptedException {

        WaitingRoom waitingRoom = new WaitingRoom();

        Barber barber = new Barber(waitingRoom);
        barber.start();

        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            Customer customer = new Customer("Customer-" + (i + 1), waitingRoom);
            customer.start();
            customers.add(customer);
            Thread.sleep(100);
        }

        for (Customer customer : customers) {
            customer.join();
        }

        barber.interrupt();
    }
}
