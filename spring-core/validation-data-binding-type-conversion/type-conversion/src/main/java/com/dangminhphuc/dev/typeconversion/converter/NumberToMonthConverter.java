package com.dangminhphuc.dev.typeconversion.converter;

import org.springframework.core.convert.converter.Converter;

public class NumberToMonthConverter implements Converter<Integer, String> {
    @Override
    public String convert(Integer source) {
        return switch (source) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "Invalid";
        };
    }
}
