package lab4.sleeping_barber;

public class Barber extends Thread {

    private WaitingRoom waitingRoom;

    Barber(WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    public void run() {

        while (!interrupted()) {
            Customer customerForHaircut;
            try {
                customerForHaircut = waitingRoom.getCustomerForHaircut();
            } catch (InterruptedException e) {
                break;
            }

            System.out.println("Doing a haircut for " + customerForHaircut);

            try {
                sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
