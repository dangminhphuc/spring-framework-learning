package com.dangminhphuc.dev.typeconversion;

import com.dangminhphuc.dev.typeconversion.genericconverter.StringToTimeGenericConverter;
import com.dangminhphuc.dev.typeconversion.genericconverter.TimeToStringGenericConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GenericConverterTest {

    @Nested
    @DisplayName("StringToTimeGenericConverter")
    class StringToTimeGenericConverterTest {

        private ConversionServiceFactoryBean factory;

        @BeforeEach
        public void setUp() {
            Set<GenericConverter> converters = Set.of(
                    new StringToTimeGenericConverter()
            );

            this.factory = new ConversionServiceFactoryBean();
            this.factory.setConverters(converters);
            this.factory.afterPropertiesSet();
        }

        @Test
        @DisplayName("Should convert string to LocalDate, YearMonth, and Year")
        void stringToTimeGenericConverter_whenConvertStringInput_thenReturnValid() {
            ConversionService service = this.factory.getObject();

            // Map<Input, Map<TargetType, ExpectedValue>>
            Map<String, Map<Class<?>, Object>> testCases = Map.of(
                    "2025-06-28", Map.of(LocalDate.class, LocalDate.of(2025, 6, 28)),
                    "2025-06", Map.of(YearMonth.class, YearMonth.of(2025, 6)),
                    "2025", Map.of(Year.class, Year.of(2025))
            );

            assertNotNull(service, "ConversionService should not be null");
            testCases.forEach((input, result) -> {
                result.forEach((targetType, expected) -> {
                    Object actual = service.convert(input, targetType);
                    assertEquals(expected, actual, "Should convert string to " + targetType.getSimpleName());
                });
            });
        }

        @Test
        void stringToTimeGenericConverter_whenConvertInputInvalidType_thenThrowConverterNotFoundException() {
            ConversionService service = this.factory.getObject();
            assertNotNull(service, "ConversionService should not be null");

            // Map<InvalidSource, TargetType>
            Map<Object, Class<?>> invalidInputs = Map.of(
                    1, LocalDate.class,
                    1.5, Year.class,
                    true, YearMonth.class
            );

            Class<ConverterNotFoundException> expected = ConverterNotFoundException.class;

            invalidInputs.forEach((input, type) -> assertThrows(
                    expected,
                    () -> service.convert(input, type),
                    "Should throw ConverterNotFoundException for invalid input: " + input));
        }

        @Test
        void stringToTimeGenericConverter_whenConvertInputInvalidType_thenThrowConversionFailedException() {
            ConversionService service = this.factory.getObject();
            assertNotNull(service, "ConversionService should not be null");

            // Map<InvalidSource, TargetType>
            Map<Object, Class<?>> invalidInputs = Map.of(
                    "invalid-date", LocalDate.class,
                    "2025_12_30", LocalDate.class,
                    "2025-12-30", Year.class,
                    "2025-13", YearMonth.class
            );

            Class<ConversionFailedException> expected = ConversionFailedException.class;

            invalidInputs.forEach((invalidInput, targetType) -> assertThrows(
                    expected,
                    () -> service.convert(invalidInput, targetType),
                    "Should throw ConversionFailedException for invalid input: " + invalidInput));

        }
    }

    @Nested
    class TimeToStringGenericConverterTest {
        // THE SAME AS StringToTimeGenericConverterTest
    }

    @Nested
    class MultipleGenericConverterTest {
        private ConversionServiceFactoryBean factory;

        @BeforeEach
        public void setUp() {
            Set<GenericConverter> converters = Set.of(
                    new StringToTimeGenericConverter(),
                    new TimeToStringGenericConverter()
            );

            this.factory = new ConversionServiceFactoryBean();
            this.factory.setConverters(converters);
            this.factory.afterPropertiesSet();
        }

        @Test
        @DisplayName("Should convert between String and Time types")
        void multipleGenericConverters_when_then() {
            ConversionService service = this.factory.getObject();

            // Map<Input, Map<TargetType, ExpectedValue>>
            Map<String, Map<Class<?>, Object>> testCases = Map.of(
                    // String to Time
                    "2025-12-30", Map.of(LocalDate.class, LocalDate.of(2025, 12, 30)),
                    "2025-12", Map.of(YearMonth.class, YearMonth.of(2025, 12)),
                    "2025", Map.of(Year.class, Year.of(2025)),
                    // Time to String
                    LocalDate.of(2025, 6, 28).toString(), Map.of(String.class, "2025-06-28"),
                    YearMonth.of(2025, 6).toString(), Map.of(String.class, "2025-06"),
                    Year.of(1995).toString(), Map.of(String.class, "1995")
            );

            assertNotNull(service, "ConversionService should not be null");
            testCases.forEach((input, result) -> result.forEach((targetType, expected) -> {
                Object actual = service.convert(input, targetType);
                assertEquals(expected, actual, "Should convert string to " + targetType.getSimpleName());
            }));
        }
    }
//    @Test
//    @DisplayName("Should return valid target when set converter")
//    public void genericConverter_whenSetGenericConverter_thenReturn1Converter() {
//        Set<StringToTimeGenericConverter> converters = Set.of(
//                new StringToTimeGenericConverter()
//        );
//
//        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
//        factory.setConverters(converters);
//        factory.afterPropertiesSet();
//
//        ConversionService service = factory.getObject();
//        assertNotNull(service);
//
//        LocalDate actual = service.convert("2023-10-01", LocalDate.class);
//        LocalDate expected = LocalDate.of(2023, 10, 1);
//
//        assertEquals(expected, actual, "Should convert string to LocalDate");
//    }
//
//    @Test
//    @DisplayName("Should return valid target when set converter")
//    public void genericConverter_whenSetGenericConverters_thenReturnConverters() {
//        Set<?> converters = Set.of(
//                new StringToTimeGenericConverter(),
//                new TimeToStringGenericConverter()
//        );
//
//        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
//        factory.setConverters(converters);
//        factory.afterPropertiesSet();
//
//        ConversionService service = factory.getObject();
//        assertNotNull(service);
//
//        // Convert String to Time
//        Year yearValue = service.convert("2025", Year.class);
//        // Convert Time to String
//        String stringValue = service.convert(Year.of(2025), String.class);
//
//        assertEquals(Year.of(2025), yearValue, "Should convert string to LocalDate");
//        assertEquals("2025", stringValue, "Should convert string to LocalDate");
//    }
}
