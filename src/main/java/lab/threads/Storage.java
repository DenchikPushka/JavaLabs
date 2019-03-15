package lab.threads;

public class Storage {
    private double sum;

    public Storage(double sum) {
        this.sum = sum;
    }

    public synchronized double getSum() {
        return sum;
    }

    public synchronized double bringOut(double sum) {
        if (sum <= 0) {
            throw new Error("Incorrect sum");
        }
        if (sum > this.sum) {
            return 0;
        } else {
            this.sum -= sum;
            return sum;
        }
    }

    public synchronized double bringIn(double sum) {
        if (sum <= 0) {
            throw new Error("Incorrect sum");
        }
        this.sum += sum;
        return this.getSum();
    }
}
