package com.example.comparators;

import com.example.humans.Human;

import java.util.Comparator;

public class HumanIdComparator implements Comparator<Human> {
    public int compare(Human a, Human b) {
        return a.getId().compareTo(b.getId());
    }
}
