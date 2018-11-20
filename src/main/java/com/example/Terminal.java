package com.example;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import java.util.Scanner;

public class Terminal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HumansRepository humans = new HumansRepository();
        int command = 999;
        humans.insert(new Human("q1", Human.Gender.man, new LocalDate().minusYears(22)));
        humans.insert(new Human("w1", Human.Gender.man, new LocalDate().minusYears(12)));
        humans.insert(new Human("aaa bbb", Human.Gender.man, new LocalDate().minusYears(11)));
        humans.insert(new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44)));
        humans.insert(new Human("r2d2", Human.Gender.man, new LocalDate().minusYears(13)));

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
                default:
                    System.out.println("Комманда не найдена");
                    break;
            }
        }

    }

    public static void printCommands(){
        System.out.println("----------------------------------------");
        System.out.println("1: Добавить человека");
        System.out.println("2: Удалить человека");
        System.out.println("3: Получить человека");
        System.out.println("4: Вывести репозиторий");
        System.out.println("5: Отсортировать репозиторий");
        System.out.println("0: Выход");
        System.out.println("----------------------------------------");
    }

    public static void createHuman(HumansRepository repository) {
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

    public static void deleteHuman(HumansRepository repository) {
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

    public static void getHuman(HumansRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Получить человека (1 - по id, 2 - по индексу)");
        int command = scanner.nextInt();
        System.out.println("Введите значение:");
        int value = scanner.nextInt();
        Human human = null;

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

    public static void printHumanFromRepository(HumansRepository repository, Human human) {
        System.out.println(repository.getIndexById(human.getId()) +
                ": {" +
                "fullName='" + human.getFullName() + '\'' +
                ", gender=" + human.getGender() +
                ", dateBirth=" + human.getDateBirth() +
                ", age=" + human.getAge() +
                ", id=" + human.getId() +
                '}');
    }

    public static void printRepository(HumansRepository repository) {
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

    public static void sortRepository(HumansRepository repository) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Отсортировать репозиторий (1 - по id, 2 - по имени)");
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
            default:
                System.out.println("Недопустимая комманда!");
                return;
        }

        printRepository(repository);
    }

}
