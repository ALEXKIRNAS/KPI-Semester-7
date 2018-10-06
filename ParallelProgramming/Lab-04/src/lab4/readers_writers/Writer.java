package lab4.readers_writers;

public class Writer extends Thread {

    private String name;

    private Storage storage;

    Writer(String name, Storage storage) {
        this.name = name;
        this.storage = storage;
    }

    public void run() {

        for (int i = 0; i < 10; ++i) {

            String string = name + "-string-" + (i + 1);
            System.out.println(name + " is going to write: "  + string);

            try {
                storage.write(string);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(name + " wrote: " + string);

            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
