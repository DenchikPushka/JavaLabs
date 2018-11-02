package com.example;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import java.util.Scanner;

public class Terminal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter full name");
        String inputFullName = scanner.nextLine();

        System.out.println("Enter gender");
        Byte inputGender = scanner.nextByte();
        scanner.nextLine();

        System.out.println("Enter date of birth");
        String inputDateString = scanner.nextLine();

        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd.MM.yyyy");
        LocalDate inputDate = LocalDate.parse(inputDateString, dateFormat);

        Human human = new Human(inputFullName, inputGender, inputDate);
        System.out.println(human.toString());
    }
}
