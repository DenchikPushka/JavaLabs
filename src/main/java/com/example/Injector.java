package com.example;

import com.example.annotations.AutoInjectable;
import com.example.sorters.QuickSort;
import com.example.sorters.Sorter;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {

    private String getProperty(String propertyName) {
        FileInputStream fis;
        Properties property = new Properties();
        String result = null;
        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);
        } catch (IOException e) {
            throw new Error(e.getMessage());
        }
        result = property.getProperty(propertyName);

        return result;
    }

    public Object inject(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();

        for (int i = fields.length; i-- > 0;) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                Class<?> typeOfField = field.getType();
                try {
                    System.out.println(field.getType());
                    System.out.println(Class.forName(getProperty(typeOfField.getName())).newInstance().toString());
                    //field.set(Class.forName(getProperty(typeOfField.getName())).newInstance(), Class.forName(getProperty(typeOfField.getName())).newInstance());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }
}
