package com.dangminhphuc.dev.typeconversion.genericconverter;

import lombok.NonNull;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Set;

public class TimeToStringGenericConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of(
                new ConvertiblePair(LocalDate.class, String.class),
                new ConvertiblePair(YearMonth.class, String.class),
                new ConvertiblePair(Year.class, String.class)
        );
    }

    @Override
    public Object convert(Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        Class<?> sourceTypeType = sourceType.getType();
        if (sourceTypeType == LocalDate.class) {
            return source.toString();
        } else if (sourceTypeType == YearMonth.class) {
            return source.toString();
        } else if (sourceTypeType == Year.class) {
            return source.toString();
        }

        throw new IllegalArgumentException("Unsupported conversion from " + sourceType.getName() + " to " + targetType.getName());
    }
}
