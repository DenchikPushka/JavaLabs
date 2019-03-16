package lab.threads;

import org.apache.log4j.Logger;

import java.util.LinkedList;

public class Employee extends Thread {
    private static final Logger log = Logger.getLogger(Employee.class);
    private LinkedList<Customer> queue;
    private Storage storage;
    private String name, textColor;
    private Employee[] currentEmployees;

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
        log.info(toColorText(this.name+": клиент "+customer+" встал в очередь ", textColor));
        log.info(toColorText(this.name+": длина очереди - "+queue.size(), textColor));
    }

    @Override
    public void run() {
        Customer customer;
        LinkedList<Customer> queue;
        Customer.actionTypes action;
        double sum;
        while (true) {
            queue = this.getQueue();
            if (queue.size() > 0) {
                customer = queue.getFirst();
                log.info(toColorText(this.name+": начал обработку клиента "+customer, textColor));
                action = customer.getAction();
                sum = customer.getSum();
                try {
                    Thread.sleep(customer.getTimeout());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (action == Customer.actionTypes.set) {
                    this.storage.bringIn(sum);
                    log.info(toColorText(this.name+": внес сумму "+sum, textColor));
                    log.info(toColorText("Остаток хранилища: "+this.storage.getSum(), "YELLOW"));
                } else {
                    if (this.storage.bringOut(sum)) {
                        log.info(toColorText(this.name+": снял сумму "+sum, textColor));
                        log.info(toColorText("Остаток хранилища: "+this.storage.getSum(), "YELLOW"));
                    } else {
                        log.info(toColorText(this.name+": в хранилище недостаточно средств для клиента "+customer, textColor));
                        queue.removeFirst();
                        log.info(toColorText(this.name+": клиент "+customer+" вышел из очереди", textColor));
                        selectQueue(this.currentEmployees, customer);
                        continue;
                    }
                }
                queue.removeFirst();
                log.info(toColorText(this.name+": закончил обработку клиента "+customer, textColor));
                log.info(toColorText(this.name+": длина очереди - "+queue.size(), textColor));
            }

        }
    }

    public void setCurrentEmployees(Employee[] currentEmployees) {
        this.currentEmployees = currentEmployees;
    }

    public static void selectQueue(Employee[] employees, Customer customer) {
        Employee minEmployee = employees[0];
        int minLength = minEmployee.getQueue().size(), temp;
        for (int i = 3; i-- > 0;) {
            temp = employees[i].getQueue().size();
            if (temp < minLength) {
                minLength = temp;
                minEmployee = employees[i];
            }
        }
        minEmployee.pushCustomer(customer);
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
