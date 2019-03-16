package lab.threads;

import org.apache.log4j.Logger;

import java.util.*;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        int command = 999;
        Scanner scanner = new Scanner(System.in);

        while (command != 0) {
            System.out.println("Enter command:");
            command = scanner.nextInt();
            switch (command) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    runMatrix();
                    break;
                case 3:
                    runBank();
                    break;
                default:
                    System.out.println("Command not found!");
                    break;
            }
        }
    }

    private static void runMatrix() {
        int[][] result, a, b;
        long start, finish;
        int size = 2;

        while (size < 1060) {
            System.out.println("================");
            a = Matrices.generateMatrix(size, size);
            b = Matrices.generateMatrix(size, size);
            if (a == null || b == null) {
                return;
            }

            System.out.println("Matrices size: "+a.length+"x"+a[0].length+", "+b.length+"x"+b[0].length);
            System.out.println("----------------");

            if (size < 40) {
                Matrices.printMatrix(a, 2);
                System.out.println("----------------");
                Matrices.printMatrix(b, 2);
                System.out.println("----------------");
            }

            start = System.currentTimeMillis();
            result = new Matrices(a, b).multiply();
            finish = System.currentTimeMillis();
            if (size < 40) {
                Matrices.printMatrix(result, 6);
                System.out.println("----------------");
            }
            System.out.println("time multiply: "+(finish - start));
            System.out.println("----------------");

            start = System.currentTimeMillis();
            result = new Matrices(a, b).multiply2Threads();
            finish = System.currentTimeMillis();
            if (size < 40) {
                Matrices.printMatrix(result, 6);
                System.out.println("----------------");
            }
            System.out.println("time multiply2Threads: "+(finish - start));
            System.out.println("----------------");
            if (size < 1024) {
                size = size << 1;
            } else {
                size += 10;
            }
        }
    }

    private static void runBank() {
        Storage storage = new Storage(100000);
        Employee[] employees = {
                new Employee(new LinkedList<>(), storage, "Кассир 1", "RED"),
                new Employee(new LinkedList<>(), storage, "Кассир 2", "BLUE"),
                new Employee(new LinkedList<>(), storage, "Кассир 3", "GREEN")
        };
        Random random = new Random();

        for (int i = 3; i-- > 0;) {
            employees[i].start();
        }

        while (true) {
            Customer randomCustomer = Customer.generateCustomer();
            Employee minEmployee = employees[0];
            int minLength = minEmployee.getQueue().size(), temp;

            log.info("Пришел клиент "+randomCustomer);

            for (int i = 3; i-- > 0;) {
                temp = employees[i].getQueue().size();
                if (temp < minLength) {
                    minLength = temp;
                    minEmployee = employees[i];
                }
            }
            minEmployee.pushCustomer(randomCustomer);
            try {
                Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
