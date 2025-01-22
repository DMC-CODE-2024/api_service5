package com.gl.mdr.util;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IntegerStringConverter implements AttributeConverter<Object, String> {

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        if (attribute == null) {
            return null;
        }
        if (attribute instanceof Integer) {
            return String.valueOf(attribute);
        }
        return (String) attribute; // assume it's a string
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            // Try converting to Integer if possible
            return Integer.parseInt(dbData);
        } catch (NumberFormatException e) {
            // If it's not a number, return it as a String
            return dbData;
        }
    }
}
