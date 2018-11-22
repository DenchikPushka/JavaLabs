package com.example.sorters;

import com.example.humans.Human;

import java.util.Comparator;

public interface Sorter {
    Human[] sort(Human[] humans, Comparator comparator);
}
