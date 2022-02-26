package com.ssafy.db.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ReportAttributeConverter implements AttributeConverter<String, Integer> {

    @Override
    public Integer convertToDatabaseColumn(String s) {
        if ("community".equals(s)) {
            return 1;
        } else if ("comment".equals(s)) {
            return 2;
        } else if ("chatroom".equals(s)) {
            return 3;
        } else if ("confession".equals(s)) {
            return 4;
        } else if ("advice".equals(s)) {
            return 5;
        }
        return 0;
    }

    @Override
    public String convertToEntityAttribute(Integer code) {
        if (1 == code) {
            return "community";
        } else if (2 == code) {
            return "comment";
        } else if (3 == code) {
            return "chatroom";
        } else if (4 == code) {
            return "confession";
        } else if (5 == code) {
            return "advice";
        }
        return "UNKNOWN_REPORT";
    }
}