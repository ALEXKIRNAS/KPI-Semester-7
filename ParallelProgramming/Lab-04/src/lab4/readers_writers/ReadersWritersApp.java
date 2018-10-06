package lab4.readers_writers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReadersWritersApp {

    private static Random random = new Random(System.currentTimeMillis());

    public void modelReadersWriters() throws InterruptedException {

        Storage storage = new Storage();

        List<Writer> writers = new ArrayList<>();

        for (int i = 0; i < 5; ++i) {
            writers.add(new Writer("Writer-" + (i + 1), storage));
        }

        for (Writer writer : writers) {
            writer.start();
        }

        for (int i = 0; i < 300; ++i) {
                new Reader("Reader-" + (i + 1), storage).start();

            Thread.sleep(10);
        }
    }
}
