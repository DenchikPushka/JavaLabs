package com.example;

import java.util.Arrays;

public class HumansRepository {
    private Human[] arrayHumans;
    HumansRepository() {
        arrayHumans = new Human[0];
    }

    private static Human[] concatHumans(Human[] a, Human[] b){
        int length = a.length + b.length;
        Human[] result = new Human[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public void insert(Human human) {
        Human[] oneHumanArray = {human};
        this.arrayHumans = concatHumans(arrayHumans, oneHumanArray);
    }

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

    public int getIndexById(int id) {
        for (int i = this.arrayHumans.length; i-- > 0;) {
            if (this.arrayHumans[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public Human getHumanById(int id) {
        for (int i = this.arrayHumans.length; i-- > 0;) {
            if (this.arrayHumans[i].getId() == id) {
                return arrayHumans[i];
            }
        }
        return null;
    }

    public Human getHumanByIndex(int index) {
        if (index >= arrayHumans.length || index < 0) {
            return null;
        }
        return arrayHumans[index];
    }

    public int getLength() {
        return arrayHumans.length;
    }

    @Override
    public String toString() {
        return "HumansRepository{" +
                "arrayHumans=" + Arrays.toString(arrayHumans) +
                '}';
    }
}