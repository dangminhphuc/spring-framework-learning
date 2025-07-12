package com.dangminhphuc.dev.databinding;

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
        if (name.equals("textCustomized")) {
            return this.input.get("text_2nd");
        }
        return this.input.get(name);
    }

    @Override
    public Set<String> getNames() {
        return this.input.keySet();
    }
}
