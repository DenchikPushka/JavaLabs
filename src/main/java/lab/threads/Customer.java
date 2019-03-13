package lab.threads;

public class Customer {
    enum actionTypes {
        get,
        set
    }
    private actionTypes action;
    private int timeout;
    private double sum;

    public Customer(actionTypes action, int timeout, double sum) {
        this.action = action;
        this.timeout = timeout;
        this.sum = sum;
    }

    public actionTypes getAction() {
        return action;
    }

    public int getTimeout() {
        return timeout;
    }

    public double getSum() {
        return sum;
    }

}
