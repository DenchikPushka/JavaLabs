package lab.threads;

import java.util.LinkedList;
import java.util.ListIterator;

public class Employee extends Thread {
    private LinkedList<Customer> queue;
    private Storage storage;
    private String name;

    public Employee(LinkedList<Customer> queue, Storage storage, String name) {
        this.queue = queue;
        this.storage = storage;
        this.name = name;
    }

    public synchronized LinkedList<Customer> getQueue() {
        return queue;
    }

    public void pushCustomer(Customer customer) {
        queue.addLast(customer);
        System.out.println(this.name+": добавлен в очередь "+customer.toString());
        System.out.println(this.name+": Длина очереди - "+queue.size());
    }

    @Override
    public void run() {
        Customer customer;
        LinkedList<Customer> queue = this.getQueue();
        while (true) {
            //System.out.println(queue.size());
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
