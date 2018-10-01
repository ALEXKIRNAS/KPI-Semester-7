package Lab_01;

import java.lang.Math;

public class ThreadSample {
    public static int[] SIZES = {5000, 5000000, 50000000, 50000000, 100000000};
    public static int NUMBER_JOBS = 2;

    public static void main(String [] args ) throws InterruptedException{
        for (int SIZE: SIZES) {
            System.out.format("Size %d: \n", SIZE);
            double vector[] = new double[SIZE];

            for (int i = 0; i < SIZE; i++) {
                vector[i] = Math.random();
            }

            double serialResult = 0;
            double start_time = System.nanoTime();

            for (int i = 0; i < SIZE; i++) {
                serialResult += Math.pow(vector[i], 2);
            }
            serialResult = Math.sqrt(serialResult);
            double end_time = System.nanoTime();
            double serial_time = (end_time - start_time);

            System.out.format("\tSerial results: %.6f\n", serialResult);
            System.out.format("\tSerial time: %.3fs\n\n", serial_time / 1e9);

            start_time = System.nanoTime();
            ThreadCalc TreadArrray[] = new ThreadCalc[NUMBER_JOBS];
            for (int i = 0; i < NUMBER_JOBS; i++) {
                int start = SIZE / NUMBER_JOBS * i;
                int end = Math.min(SIZE, SIZE / NUMBER_JOBS * (i + 1));
                TreadArrray[i] = new ThreadCalc(vector, start, end);
                TreadArrray[i].start();
            }

            for (int i = 0; i < NUMBER_JOBS; i++) {
                TreadArrray[i].join();
            }

            double parallelResult = 0;
            for (int i = 0; i < NUMBER_JOBS; i++) {
                parallelResult += TreadArrray[i].getResult();
            }
            parallelResult = Math.sqrt(parallelResult);
            end_time = System.nanoTime();
            double parallel_time = end_time - start_time;

            System.out.format("\tParallel results: %.6f\n", parallelResult);
            System.out.format("\tParallel time: %.3fs\n", parallel_time / 1e9);
            System.out.format("\tSpeedup: %.3fx\n\n",  serial_time / parallel_time);
            System.out.format("\tEfficiency: %.2f%%\n\n",  serial_time / parallel_time / NUMBER_JOBS * 100);
        }
    }
}
