package com.example.humans;

import com.example.checkers.HumanAgeChecker;
import com.example.sorters.QuickSort;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

public class HumansRepositoryTest {

    @Test
    public void insert() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void deleteByIndex() {
    }

    @Test
    public void getIndexById() {
        HumansRepository actual = init();
        assertEquals(0, actual.getIndexById(1));
        assertEquals(9, actual.getIndexById(10));
        assertEquals(-1, actual.getIndexById(11));
        assertEquals(-1, actual.getIndexById(-1));
    }

    @Test
    public void getHumanById() {
        Human expected;
        HumansRepository actual = init();

        expected = new Human("r2d2", Human.Gender.man, new LocalDate().minusYears(40));
        assertEquals(expected, actual.getHumanById(5));
        expected = new Human("0", Human.Gender.man, new LocalDate().minusYears(11));
        assertEquals(expected, actual.getHumanById(1));
        expected = new Human("id10index9", Human.Gender.man, new LocalDate().minusYears(109));
        assertEquals(expected, actual.getHumanById(10));
        assertNull(actual.getHumanById(11));
        assertNull(actual.getHumanById(0));
        assertNull(actual.getHumanById(-1));
    }

    @Test
    public void getHumanByIndex() {
        Human expected;
        HumansRepository actual = init();

        expected = new Human("r2d2", Human.Gender.man, new LocalDate().minusYears(40));
        assertEquals(expected, actual.getHumanByIndex(4));
        expected = new Human("0", Human.Gender.man, new LocalDate().minusYears(11));
        assertEquals(expected, actual.getHumanByIndex(0));
        expected = new Human("id10index9", Human.Gender.man, new LocalDate().minusYears(109));
        assertEquals(expected, actual.getHumanByIndex(9));
        assertNull(actual.getHumanByIndex(10));
        assertNull(actual.getHumanByIndex(-1));
    }

    @Test
    public void getLength() {
        assertEquals(0, new HumansRepository(new QuickSort()).getLength());
        assertEquals(10, init().getLength());
    }

    @Test
    public void sortBy() {
        HumansRepository actual = init();
        assertEquals(10, actual.getLength());
    }

    @Test
    public void findBy() {
        Human[] expected = {
                new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44)),
                new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44))
        };
        HumansRepository actual = init();
        assertEquals(expected, actual.findBy(new HumanAgeChecker(), "44"));
    }

    private HumansRepository init() {
        HumansRepository result = new HumansRepository(new QuickSort());
        result.insert(new Human("0", Human.Gender.man, new LocalDate().minusYears(11)));
        result.insert(new Human("a", Human.Gender.man, new LocalDate().minusYears(14)));
        result.insert(new Human("aaa bbb", Human.Gender.man, new LocalDate().minusYears(4)));
        result.insert(new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44)));
        result.insert(new Human("r2d2", Human.Gender.man, new LocalDate().minusYears(40)));
        result.insert(new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44)));
        result.insert(new Human("qwerty", Human.Gender.man, new LocalDate().minusYears(99)));
        result.insert(new Human("0 1", Human.Gender.man, new LocalDate().minusYears(1)));
        result.insert(new Human("q1z2", Human.Gender.man, new LocalDate().minusYears(0)));
        result.insert(new Human("id10index9", Human.Gender.man, new LocalDate().minusYears(109)));
        return result;
    }
}