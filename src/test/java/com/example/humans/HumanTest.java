package com.example.humans;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

public class HumanTest {

    @Test
    public void getAge() {
        Human human;
        human = new Human("aaa", Human.Gender.woman, new LocalDate());
        assertEquals(Integer.valueOf(0), human.getAge());
        human = new Human("aaa", Human.Gender.woman, new LocalDate().minusYears(11));
        assertEquals(Integer.valueOf(11), human.getAge());
        human = new Human("aaa", Human.Gender.woman, new LocalDate().minusYears(1));
        assertEquals(Integer.valueOf(1), human.getAge());
        human = new Human("aaa", Human.Gender.woman, new LocalDate().minusMonths(11));
        assertEquals(Integer.valueOf(0), human.getAge());
        human = new Human("aaa", Human.Gender.woman, new LocalDate().minusMonths(1));
        assertEquals(Integer.valueOf(0), human.getAge());
        human = new Human("aaa", Human.Gender.woman, new LocalDate().minusMonths(12));
        assertEquals(Integer.valueOf(1), human.getAge());
        human = new Human("aaa", Human.Gender.woman, new LocalDate().minusDays(11));
        assertEquals(Integer.valueOf(0), human.getAge());
        human = new Human("aaa", Human.Gender.woman, new LocalDate().minusDays(1));
        assertEquals(Integer.valueOf(0), human.getAge());
    }

    @Test
    public void getId() {
    }

    @Test
    public void getFullName() {
        Human human;
        human = new Human("aaa bbb", Human.Gender.woman, new LocalDate());
        assertEquals("aaa bbb", human.getFullName());
        human = new Human("", Human.Gender.woman, new LocalDate());
        assertEquals("", human.getFullName());
    }

    @Test
    public void setFullName() {
    }

    @Test
    public void getGender() {
        Human human;
        human = new Human("a", Human.Gender.man, new LocalDate());
        assertEquals(Human.Gender.man, human.getGender());
        human = new Human("b", Human.Gender.woman, new LocalDate());
        assertEquals(Human.Gender.woman, human.getGender());
        human = new Human("c", null, new LocalDate());
        assertNull(human.getGender());
    }

    @Test
    public void setGender() {
    }

    @Test
    public void getDateBirth() {
        Human human;
        human = new Human("c", Human.Gender.man, new LocalDate());
        assertEquals(new LocalDate(), human.getDateBirth());
        human = new Human("a", Human.Gender.man, new LocalDate().minusYears(1));
        assertEquals(new LocalDate().minusYears(1), human.getDateBirth());
        human = new Human("b", Human.Gender.woman, new LocalDate().minusMonths(1));
        assertEquals(new LocalDate().minusMonths(1), human.getDateBirth());
        human = new Human("c", Human.Gender.man, new LocalDate().minusDays(1));
        assertEquals(new LocalDate().minusDays(1), human.getDateBirth());
    }

    @Test
    public void setDateBirth() {
    }
}