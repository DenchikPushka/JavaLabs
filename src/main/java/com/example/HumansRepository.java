package com.example;

import java.util.Arrays;
import java.util.Comparator;

public class HumansRepository {
    private Human[] arrayHumans;
    private Sorter sorter;
    /**
     * Constructs a new and empty repository of humans.
     */
    HumansRepository() {
        arrayHumans = new Human[0];
        sorter = new BubbleSort();
    }

    /**
     * Combines 2 arrays of humans.
     * @param a First array
     * @param b Second array
     * @return new merged array
     */
    private static Human[] concatHumans(Human[] a, Human[] b){
        int length = a.length + b.length;
        Human[] result = new Human[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    /**
     * Adds a human to the end of the repository.
     * @param human Object of class Human
     */
    public void insert(Human human) {
        if (human == null) {
            throw new Error("human is null");
        }
        Human[] oneHumanArray = {human};
        this.arrayHumans = concatHumans(arrayHumans, oneHumanArray);
    }

    /**
     * Deletes human from repository by id.
     * @param id Id of human
     * @return true if the human was deleted, and false if the person was not found in the repository
     */
    public boolean deleteById(int id) {
        for (int i = this.arrayHumans.length; i-- > 0;) {
            if (this.arrayHumans[i].getId() == id) {
                Human[] result = new Human[arrayHumans.length - 1];
                System.arraycopy(arrayHumans, 0, result, 0, i);
                System.arraycopy(arrayHumans, i + 1, result, i, arrayHumans.length - i - 1);
                this.arrayHumans = result;
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes human from repository by index.
     * @param index Index of human in repository
     * @return true if the human was deleted, and false if index is not within the bounds of the array
     */
    public boolean deleteByIndex(int index) {
        if (index >= arrayHumans.length || index < 0) {
            return false;
        }
        Human[] result = new Human[arrayHumans.length - 1];
        System.arraycopy(arrayHumans, 0, result, 0, index);
        System.arraycopy(arrayHumans, index + 1, result, index, arrayHumans.length - index - 1);
        this.arrayHumans = result;
        return true;
    }

    /**
     * Returns a human's index in repository by id of human.
     * @param id Id of human
     * @return index of human; returns -1 if human not found
     */
    public int getIndexById(int id) {
        for (int i = this.arrayHumans.length; i-- > 0;) {
            if (this.arrayHumans[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a human from repository by id of human.
     * @param id Id of human
     * @return human; returns null if human not found
     */
    public Human getHumanById(int id) {
        for (int i = this.arrayHumans.length; i-- > 0;) {
            if (this.arrayHumans[i].getId() == id) {
                return arrayHumans[i];
            }
        }
        return null;
    }

    /**
     * Returns a human from repository by index.
     * @param index Index of human
     * @return human; returns null if index not within the bounds of the array
     */
    public Human getHumanByIndex(int index) {
        if (index >= arrayHumans.length || index < 0) {
            return null;
        }
        return arrayHumans[index];
    }

    /**
     * Returns a length of repository.
     * @return length of repository
     */
    public int getLength() {
        return arrayHumans.length;
    }

    public void sortBy(Comparator comparator) {
        this.arrayHumans = sorter.sort(arrayHumans, comparator);
    }

    @Override
    public String toString() {
        return "HumansRepository{" +
                "arrayHumans=" + Arrays.toString(arrayHumans) +
                '}';
    }
}