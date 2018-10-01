package Lab_02;

public class App {

    private static final int QUEUE_SIZE = 7;
    private static final long PROCESSES_AMOUNT = 10;
    private static final int MAX_EXECUTION_TIME_MILLIS = 500;
    private static final int MIX_EXECUTION_TIME_MILLIS = 250;

    private static final int generationTimeMillis = 300;

    public static void main(String[] args) {
        System.out.println("Starting modeling...");
        System.out.println("CPUQueue size: " + QUEUE_SIZE);
        System.out.println("CPUProcesses amount: " + PROCESSES_AMOUNT);
        System.out.println("CPUProcess generation time: " + generationTimeMillis + " millis.");

        CPUQueue queue = new CPUQueue(QUEUE_SIZE);
        CPU[] cpus = new CPU[2];
        ProcessGenerator[] processGenerators = new ProcessGenerator[2];

        for (int i=0; i < 2; i++) {
            cpus[i] = new CPU(queue, i);
            queue.subscribe(cpus[i]);
            cpus[i].start();
        }

        for (int i=0; i < 2; i++) {
            processGenerators[i] = new ProcessGenerator(
                    queue, cpus, PROCESSES_AMOUNT, i, generationTimeMillis, MIX_EXECUTION_TIME_MILLIS,
                    MAX_EXECUTION_TIME_MILLIS, (i == 0)
            );
            processGenerators[i].start();
        }
    }
}
