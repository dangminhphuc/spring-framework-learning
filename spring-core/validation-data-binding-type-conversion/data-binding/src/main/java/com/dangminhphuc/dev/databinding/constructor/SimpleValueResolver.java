package com.dangminhphuc.dev.databinding.constructor;

import org.springframework.validation.DataBinder;

import java.util.Map;
import java.util.Set;

public class SimpleValueResolver implements DataBinder.ValueResolver {
    private final Map<String, ?> input;

    public SimpleValueResolver(Map<String, ?> input) {
        this.input = input;
    }

    @Override
    public Object resolveValue(String name, Class<?> type) {
        if (name.equals("string2nd")) {
            return this.input.get("stringCustomized");
        }
        return this.input.get(name);
    }

    @Override
    public Set<String> getNames() {
        return this.input.keySet();
    }
}
