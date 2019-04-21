package com.example;

import com.example.XML.DOM;
import com.example.XML.JAXB;
import com.example.XML.SAX;
import com.example.checkers.HumanAgeChecker;
import com.example.checkers.HumanFullNameChecker;
import com.example.checkers.HumanIdChecker;
import com.example.comparators.HumanAgeComparator;
import com.example.comparators.HumanFullNameComparator;
import com.example.comparators.HumanIdComparator;
import com.example.humans.Human;
import com.example.humans.HumansRepository;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import java.util.Scanner;

public class Terminal {
    public static void main(String[] args) {
        DOM dom;
        JAXB jaxb;
        SAX sax = new SAX();
        String strxml = "";
        Scanner scanner = new Scanner(System.in);
        HumansRepository humans;
        humans = new Injector().inject(new HumansRepository());

        int command = 999;
        humans.insert(new Human("q1", Human.Gender.man, new LocalDate().minusYears(22)));
        humans.insert(new Human("w1", Human.Gender.woman, new LocalDate().minusYears(12)));
        humans.insert(new Human("aaa bbb", Human.Gender.man, new LocalDate().minusYears(11)));
        humans.insert(new Human("bbb", Human.Gender.woman, new LocalDate().minusYears(44)));
        humans.insert(new Human("test", Human.Gender.man, new LocalDate().minusYears(1)));
        humans.insert(new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44)));
        humans.insert(new Human("r2d2", null, new LocalDate().minusYears(13)));

        while (command != 0) {
            printCommands();
            command = scanner.nextInt();
            switch (command) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    createHuman(humans);
                    break;
                case 2:
                    deleteHuman(humans);
                    break;
                case 3:
                    getHuman(humans);
                    break;
                case 4:
                    printRepository(humans);
                    break;
                case 5:
                    sortRepository(humans);
                    break;
                case 6:
                    findHumans(humans);
                    break;
                case 7:
                    dom = new DOM();
                    strxml = dom.toExport(humans);
                    System.out.println(strxml);
                    break;
                case 8:
                    dom = new DOM();
                    humans = dom.toImport(strxml);
                    break;
                case 9:
                    jaxb = new JAXB();
                    strxml = jaxb.toExport(humans);
                    System.out.println(strxml);
                    break;
                case 10:
                    jaxb = new JAXB();
                    humans = jaxb.toImport(strxml);
                    break;
                case 12:
                    sax = new SAX();
                    humans = sax.toImport(strxml);
                    break;
                default:
                    System.out.println("Комманда не найдена");
                    break;
            }
        }
    }

    private static void printCommands(){
        System.out.println("----------------------------------------");
        System.out.println("1: Добавить человека");
        System.out.println("2: Удалить человека");
        System.out.println("3: Получить человека");
        System.out.println("4: Вывести репозиторий");
        System.out.println("5: Отсортировать репозиторий");
        System.out.println("6: Поиск человека");
        System.out.println("7: DOM export");
        System.out.println("8: DOM import");
        System.out.println("9: JAXB export");
        System.out.println("10: JAXB import");
        System.out.println("12: SAX import");
        System.out.println("0: Выход");
        System.out.println("----------------------------------------");
    }

    private static void createHuman(HumansRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя:");
        String inputFullName = scanner.nextLine();

        System.out.println("Введите пол (m - man, w - woman):");
        char inputGenderChar;
        try {
            inputGenderChar = scanner.nextLine().charAt(0);
        } catch (Throwable t) {
            System.out.println("Пол не был выбран!");
            return;
        }

        Human.Gender inputGender;

        switch (inputGenderChar) {
            case 'm':
                inputGender = Human.Gender.man;
                break;
            case 'w':
                inputGender = Human.Gender.woman;
                break;
            default:
                System.out.println("Недопустимый пол!");
                return;
        }

        System.out.println("Введите дату рождения (format: dd.MM.yyyy):");
        String inputDateString = scanner.nextLine();

        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd.MM.yyyy");
        LocalDate inputDate;

        try {
            inputDate = LocalDate.parse(inputDateString, dateFormat);
        } catch (Throwable t) {
            System.out.println("Неверный формат даты!");
            return;
        }

        Human human = new Human(inputFullName, inputGender, inputDate);
        repository.insert(human);
        System.out.println("Человек добавлен");
        printHumanFromRepository(repository, human);
    }

    private static void deleteHuman(HumansRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Удалить человека (1 - по id, 2 - по индексу)");
        int command = scanner.nextInt();
        System.out.println("Введите значение:");
        int value = scanner.nextInt();
        boolean deleted;

        switch (command) {
            case 1:
                deleted = repository.deleteById(value);
                break;
            case 2:
                deleted = repository.deleteByIndex(value);
                break;
            default:
                System.out.println("Недопустимая комманда!");
                return;
        }

        if (deleted) {
            System.out.println("Человек удален");
            System.out.println("Длина репозитория: " + repository.getLength());
        } else {
            System.out.println("Человек не был удален!");
        }
    }

    private static void getHuman(HumansRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Получить человека (1 - по id, 2 - по индексу)");
        int command = scanner.nextInt();
        System.out.println("Введите значение:");
        int value = scanner.nextInt();
        Human human;

        switch (command) {
            case 1:
                human = repository.getHumanById(value);
                break;
            case 2:
                human = repository.getHumanByIndex(value);
                break;
            default:
                System.out.println("Недопустимая комманда!");
                return;
        }

        if (human == null) {
            System.out.println("Человек не найден!");
        } else {
            printHumanFromRepository(repository, human);
        }
    }

    private static void printHumanFromRepository(HumansRepository repository, Human human) {
        System.out.println(repository.getIndexById(human.getId()) +
                ": {" +
                "fullName='" + human.getFullName() + '\'' +
                ", gender=" + human.getGender() +
                ", dateBirth=" + human.getDateBirth() +
                ", age=" + human.getAge() +
                ", id=" + human.getId() +
                '}');
    }

    private static void printRepository(HumansRepository repository) {
        int repositoryLength = repository.getLength();
        if (repositoryLength == 0) {
            System.out.println("Пусто");
            return;
        }
        Human human;

        for (int i = 0; i < repositoryLength; i++) {
            human = repository.getHumanByIndex(i);
            printHumanFromRepository(repository, human);
        }
    }

    private static void sortRepository(HumansRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Отсортировать репозиторий");
        System.out.println("1 - по id");
        System.out.println("2 - по имени");
        System.out.println("3 - по возрасту");
        int command = scanner.nextInt();

        switch (command) {
            case 1:
                repository.sortBy(new HumanIdComparator());
                System.out.println("Репозиторий отсортирован по id");
                break;
            case 2:
                repository.sortBy(new HumanFullNameComparator());
                System.out.println("Репозиторий отсортирован по имени");
                break;
            case 3:
                repository.sortBy(new HumanAgeComparator());
                System.out.println("Репозиторий отсортирован по возрасту");
                break;
            default:
                System.out.println("Недопустимая комманда!");
                return;
        }

        printRepository(repository);
    }

    private static void findHumans(HumansRepository repository) {
        Scanner scanner = new Scanner(System.in);
        Human[] foundHumans;
        System.out.println("Найти человека");
        System.out.println("1 - по id");
        System.out.println("2 - по имени");
        System.out.println("3 - по возрасту");
        int command = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите значение:");
        String value = scanner.nextLine();

        switch (command) {
            case 1:
                foundHumans = repository.findBy(new HumanIdChecker(), value);
                break;
            case 2:
                foundHumans = repository.findBy(new HumanFullNameChecker(), value);
                break;
            case 3:
                foundHumans = repository.findBy(new HumanAgeChecker(), value);
                break;
            default:
                System.out.println("Недопустимая комманда!");
                return;
        }

        int resultLength = foundHumans.length;
        for (int i = 0; i < resultLength; i++) {
            printHumanFromRepository(repository, foundHumans[i]);
        }
    }

}
