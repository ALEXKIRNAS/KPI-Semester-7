package Lab_02;

import java.util.Random;

public class ProcessGenerator extends Thread {
    private CPUQueue queue;
    private CPU[] cpus;
    private long n_processes;
    private final int milliSecToGenProcess;
    private long KilledProcessesCounter;
    private boolean allowedDirectAssign;
    private int minProcessTime;
    private int maxProcessTime;
    private int processKind;
    private Random random = new Random();

    public ProcessGenerator(
            CPUQueue queue,
            CPU[] cpus,
            long processesAmount,
            int processKind,
            int generationTimeMillis,
            int minProcessTime,
            int maxProcessTime,
            boolean allowedDirectAssign) {

        this.queue = queue;
        this.cpus = cpus;
        this.n_processes = processesAmount;
        this.milliSecToGenProcess = generationTimeMillis;
        this.KilledProcessesCounter = 0;
        this.allowedDirectAssign = allowedDirectAssign;
        this.minProcessTime = minProcessTime;
        this.maxProcessTime = maxProcessTime;
        this.processKind = processKind;
    }

    public void run() {

        for (int i = 0; i < this.n_processes; i++) {
            System.out.println("Generator @ " + this.processKind + ". Done " + i + "/" + this.n_processes);
            int executionTimeMillis = (
                    random.nextInt(this.maxProcessTime - this.minProcessTime + 1) + this.minProcessTime
            );
            CPUProcess process = new CPUProcess(this.processKind, executionTimeMillis);

            if (this.allowedDirectAssign) {
                boolean isAssigned = false;
                CPU cpu = this.cpus[this.processKind];
                int kind = cpu.getBusyKind();
                // System.out.println(kind);

                if (kind != this.processKind) {
                    if (kind != -1) {
                        cpu.interrupt();
                    }
                    boolean status = this.queue.put(cpu.popProcess());
                    if (!status) {
                        this.KilledProcessesCounter++;
                    }
                    isAssigned = true;
                } else {
                    for (int j = 0; j < cpus.length; j++) {
                        if (this.cpus[j].getBusyKind() == -1) {
                            // System.out.println(this.cpus[j].getBusyKind() + " " + j);
                            cpu = this.cpus[j];
                            isAssigned = true;
                            break;
                        }
                    }
                }

                if (isAssigned) {
                    cpu.setProcess(process);
                    System.out.println("Generated process: " + process + "@kind: " + this.processKind + " @ assigned directly");
                } else {
                    this.KilledProcessesCounter++;
                    System.out.println("Generated process: " + process + "@kind: " + this.processKind + " @ killed");
                }

            } else {
                boolean status = this.queue.put(process);
                if (!status) {
                    this.KilledProcessesCounter++;
                    System.out.println("Generated process: " + process + "@kind: " + this.processKind + " @ killed");
                } else {
                    System.out.println("Generated process: " + process + "@kind: " + this.processKind + " @ added");
                }
            }

            try {
                Thread.sleep(this.milliSecToGenProcess);
            } catch (InterruptedException ex) {
                if (isInterrupted()) {
                    System.out.println("ProcessGenerator is interrupted. Generated only " + (i + 1) + " processes.");
                    System.out.println("Destroyed processes@kind: " + this.processKind + " = " +
                                       (int)(this.KilledProcessesCounter * 100 / (i + 1)) + "%.");
                    return;
                }
            }
        }

        System.out.println("ProcessGenerator @ kind: " + this.processKind + " @ ended its work.");
        System.out.println("Destroyed processes@ kind: " + this.processKind + " = " +
                            (int)(KilledProcessesCounter * 100 / n_processes) + "%.");

        this.queue.setEmptyInput();
    }
}
