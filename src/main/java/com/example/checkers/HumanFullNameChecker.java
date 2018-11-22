package com.example.checkers;

import com.example.humans.Human;

public class HumanFullNameChecker implements Checker {
    public boolean check(Human human, Object value) {
        return human.getFullName().contains(value.toString());
    }
}
