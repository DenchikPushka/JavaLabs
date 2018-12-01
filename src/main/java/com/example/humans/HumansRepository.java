package com.example.humans;

import com.example.checkers.Checker;
import com.example.sorters.Sorter;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Comparator;

public class HumansRepository {
    private static final Logger log = Logger.getLogger(HumansRepository.class);
    private Human[] arrayHumans;
    private Sorter sorter;

    /**
     * Creates a new and empty repository of humans with the choice of sorting.
     * @param sorter object sorter defining used sorting
     */
    public HumansRepository(Sorter sorter) {
        log.debug("start. Input parameters: (sorter="+sorter+')');

        this.arrayHumans = new Human[0];
        this.sorter = sorter;

        log.info("Created "+this.toString());
        log.debug("end");
    }

    /**
     * Combines 2 arrays of humans.
     * @param a First array
     * @param b Second array
     * @return new merged array
     */
    private static Human[] concatHumans(Human[] a, Human[] b){
        log.debug("start");

        int length = a.length + b.length;
        Human[] result = new Human[length];
        try {
            System.arraycopy(a, 0, result, 0, a.length);
            System.arraycopy(b, 0, result, a.length, b.length);
        } catch (Throwable ex) {
            log.error(ex.getMessage());
            throw ex;
        }

        log.debug("end");
        return result;
    }

    /**
     * Adds a human to the end of the repository.
     * @param human Object of class Human
     */
    public void insert(Human human) {
        log.debug("start. Input parameters: (human="+human+')');

        if (human == null) {
            throw new Error("human is null");
        }
        Human[] oneHumanArray = {human};
        this.arrayHumans = concatHumans(this.arrayHumans, oneHumanArray);

        log.info("Inserted "+human.toString());
        log.debug("end");
    }

    /**
     * Deletes human from repository by id.
     * @param id Id of human
     * @return true if the human was deleted, and false if the person was not found in the repository
     */
    public boolean deleteById(int id) {
        log.debug("start. Input parameters: (id="+id+')');
        try {
            for (int i = this.arrayHumans.length; i-- > 0;) {
                if (this.arrayHumans[i].getId() == id) {
                    Human[] result = new Human[arrayHumans.length - 1];
                    System.arraycopy(arrayHumans, 0, result, 0, i);
                    System.arraycopy(arrayHumans, i + 1, result, i, arrayHumans.length - i - 1);
                    log.info("Deleted "+this.arrayHumans[i].toString());
                    this.arrayHumans = result;
                    log.debug("return true");
                    return true;
                }
            }
        } catch (Throwable ex) {
            log.error(ex.getMessage());
            throw ex;
        }

        log.debug("return false");
        return false;
    }

    /**
     * Deletes human from repository by index.
     * @param index Index of human in repository
     * @return true if the human was deleted, and false if index is not within the bounds of the array
     */
    public boolean deleteByIndex(int index) {
        log.debug("start. Input parameters: (index="+index+')');
        try {
            if (index >= arrayHumans.length || index < 0) {
                log.debug("return false");
                return false;
            }
            Human[] result = new Human[arrayHumans.length - 1];
            System.arraycopy(arrayHumans, 0, result, 0, index);
            System.arraycopy(arrayHumans, index + 1, result, index, arrayHumans.length - index - 1);
            log.info("Deleted "+this.arrayHumans[index].toString());
            this.arrayHumans = result;
            log.debug("return true");
            return true;
        } catch (Throwable ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    /**
     * Returns a human's index in repository by id of human.
     * @param id Id of human
     * @return index of human; returns -1 if human not found
     */
    public int getIndexById(int id) {
        log.debug("start. Input parameters: (id="+id+')');
        try {
            for (int i = this.arrayHumans.length; i-- > 0;) {
                if (this.arrayHumans[i].getId() == id) {
                    log.debug("return "+i);
                    return i;
                }
            }
        } catch (Throwable ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        log.debug("return -1");
        return -1;
    }

    /**
     * Returns a human from repository by id of human.
     * @param id Id of human
     * @return human; returns null if human not found
     */
    public Human getHumanById(int id) {
        log.debug("start. Input parameters: (id="+id+')');
        try {
            for (int i = this.arrayHumans.length; i-- > 0;) {
                if (this.arrayHumans[i].getId() == id) {
                    log.debug("return "+arrayHumans[i].toString());
                    return arrayHumans[i];
                }
            }
        } catch (Throwable ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        log.debug("return null");
        return null;
    }

    /**
     * Returns a human from repository by index.
     * @param index Index of human
     * @return human; returns null if index not within the bounds of the array
     */
    public Human getHumanByIndex(int index) {
        log.debug("start. Input parameters: (index="+index+')');
        if (index >= arrayHumans.length || index < 0) {
            log.debug("return null");
            return null;
        }
        log.debug("return "+arrayHumans[index].toString());
        return arrayHumans[index];
    }

    /**
     * Returns a length of repository.
     * @return length of repository
     */
    public int getLength() {
        return arrayHumans.length;
    }

    /**
     * Sorts humans in repository in ascending order by a specific field.
     * @param comparator object defining sorting by the corresponding field
     */
    public void sortBy(Comparator comparator) {
        log.debug("start. Input parameters: (comparator="+comparator+')');
        this.arrayHumans = sorter.sort(arrayHumans, comparator);
        log.info("sorted by "+comparator);
        log.debug("end");
    }

    /**
     * Finds humans in repository by a specific field.
     * @param checker object defined finding by the corresponding field
     * @param value value to search
     * @return array of found humans
     */
    public Human[] findBy(Checker checker, Object value) {
        log.debug("start. Input parameters: (checker="+checker+", value="+value+')');
        Human[] oneHumanArray = new Human[1], result = new Human[0];
        try {
            for (int i = this.arrayHumans.length; i-- > 0;) {
                if (checker.check(arrayHumans[i], value)) {
                    oneHumanArray[0] = arrayHumans[i];
                    result = concatHumans(oneHumanArray, result);
                }
            }
        } catch (Throwable ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        log.debug("return "+ Arrays.toString(result));
        return result;
    }

    @Override
    public String toString() {
        return "HumansRepository{" +
                "arrayHumans=" + Arrays.toString(arrayHumans) +
                ", sorter=" + sorter +
                '}';
    }
}