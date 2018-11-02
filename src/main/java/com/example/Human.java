package com.example;

import org.joda.time.LocalDate;

public class Human {
    private String fullName;
    private Byte gender;
    private LocalDate dateBirth;
    private Integer id;

    public Human(String fullName, Byte gender, LocalDate dateBirth) {
        this.fullName = fullName;
        this.gender = gender;
        this.dateBirth = dateBirth;
    }

    @Override
    public String toString() {
        return "Human{" +
                "fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", dateBirth=" + dateBirth +
                ", id=" + id +
                '}';
    }
}
