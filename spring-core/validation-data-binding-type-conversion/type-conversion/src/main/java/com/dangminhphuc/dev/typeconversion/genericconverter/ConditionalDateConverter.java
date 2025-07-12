package com.dangminhphuc.dev.typeconversion.genericconverter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Set;

// TBU
public class ConditionalDateConverter implements ConditionalGenericConverter {
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return false;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Set.of();
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return null;
    }
}
