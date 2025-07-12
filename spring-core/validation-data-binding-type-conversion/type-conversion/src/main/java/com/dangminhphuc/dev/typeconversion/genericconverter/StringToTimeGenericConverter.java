package com.dangminhphuc.dev.typeconversion.genericconverter;

import lombok.NonNull;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Set;

public class StringToTimeGenericConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(
                new ConvertiblePair(String.class, LocalDate.class),
                new ConvertiblePair(String.class, YearMonth.class),
                new ConvertiblePair(String.class, Year.class)
        );
    }

    @Override
    public Object convert(Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        if (!(source instanceof String input)) {
            throw new IllegalArgumentException("Source must be a String, but was: " + source.getClass().getName());
        }

        var targetClass = targetType.getType();
        if (targetClass == LocalDate.class) {
            return LocalDate.parse(input);
        } else if (targetClass == YearMonth.class) {
            return YearMonth.parse(input);
        } else if (targetClass == Year.class) {
            return Year.parse(input);
        }

        throw new IllegalArgumentException("Unsupported conversion from " + sourceType.getName() + " to " + targetType.getName());
    }
}
