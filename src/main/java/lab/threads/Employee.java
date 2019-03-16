package lab.threads;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.ListIterator;

public class Employee extends Thread {
    private static final Logger log = Logger.getLogger(Employee.class);
    private LinkedList<Customer> queue;
    private Storage storage;
    private String name, textColor;

    public Employee(LinkedList<Customer> queue, Storage storage, String name, String textColor) {
        this.queue = queue;
        this.storage = storage;
        this.name = name;
        this.textColor = textColor;
    }

    public synchronized LinkedList<Customer> getQueue() {
        return queue;
    }

    public synchronized void pushCustomer(Customer customer) {
        queue.addLast(customer);
        log.info(toColorText(this.name+": в очередь встал клиент "+customer.toString(), textColor));
        log.info(toColorText(this.name+": длина очереди - "+queue.size(), textColor));
    }

    @Override
    public void run() {
        Customer customer;
        LinkedList<Customer> queue;
        while (true) {
            queue = this.getQueue();
            if (queue.size() > 0) {
                customer = queue.getFirst();
                log.info(toColorText(this.name+": начал обработку клиента "+customer, textColor));
                try {
                    Thread.sleep(customer.getTimeout());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (customer.getAction() == Customer.actionTypes.set) {
                    this.storage.bringIn(customer.getSum());
                    log.info(toColorText(this.name+": внес сумму "+customer.getSum(), textColor));
                    log.info(toColorText("Остаток хранилища: "+this.storage.getSum(), "YELLOW"));
                }
                queue.removeFirst();
                log.info(toColorText(this.name+": закончил обработку клиента "+customer, textColor));
                log.info(toColorText(this.name+": длина очереди - "+queue.size(), textColor));
            }

        }
        /*ListIterator iterator = queue.listIterator();
        while (iterator.hasNext())
            System.out.println(iterator.next());*/
    }

    private static String toColorText(String text, String color) {
        int x = color.equals("RED") ? 31 :
                color.equals("GREEN") ? 32 :
                color.equals("YELLOW") ? 33 :
                color.equals("BLUE") ? 34 :
                color.equals("MAGENTA") ? 35 :
                color.equals("CYAN") ? 36 :
                color.equals("WHITE") ? 37 :
                color.equals("BLACK") ? 30 :
                color.equals("BRIGHT") ? 1 : 0;
        return (char) 27 + "[" + x + "m" + text + (char) 27 + "[0m";
    }
}
