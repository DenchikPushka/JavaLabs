package lab.threads;

import java.util.Random;

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

    @Override
    public String toString() {
        return "Customer{" +
                "action=" + action +
                ", timeout=" + timeout +
                ", sum=" + sum +
                '}';
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

    public static Customer generateCustomer() {
        Customer result;
        int randomInt;
        double randomDouble;
        actionTypes action;

        Random random = new Random();
        randomInt = random.nextInt(2);
        if (randomInt == 1) {
            action = actionTypes.get;
        } else {
            action = actionTypes.set;
        }
        randomInt = random.nextInt(15000);
        randomDouble = (Math.ceil(random.nextDouble() * randomInt * 100) / 100) + 100;
        result = new Customer(action, randomInt, randomDouble);
        return result;
    }

}
