package lab.threads;

import java.util.LinkedList;
import java.util.ListIterator;

public class Employee extends Thread {
    private LinkedList<Customer> queue;
    private Storage storage;

    public Employee(LinkedList<Customer> queue, Storage storage) {
        this.queue = queue;
        this.storage = storage;
    }

    public LinkedList<Customer> getQueue() {
        return queue;
    }

    public void pushCustomer(Customer customer) {
        queue.addLast(customer);
    }

    @Override
    public void run() {
        Customer customer;
        while (true) {
            if (queue.size() > 0) {
                customer = queue.getFirst();
                System.out.println(customer.toString());
                try {
                    Thread.sleep(customer.getTimeout());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        /*ListIterator iterator = queue.listIterator();
        while (iterator.hasNext())
            System.out.println(iterator.next());*/
    }
}
