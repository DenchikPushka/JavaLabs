package com.example.XML;

import com.example.humans.Human;
import com.example.humans.HumansRepository;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

public class DOM implements XML {

    public String toExport(HumansRepository humansRepository) {
        String result = null;
        int repositoryLength = humansRepository.getLength();
        Human human;

        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element elemHumansRepository = document.createElement("humansRepository");
            document.appendChild(elemHumansRepository);

            Element humans = document.createElement("humans");
            elemHumansRepository.appendChild(humans);

            Element elemHuman;


            for (int i = 0; i < repositoryLength; i++) {
                human = humansRepository.getHumanByIndex(i);
                elemHuman = document.createElement("human");
                humans.appendChild(elemHuman);

                String fullName = human.getFullName(),
                        dateBirth = human.getStringDateBirth();
                Human.Gender gender = human.getGender();

                if (fullName != null) {
                    elemHuman.setAttribute("fullName", fullName);
                }
                if (gender != null) {
                    elemHuman.setAttribute("gender", gender.toString());
                }
                if (dateBirth != null) {
                    elemHuman.setAttribute("dateBirth", dateBirth);
                }
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;

            transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            result = writer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public HumansRepository toImport(String xmlData) {
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

    private static String getNodeValue(NamedNodeMap attributes, String name) {
        String result = null;
        Node node = attributes.getNamedItem(name);
        if (node != null) {
            result = node.getNodeValue();
        }
        return  result;
    }
}
