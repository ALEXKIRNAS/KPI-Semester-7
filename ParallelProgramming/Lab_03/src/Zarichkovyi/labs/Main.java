package Zarichkovyi.labs;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


public class Main {
    private static final int SEQUENCE_LENGTH = 17;

    public static void main(String[] args) {
        Random random = new Random(0xCAFFE);

        int[] sequence = new int[SEQUENCE_LENGTH];
        System.out.print("Generated sequence: ");
        for (int i = 0; i < SEQUENCE_LENGTH; ++i) {
            sequence[i] = random.nextInt(17);
            System.out.print(sequence[i] + " ");
        }
        System.out.println();
        System.out.println("- - - - - - - - - - - - -");

        System.out.println("Number of elements greater than 7 is " + CountElemByCond(sequence));

        Pair<Integer, Integer> minMaxIndexes = GetMinMaxIndexes(sequence);
        System.out.println("Min sequence value is " + sequence[minMaxIndexes.first] + " at index " + minMaxIndexes.first);
        System.out.println("Max sequence value is " + sequence[minMaxIndexes.second] + " at index " + minMaxIndexes.second);

        System.out.println("Control Hash Sum of sequence is " + GetControlHashSum(sequence));
    }

    private static boolean cond(int x) {
        return x > 7;
    }

    private static int CountElemByCond(int[] sequence) {
        AtomicInteger counter = new AtomicInteger();

        IntStream.of(sequence).parallel().forEach(x -> {
            if (cond(x)) {
                int oldValue;
                int newValue;

                do {
                    oldValue = counter.get();
                    newValue = oldValue + 1;
                } while (!counter.compareAndSet(oldValue, newValue));
            }
        });

        return counter.get();
    }

    private static Pair<Integer, Integer> GetMinMaxIndexes(int[] sequence) {
        AtomicInteger minIndex = new AtomicInteger();
        AtomicInteger maxIndex = new AtomicInteger();

        IntStream.range(0, sequence.length).parallel().forEach(i -> {
            int oldValue;
            int newValue;

            do {
                oldValue = minIndex.get();
                newValue = i;
            } while (((sequence[i] < sequence[oldValue]) || (sequence[i] == sequence[oldValue] && i < oldValue))
                       && !minIndex.compareAndSet(oldValue, newValue));

            do {
                oldValue = maxIndex.get();
                newValue = i;
            } while (((sequence[i] > sequence[oldValue]) || (sequence[i] == sequence[oldValue] && i < oldValue))
                       && !maxIndex.compareAndSet(oldValue, newValue));
        });

       return new Pair<>(minIndex.get(), maxIndex.get());
    }

    private static int GetControlHashSum(int[] sequence) {
        AtomicInteger controlHashSum = new AtomicInteger();

        IntStream.of(sequence).parallel().forEach(x -> {
            int oldValue;
            int newValue;

            do {
                oldValue = controlHashSum.get();
                newValue = oldValue ^ x;
            } while (!controlHashSum.compareAndSet(oldValue, newValue));
        });

        return controlHashSum.get();
    }
}
