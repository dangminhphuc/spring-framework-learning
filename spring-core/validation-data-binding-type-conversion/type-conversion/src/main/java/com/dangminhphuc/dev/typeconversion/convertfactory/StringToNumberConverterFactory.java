package com.dangminhphuc.dev.typeconversion.convertfactory;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumberConverter<>(targetType);
    }

    private static class StringToNumberConverter<T extends Number> implements Converter<String, T> {
        private final Class<T> targetType;

        private StringToNumberConverter(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (this.targetType == Integer.class) {
                return this.targetType.cast(Integer.parseInt(source));
            } else if (this.targetType == Double.class) {
                return this.targetType.cast(Double.parseDouble(source));
            } else if (this.targetType == Long.class) {
                return this.targetType.cast(Long.parseLong(source));
            }
            throw new IllegalArgumentException("Unsupported number type: " + this.targetType);
        }
    }
}
