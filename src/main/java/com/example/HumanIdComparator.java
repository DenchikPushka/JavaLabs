package com.example;

import java.util.Comparator;

public class HumanIdComparator implements Comparator<Human> {
    public int compare(Human a, Human b) {
        return a.getId().compareTo(b.getId());
    }
}
