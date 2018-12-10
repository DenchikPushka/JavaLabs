package com.example;

import com.example.annotations.AutoInjectable;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {
    private static final Logger log = Logger.getLogger(Injector.class);
    private static final String propertiesPath = "src/main/resources/config.properties";

    public<T> T inject(T object) {
        log.info("Injection start "+object.toString());
        Field[] fields = object.getClass().getDeclaredFields();
        Field field;
        Class<?> typeOfField;
        FileInputStream fis;
        Properties properties = new Properties();
        String property;

        try {
            fis = new FileInputStream(propertiesPath);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }

        for (int i = fields.length; i-- > 0;) {
            field = fields[i];
            field.setAccessible(true);
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                try {
                    typeOfField = field.getType();
                    property = properties.getProperty(typeOfField.getName());
                    field.set(object, Class.forName(property).newInstance());
                    log.info("Initialized field "+field.getName()+" = "+field.get(object));
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("Injection end "+object.toString());
        return object;
    }
}
