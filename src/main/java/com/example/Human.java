package com.example;

import com.sun.istack.internal.NotNull;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;

public class Human {
    /**
     * Gender of human. Values: man, woman.
     */
    public enum Gender {
        man,
        woman
    }
    private String fullName;
    private Gender gender;
    private LocalDate dateBirth;
    private Integer id;
    private static int objectsCount = 1;

    /**
     * Constructs a new human and set him an id.
     * @param fullName Human's full name
     * @param gender Human's gender (use the class Human.Gender)
     * @param dateBirth Human's date of birth
     */
    Human(@NotNull String fullName, @NotNull Gender gender, @NotNull LocalDate dateBirth) {
        if (fullName == null || gender == null || dateBirth == null) {
            throw new Error("One of the parameters is null");
        }
        this.fullName = fullName;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.id = objectsCount;
        objectsCount++;
    }

    /**
     * Returns a human's age.
     * @return number of full years
     */
    public Integer getAge() {
        Integer year = dateBirth.getYear(),
                month = dateBirth.getMonthOfYear() - 1,
                day = dateBirth.getDayOfMonth() - 1;
        LocalDate tempDate = new LocalDate().minusDays(day).minusMonths(month).minusYears(year);

        return tempDate.getYear();
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(@NotNull String fullName) {
        if (fullName == null) {
            throw new Error("fullName is null");
        }
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(@NotNull Gender gender) {
        if (gender == null) {
            throw new Error("gender is null");
        }
        this.gender = gender;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(@NotNull LocalDate dateBirth) {
        if (dateBirth == null) {
            throw new Error("dateBirth is null");
        }
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
