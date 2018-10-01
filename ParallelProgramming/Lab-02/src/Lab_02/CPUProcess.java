package Lab_02;

public class CPUProcess {
    private int kind;
    private int milliSecToExec;

    CPUProcess(int kind, int milliSecondsToExec) {
        this.kind = kind;
        this.milliSecToExec = milliSecondsToExec;
    }

    public int getTime() {
        return this.milliSecToExec;
    }

    public int getKind() {
        return this.kind;
    }
}
