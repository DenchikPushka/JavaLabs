package com.example.checkers;

import com.example.humans.Human;

public class HumanAgeChecker implements Checker {
    public boolean check(Human human, Object value) {
        int intValue = Integer.valueOf((String) value);
        return human.getAge().equals(intValue);
    }
}
