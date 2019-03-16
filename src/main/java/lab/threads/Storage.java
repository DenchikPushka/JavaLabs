package lab.threads;

import org.apache.log4j.Logger;

public class Storage {
    private double sum;

    public Storage(double sum) {
        this.sum = sum;
    }

    public synchronized double getSum() {
        return sum;
    }

    public synchronized void bringOut(double sum) {
        if (sum <= 0) {
            throw new Error("Incorrect sum");
        }
        if (sum > this.sum) {
            throw new Error("Big sum");
        } else {
            this.sum -= sum;
        }
    }

    public synchronized void bringIn(double sum) {
        if (sum <= 0) {
            throw new Error("Incorrect sum");
        }
        this.sum += sum;
    }
}
