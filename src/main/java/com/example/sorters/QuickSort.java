package com.example.sorters;

import com.example.humans.Human;

import java.util.Comparator;

public class QuickSort implements Sorter {

    private Human[] people;
    private Comparator comparator;

    public Human[] sort(Human[] humans, Comparator comparator) {
        this.people = humans;
        this.comparator = comparator;
        int startIndex = 0;
        int endIndex = humans.length - 1;
        doSort(startIndex, endIndex);
        return this.people;
    }

    private void doSort(int start, int end) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (comparator.compare(people[i], people[cur]) <= 0)) {
                i++;
            }
            while (j > cur && (comparator.compare(people[cur], people[j]) <= 0)) {
                j--;
            }
            if (i < j) {
                Human temp = people[i];
                people[i] = people[j];
                people[j] = temp;
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(start, cur);
        doSort(cur+1, end);
    }
}
