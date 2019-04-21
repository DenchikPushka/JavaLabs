package com.example.XML;

import com.example.humans.HumansRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class JAXB implements XML {

    public String toExport(HumansRepository humansRepository) {
        StringWriter writer = new StringWriter();
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(HumansRepository.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(humansRepository, writer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

    public HumansRepository toImport(String xmlData) {
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
}
