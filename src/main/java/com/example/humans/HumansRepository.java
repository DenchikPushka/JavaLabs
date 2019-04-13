package com.example.humans;

import com.example.annotations.AutoInjectable;
import com.example.checkers.Checker;
import com.example.sorters.Sorter;
import com.sun.jndi.toolkit.url.Uri;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

@XmlType(propOrder = { "arrayHumans" })
@XmlRootElement
public class HumansRepository {
    private static final Logger log = Logger.getLogger(HumansRepository.class);
    private Human[] arrayHumans;

    @AutoInjectable
    private Sorter sorter;

    /**
     * Creates a new and empty repository of humans with the choice of sorting.
     */
    public HumansRepository() {
        log.debug("start)");
        this.arrayHumans = new Human[0];

        log.info("Created "+this.toString());
        log.debug("end");
    }

    @XmlElementWrapper(name = "humans")
    @XmlElement(name = "human")
    private Human[] getArrayHumans() {
        return arrayHumans;
    }

    private void setArrayHumans(Human[] arrayHumans) {
        this.arrayHumans = arrayHumans;
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
        if (this.getHumanById(human.getId()) != null) {
            log.warn("Human not inserted! This human is already in the repository!");
        } else {
            Human[] oneHumanArray = {human};
            this.arrayHumans = concatHumans(this.arrayHumans, oneHumanArray);
        }

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

    private static String getNodeValue(NamedNodeMap attributes, String name) {
        String result = null;
        Node node = attributes.getNamedItem(name);
        if (node != null) {
            result = node.getNodeValue();
        }
        return  result;
    }

    public String exportToXMLDOM() {
        String result = null;
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element humansRepository = document.createElement("humansRepository");
            document.appendChild(humansRepository);

            Element humans = document.createElement("humans");
            humansRepository.appendChild(humans);

            Element human;

            for (int i = 0; i < this.arrayHumans.length; i++) {
                human = document.createElement("human");
                humans.appendChild(human);

                String fullName = this.arrayHumans[i].getFullName(),
                        dateBirth = this.arrayHumans[i].getStringDateBirth();
                Human.Gender gender = this.arrayHumans[i].getGender();

                if (fullName != null) {
                    human.setAttribute("fullName", fullName);
                }
                if (gender != null) {
                    human.setAttribute("gender", gender.toString());
                }
                if (dateBirth != null) {
                    human.setAttribute("dateBirth", dateBirth);
                }
            }


            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;

            transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            result = writer.toString();

            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static HumansRepository importFromXMLDOM(String xmlData) {
        HumansRepository result = new HumansRepository();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document document;
        DocumentBuilder builder;

        String fullName;
        Human.Gender gender;
        LocalDate dateBirth;
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");

        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(xmlData)));
            NodeList humanElements = document.getDocumentElement().getElementsByTagName("human");

            for (int i = 0; i < humanElements.getLength(); i++) {
                Node employee = humanElements.item(i);
                NamedNodeMap attributes = employee.getAttributes();
                gender = null;
                fullName = getNodeValue(attributes, "fullName");
                String genderString = getNodeValue(attributes,"gender");
                dateBirth = dtf.parseLocalDate(getNodeValue(attributes, "dateBirth"));
                if (genderString != null) {
                    if (genderString.equals("man")) {
                        gender = Human.Gender.man;
                    } else if (genderString.equals("woman")) {
                        gender = Human.Gender.woman;
                    }
                }
                result.insert(new Human(fullName, gender, dateBirth));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String exportToXMLJAXB() {
        StringWriter writer = new StringWriter();
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(HumansRepository.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(this, writer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        String result = writer.toString();
        System.out.println(result);
        return result;
    }

    public static HumansRepository importFromXMLJAXB(String xmlData) {
        HumansRepository result = null;
        StringReader reader = new StringReader(xmlData);
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(HumansRepository.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            result = (HumansRepository) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String toString() {
        return "HumansRepository{" +
                "arrayHumans=" + Arrays.toString(arrayHumans) +
                ", sorter=" + sorter +
                '}';
    }

    private static HumansRepository staticHumans = new HumansRepository();

    public static HumansRepository importFromXMLSAX(String xmlData) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        AdvancedXMLHandler handler = new AdvancedXMLHandler();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(new InputSource(new StringReader(xmlData)), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staticHumans;
    }

    private static class AdvancedXMLHandler extends DefaultHandler {
        private String fullName;
        private Human.Gender gender;
        private LocalDate dateBirth;
        private static DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equals("human")) {
                fullName = null;
                gender = null;
                dateBirth = null;
                fullName = attributes.getValue("fullName");
                String genderString = attributes.getValue("gender");
                dateBirth = dtf.parseLocalDate(attributes.getValue("dateBirth"));
                if (genderString != null) {
                    if (genderString.equals("man")) {
                        gender = Human.Gender.man;
                    } else if (genderString.equals("woman")) {
                        gender = Human.Gender.woman;
                    }
                }
                staticHumans.insert(new Human(fullName, gender, dateBirth));
            }
        }
    }

}
