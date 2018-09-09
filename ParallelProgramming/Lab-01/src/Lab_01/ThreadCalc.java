package Lab_01;
import java.lang.Math;

class ThreadCalc extends Thread{
    private double vector[];
    private int endIndex;
    private  int startIndex;
    private double result;

    public ThreadCalc(double[] vector, int startIndex, int endIndex) {
        this.vector = vector;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.result = 0;
    }

    public double getResult() {
        return result;
    }

    @Override
    public void run(){
        for(int i = startIndex; i<endIndex; i++ ){
            result += Math.pow(this.vector[i], 2);
        }
    }
}
