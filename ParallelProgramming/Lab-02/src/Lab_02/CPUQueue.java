package Lab_02;

public class CPUQueue {
    private final Object lock = new Object();
    private CPUProcess[] queue;
    private int insertElementIndex;
    private int inputSourcesCounter;
    private boolean returnNull;
    private boolean empty;
    private int maxlen;

    CPUQueue(int queueSize) {
        this.queue = new CPUProcess[queueSize];
        this.insertElementIndex = 0;
        this.inputSourcesCounter = 0;
        this.maxlen = 0;
        this.empty = false;
    }

    public boolean subscribe(CPU cpu) {
        synchronized (this.lock) {
            this.inputSourcesCounter++;
            System.out.println("CPU " + cpu + " subscribed!");
            return true;
        }
    }

    public CPUProcess getOrWait() throws InterruptedException {
        synchronized (this.lock) {
            if (this.queue[0] != null) {
                return get();
            } else if (this.inputSourcesCounter == 0) {
                return new ShutDownSignal(-1, 0);
            } else {
                if (this.returnNull) {
                    this.returnNull = false;
                    return null;
                }
                this.lock.wait();
                if (this.returnNull) {
                    this.returnNull = false;
                    return null;
                } else if (this.queue[0] != null) {
                    return get();
                } else if (this.inputSourcesCounter == 0) {
                    return new ShutDownSignal(-1, 0);
                } else {
                    return null;
                }
            }
        }
    }

    private CPUProcess get() {
        CPUProcess neededProcess = this.queue[0];
        System.arraycopy(this.queue, 1, this.queue, 0, this.queue.length - 1);
        this.queue[this.queue.length - 1] = null;
        this.insertElementIndex--;

        return neededProcess;
    }

    public boolean put(CPUProcess process) {
        synchronized (this.lock) {
            boolean status = !(this.insertElementIndex == this.queue.length);

            if (status){
                this.queue[this.insertElementIndex] = process;
                this.insertElementIndex++;
                this.lock.notify();
            }

            this.maxlen = Math.max(this.maxlen, this.insertElementIndex);

            return status;
        }
    }

    public void setEmptyInput() {
        synchronized (this.lock) {
            this.inputSourcesCounter--;
            if (this.inputSourcesCounter == 0) {
                System.out.println("Maximum queue length: " + this.maxlen);
                this.empty = true;
            }
            this.lock.notifyAll();
        }
    }

    public void signalPutDirectly() {
        synchronized (this.lock) {
            this.returnNull = true;
            this.lock.notify();
        }
    }

    public boolean isEmpty() {
        return this.empty;
    }
}
