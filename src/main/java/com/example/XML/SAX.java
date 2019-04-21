package com.example.XML;

import com.example.humans.Human;
import com.example.humans.HumansRepository;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;

public class SAX implements XML {
    public String toExport(HumansRepository humansRepository) {
        return null;
    }

    private static HumansRepository staticHumans = new HumansRepository();

    public HumansRepository toImport(String xmlData) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAX.AdvancedXMLHandler handler = new SAX.AdvancedXMLHandler();
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
