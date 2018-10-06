package lab4.sleeping_barber;

public class Customer extends Thread {

    private String name;

    private WaitingRoom waitingRoom;

    public Customer(String name, WaitingRoom waitingRoom) {
        this.name = name;
        this.waitingRoom = waitingRoom;
    }

    public void run() {

        System.out.println(name + " is going to make a haircut");

        boolean gotAHaircut;
        try {
            gotAHaircut = waitingRoom.getAHaicut(this);
        } catch (InterruptedException e) {
            return;
        }

        if (gotAHaircut) {
            System.out.println(name + " got a haircut");
        } else {
            System.out.println(name + " have gone without haircut");
        }
    }

    public String toString() {
        return name;
    }
}
