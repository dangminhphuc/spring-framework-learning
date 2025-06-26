package com.dangminhphuc.dev.typeconversion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterImplTest {
    @Test
    @DisplayName("Converter implementation return value when source correct")
    public void numberToMonthConverterTest() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new NumberToMonthConverter());

        String[] months = new String[]{
                "",
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };

        for (int i = 1; i <= 12; i++) {
            String month = service.convert(i, String.class);
            assertEquals(month, months[i], "Source [" + i + "] should convert to " + month);
        }
    }

    @Test
    @DisplayName("Converter implementation return invalid when source incorrect")
    public void numberToMonthConverter_whenSourceIncorrect_shouldReturnInvalid() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new NumberToMonthConverter());

        String month = service.convert(0, String.class);

        assertEquals("Invalid", month, "Invalid source shouldn't convert to correct value");
    }
}
