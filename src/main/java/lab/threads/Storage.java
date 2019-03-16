package lab.threads;

public class Storage {
    private double sum;

    public Storage(double sum) {
        this.sum = sum;
    }

    public double getSum() {
        return sum;
    }

    public synchronized boolean bringOut(double sum) {
        if (sum <= 0) {
            throw new Error("Incorrect sum");
        }
        if (sum > this.sum) {
            return false;
        } else {
            this.sum -= sum;
            return true;
        }
    }

    public synchronized void bringIn(double sum) {
        if (sum <= 0) {
            throw new Error("Incorrect sum");
        }
        this.sum += sum;
    }
}
