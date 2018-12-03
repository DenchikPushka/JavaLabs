package com.example.humans;

import com.example.checkers.HumanAgeChecker;
import com.example.checkers.HumanFullNameChecker;
import com.example.sorters.BubbleSort;
import com.example.sorters.QuickSort;
import com.example.sorters.SelectionSort;
import com.example.sorters.Sorter;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

public class HumansRepositoryTest {

    @Test
    public void insert() {
        Human expected = new Human("a1", Human.Gender.woman, new LocalDate());
        HumansRepository humans = new HumansRepository(new QuickSort());

        humans.insert(expected);
        assertEquals(expected, humans.getHumanByIndex(0));

        assertEquals(1, humans.getLength());

        humans.insert(expected);
        assertEquals(1, humans.getLength());

        humans.insert(new Human("a1", Human.Gender.woman, new LocalDate()));
        assertEquals(2, humans.getLength());
    }

    @Test
    public void deleteById() {
        HumansRepository actual = init(new QuickSort());
        Human human = new Human("a1", Human.Gender.woman, new LocalDate());
        Integer humanId = human.getId();
        actual.insert(human);
        int expected = actual.getLength() - 1;
        actual.deleteById(humanId);
        assertEquals(expected, actual.getLength());
        assertNull(actual.getHumanById(humanId));
    }

    @Test
    public void deleteByIndex() {
        HumansRepository actual = init(new QuickSort());
        Human human = new Human("a1", Human.Gender.woman, new LocalDate());
        actual.insert(human);
        int humanIndex = actual.getLength() - 1;
        int expected = actual.getLength() - 1;
        actual.deleteByIndex(humanIndex);
        assertEquals(expected, actual.getLength());
        assertNull(actual.getHumanByIndex(humanIndex));
    }

    @Test
    public void getIndexById() {
        HumansRepository actual = new HumansRepository(new QuickSort());
        Human human;

        assertEquals(-1, actual.getIndexById(1));
        assertEquals(-1, actual.getIndexById(2));

        human = new Human("1", Human.Gender.man, new LocalDate());
        actual.insert(human);
        assertEquals(0, actual.getIndexById(human.getId()));

        human = new Human("2", Human.Gender.man, new LocalDate());
        actual.insert(human);
        assertEquals(1, actual.getIndexById(human.getId()));

        assertEquals(-1, actual.getIndexById(-1));
        assertEquals(-1, actual.getIndexById(0));
    }

    @Test
    public void getHumanById() {
        Human expected;
        HumansRepository actual = new HumansRepository(new QuickSort());

        expected = new Human("r2d2", Human.Gender.man, new LocalDate().minusYears(40));
        actual.insert(expected);
        assertEquals(expected, actual.getHumanById(expected.getId()));

        expected = new Human("0", Human.Gender.man, new LocalDate().minusYears(11));
        actual.insert(expected);
        assertEquals(expected, actual.getHumanById(expected.getId()));

        assertNull(actual.getHumanById(0));
        assertNull(actual.getHumanById(-1));
    }

    @Test
    public void getHumanByIndex() {
        Human expected;
        HumansRepository actual = init(new QuickSort());

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
        assertEquals(10, init(new QuickSort()).getLength());
    }

    @Test
    public void sortBy() {
        HumansRepository actual;

        actual = init(new BubbleSort());
        assertEquals(10, actual.getLength());

        actual = init(new QuickSort());
        assertEquals(10, actual.getLength());

        actual = init(new SelectionSort());
        assertEquals(10, actual.getLength());
    }

    @Test
    public void findBy() {
        Human[] expected, actual;
        HumansRepository humans = init(new QuickSort());

        expected = new Human[]{
                new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44)),
                new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44))
        };
        actual = humans.findBy(new HumanAgeChecker(), "44");
        for (int i = expected.length; i-- > 0;) {
            assertEquals(expected[i], actual[i]);
        }

        expected = new Human[]{
                new Human("aaa bbb", Human.Gender.man, new LocalDate().minusYears(4)),
                new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44)),
                new Human("bbb", Human.Gender.man, new LocalDate().minusYears(44))
        };
        actual = humans.findBy(new HumanFullNameChecker(), "b");
        for (int i = expected.length; i-- > 0;) {
            assertEquals(expected[i], actual[i]);
        }
    }

    private HumansRepository init(Sorter sorter) {
        HumansRepository result = new HumansRepository(sorter);
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