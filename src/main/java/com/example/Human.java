package com.example;

import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;

public class Human {
    public enum Gender {
        man,
        woman
    }
    private String fullName;
    private Gender gender;
    private LocalDate dateBirth;
    private Integer id;
    private static int objectsCount = 1;

    Human(String fullName, Gender gender, LocalDate dateBirth) {
        this.fullName = fullName;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.id = objectsCount;
        objectsCount++;
    }

    public Integer getAge() {
        Integer year = dateBirth.getYear(),
            month = dateBirth.getMonthOfYear(),
            day = dateBirth.getDayOfMonth();
        System.out.println(year.toString()+ ", " + month.toString()+ ", " + day.toString());
        LocalDate tempDate = new LocalDate().minusYears(year).minusMonths(month).minusDays(day);
        return tempDate.getYear();
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
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
