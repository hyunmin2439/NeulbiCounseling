package com.ssafy.db.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleAttributeConverter implements AttributeConverter<String, Integer> {

    @Override
    public Integer convertToDatabaseColumn(String s) {
        if ("USER".equals(s)) {
            return 1;
        } else if ("ADMIN".equals(s)) {
            return 2;
        }
        return 0;
    }

    @Override
    public String convertToEntityAttribute(Integer code) {
        if (1 == code) {
            return "USER";
        } else if (2 == code) {
            return "ADMIN";
        }
        return "HACKER";
    }
}
