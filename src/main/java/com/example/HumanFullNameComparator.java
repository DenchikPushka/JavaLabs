package com.example;

import java.util.Comparator;

public class HumanFullNameComparator implements Comparator<Human> {
    public int compare(Human a, Human b) {
        return a.getFullName().compareTo(b.getFullName());
    }
}
