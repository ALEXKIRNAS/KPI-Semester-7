package Lab_02;

public class CPU extends Thread {
    private final Object lock = new Object();
    private CPUQueue input_queue;
    private CPUProcess process;
    private int cpuKind;
    private int busyKind;

    public CPU(CPUQueue queue, int cpuKind) {
        this.input_queue = queue;
        this.cpuKind = cpuKind;
        this.busyKind = -1;
    }

    public void run() {
        while (true) {

            synchronized (this.lock) {
                if (this.process == null) {
                    try {
                        CPUProcess input_process = this.input_queue.getOrWait();
                        if (input_process != null) {
                            this.process = input_process;
                        } else {
                            this.lock.wait();
                        }
                    } catch (InterruptedException e) {
                        if (isInterrupted()) {
                            System.out.println("CPU is interrupted");
                            return;
                        }
                    }
                }

                if (process instanceof ShutDownSignal) {
                    break;
                }

                if (this.process == null) {
                        continue;
                }

                System.out.println("Executing process " + process + " @ kind: " + process.getKind() + " on CPU kind " +
                                   this.cpuKind);
                try {
                    this.busyKind = this.process.getKind();
                    Thread.sleep(this.process.getTime());
                } catch (InterruptedException e) {
                    if (isInterrupted()) {
                        System.out.println("CPU @ Kind " + this.cpuKind + " @ is interrupted.");
                        return;
                    }
                }

                this.process = null;
                this.busyKind = -1;
            }
        }
        System.out.println("CPU @ Kind " + this.cpuKind + " @ is stopped.");
    }

    public CPUProcess popProcess() {
        synchronized (lock) {
            CPUProcess process = this.process;
            this.process = null;
            this.busyKind = -1;
            return process;
        }
    }

    public void setProcess(CPUProcess process) {
        this.input_queue.signalPutDirectly();

        synchronized (lock) {
            this.process = process;
            busyKind = process.getKind();
            lock.notify();
        }
    }

    public int getBusyKind() {
        return busyKind;
    }
}
