package com.example.sorters;

import com.example.comparators.HumanAgeComparator;
import com.example.comparators.HumanFullNameComparator;
import com.example.humans.Human;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuickSortTest {

    @Test
    public void sort() {
        Human[] expected, actual;
        Sorter sorter = new QuickSort();

        expected = new Human[]{
                new Human("111", Human.Gender.man, new LocalDate())
        };
        actual = new Human[]{
                new Human("111", Human.Gender.man, new LocalDate())
        };
        actual = sorter.sort(actual, new HumanAgeComparator());
        for (int i = expected.length; i-- > 0;) {
            assertEquals(expected[i], actual[i]);
        }
        actual = sorter.sort(actual, new HumanFullNameComparator());
        for (int i = expected.length; i-- > 0;) {
            assertEquals(expected[i], actual[i]);
        }

        expected = new Human[]{
                new Human("111", Human.Gender.man, new LocalDate()),
                new Human("112", Human.Gender.man, new LocalDate().minusYears(1))
        };
        actual = new Human[]{
                new Human("112", Human.Gender.man, new LocalDate().minusYears(1)),
                new Human("111", Human.Gender.man, new LocalDate())
        };
        actual = sorter.sort(actual, new HumanAgeComparator());
        for (int i = expected.length; i-- > 0;) {
            assertEquals(expected[i], actual[i]);
        }
        actual = sorter.sort(actual, new HumanFullNameComparator());
        for (int i = expected.length; i-- > 0;) {
            assertEquals(expected[i], actual[i]);
        }

        expected = new Human[]{
                new Human("111", Human.Gender.man, new LocalDate()),
                new Human("111", Human.Gender.man, new LocalDate()),
                new Human("112", Human.Gender.man, new LocalDate().minusYears(1))
        };
        actual = new Human[]{
                new Human("111", Human.Gender.man, new LocalDate()),
                new Human("112", Human.Gender.man, new LocalDate().minusYears(1)),
                new Human("111", Human.Gender.man, new LocalDate())
        };
        actual = sorter.sort(actual, new HumanAgeComparator());
        for (int i = expected.length; i-- > 0;) {
            assertEquals(expected[i], actual[i]);
        }
        actual = sorter.sort(actual, new HumanFullNameComparator());
        for (int i = expected.length; i-- > 0;) {
            assertEquals(expected[i], actual[i]);
        }

        expected = new Human[]{
                new Human("111", Human.Gender.man, new LocalDate()),
                new Human("1111", Human.Gender.man, new LocalDate()),
                new Human("112", Human.Gender.man, new LocalDate().minusYears(1))
        };
        actual = new Human[]{
                new Human("1111", Human.Gender.man, new LocalDate()),
                new Human("112", Human.Gender.man, new LocalDate().minusYears(1)),
                new Human("111", Human.Gender.man, new LocalDate())
        };
        actual = sorter.sort(actual, new HumanFullNameComparator());
        for (int i = expected.length; i-- > 0;) {
            assertEquals(expected[i], actual[i]);
        }
    }
}