package com.example;

import java.util.Comparator;

public class BubbleSort implements Sorter {

    private Human[] people;

    private void toSwap(int first, int second) {
        Human temp = people[first];
        people[first] = people[second];
        people[second] = temp;
    }

    public Human[] sort(Human[] humans, Comparator comparator) {
        this.people = humans;
        for (int i = people.length - 1; i >= 1; i--) {
            for (int j = 0; j < i; j++) {
                if (comparator.compare(people[j], people[j + 1]) > 0) {
                    toSwap(j, j + 1);
                }
            }
        }
        return people;
    }
}
