package com.dangminhphuc.dev.typeconversion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConverterFactoryImplTest {

    @Test
    @DisplayName("Converter Factory should be return multi datatypes")
    public void converterFactoryImpl_whenConvert1Source_thenShouldReturnDataTypes() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverterFactory(new StringToNumberConverterFactory());

        Integer intValue = service.convert("1", Integer.class);
        Double doubleValue = service.convert("1", Double.class);
        Long longValue = service.convert("1", Long.class);

        assertEquals(1, intValue, "Target value should be int datatype");
        assertEquals(1L, longValue, "Target value should be long datatype");
        assertEquals(1.0, doubleValue, "Target value should be double datatype");
    }

    @Test
    @DisplayName("StringToNumberConverterFactory should throw ConversionFailedException for unsupported target type")
    void whenConvertToUnsupportedNumberType_thenThrowException() {
        // given
        DefaultConversionService service = new DefaultConversionService();
        service.addConverterFactory(new StringToNumberConverterFactory());

        // when & then
        assertThrows(ConversionFailedException.class, () -> {
            service.convert("dev", Float.class); // Float is not supported
        }, "Expected IllegalArgumentException for unsupported Number type");
    }

}
