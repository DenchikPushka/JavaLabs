package com.example;

import org.joda.time.LocalTime;

public class human {
    private String fullName;
    private Byte sex;
    private LocalTime dateBirth;
    private Integer id;

    public human(String fullName, Byte sex, LocalTime dateBirth, Integer id) {
        this.fullName = fullName;
        this.sex = sex;
        this.dateBirth = dateBirth;
        this.id = id;
    }
}
