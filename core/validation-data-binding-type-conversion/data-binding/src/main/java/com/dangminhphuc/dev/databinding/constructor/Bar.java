package com.dangminhphuc.dev.databinding.constructor;

import lombok.Getter;
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
@ToString
public class Bar {
    private final int number;
    private final String string;
    private final String string2nd;
    private final List<String> list;
    private final Map<String, String> map;
    private final Foo foo;

    // Used to allow constructor binding
    public Bar(int number, String string, String string2nd, List<String> list, Map<String, String> map, Foo foo) {
        this.number = number;
        this.string = string;
        this.string2nd = string2nd;
        this.list = list;
        this.map = map;
        this.foo = foo;
    }

    @Getter
    @ToString
    public static class Foo {
        private final String id;

        public Foo(String id) {
            this.id = id;
        }
    }

}
