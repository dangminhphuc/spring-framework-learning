package com.dangminhphuc.dev.databinding.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * Represents an object with various properties for data binding and type conversion testing. <br/>
 * This class includes simple properties, a nested object, a list, and a map.
 * <p><b>Constructor params</b> used to demonstrate constructor binding</p>
 * <p><b>Setter methods</b> used to demonstrate property binding</p>
 */

@Getter
@Setter             // Using @Setter to allow property binding
@ToString
public class Bar {
    private int number;
    private String string;
    private String string2nd;
    private List<String> list;
    private Map<String, String> map;
    private Foo foo;

    public Bar() {
    }

    @Getter
    @Setter
    @ToString
    public static class Foo {
        private String id;
    }
}
