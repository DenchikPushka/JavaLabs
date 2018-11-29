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
    }

    @Test
    public void getHumanById() {
    }

    @Test
    public void getHumanByIndex() {
    }

    @Test
    public void getLength() {
    }

    @Test
    public void sortBy() {
    }

    @Test
    public void findBy() {
        Human[] array = {
                new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44)),
                new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44))
        };
        HumansRepository humans = new HumansRepository(new QuickSort());
        humans.insert(new Human("q1", Human.Gender.man, new LocalDate().minusYears(22)));
        humans.insert(new Human("w1", Human.Gender.man, new LocalDate().minusYears(14)));
        humans.insert(new Human("aaa bbb", Human.Gender.man, new LocalDate().minusYears(4)));
        humans.insert(new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44)));
        humans.insert(new Human("r2d2", Human.Gender.man, new LocalDate().minusYears(40)));
        humans.insert(new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44)));
        assertEquals(humans.findBy(new HumanAgeChecker(), "44"), array);
    }
}