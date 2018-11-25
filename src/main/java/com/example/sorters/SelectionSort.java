package com.example.sorters;

import com.example.humans.Human;

import java.util.Comparator;

public class SelectionSort implements Sorter {

    public Human[] sort(Human[] humans, Comparator comparator) {
        for (int min = 0; min < humans.length - 1; min++) {
            int least = min;
            for (int j = min + 1; j < humans.length; j++) {
                if (comparator.compare(humans[j], humans[least]) < 0) {
                    least = j;
                }
            }
            Human tmp = humans[min];
            humans[min] = humans[least];
            humans[least] = tmp;
        }
        return humans;
    }
}
