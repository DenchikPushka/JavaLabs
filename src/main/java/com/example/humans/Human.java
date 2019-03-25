package com.example.humans;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

@XmlType(propOrder = { "fullName", "gender", "dateBirth"})
public class Human {
    private static final Logger log = Logger.getLogger(Human.class);
    /**
     * Gender of human. Values: man, woman.
     */
    public enum Gender {
        man,
        woman
    }
    private String fullName;
    private Gender gender;
    private LocalDate dateBirth;
    private Integer id;
    private static int objectsCount = 1;

    /**
     * Constructs a new human and set him an id.
     * @param fullName Human's full name
     * @param gender Human's gender (use the class Human.Gender)
     * @param dateBirth Human's date of birth
     */
    public Human(String fullName, Gender gender, LocalDate dateBirth) {
        log.debug("start. Input parameters: (fullName='"+fullName+"', gender="+gender+", dateBirth="+dateBirth+')');

        if (fullName.isEmpty() || gender == null || dateBirth == null) {
            log.warn("Empty parameter found!");
        }
        this.fullName = fullName;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.id = objectsCount;
        objectsCount++;

        log.info("Created "+this.toString());
        log.debug("end");
    }

    public Human() {
        this.id = objectsCount;
        objectsCount++;
    }

    /**
     * Returns a human's age.
     * @return number of full years
     */
    public Integer getAge() {
        log.debug("start");

        Integer result,
                year = this.dateBirth.getYear(),
                month = this.dateBirth.getMonthOfYear() - 1,
                day = this.dateBirth.getDayOfMonth() - 1;
        LocalDate tempDate = new LocalDate().minusDays(day).minusMonths(month).minusYears(year);
        result = tempDate.getYear();

        log.debug("end. Result="+result);
        return result;
    }

    public Integer getId() {
        return id;
    }

    @XmlElement(name = "fullName")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName.isEmpty()) {
            log.warn("fullName is empty!");
        }
        this.fullName = fullName;
        log.info(this.toString());
    }

    @XmlElement(name = "gender")
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        if (gender == null) {
            log.warn("gender is null!");
        }
        this.gender = gender;
        log.info(this.toString());
    }

    @XmlElement(name = "dateBirth")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public String getStringDateBirth() {
        return dateBirth.toString();
    }

    public void setDateBirth(LocalDate dateBirth) {
        if (dateBirth == null) {
            log.warn("dateBirth is null!");
        }
        this.dateBirth = dateBirth;
        log.info(this.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(fullName, human.fullName) &&
                gender == human.gender &&
                Objects.equals(dateBirth, human.dateBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, gender, dateBirth, id);
    }

    @Override
    public String toString() {
        return "Human{" +
                "fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", dateBirth=" + dateBirth +
                ", id=" + id +
                '}';
    }
}

class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private static DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public LocalDate unmarshal(String date) throws Exception {
        return dtf.parseLocalDate(date);
    }

    @Override
    public String marshal(LocalDate date) throws Exception {
        return dtf.print(date);

    }
}
