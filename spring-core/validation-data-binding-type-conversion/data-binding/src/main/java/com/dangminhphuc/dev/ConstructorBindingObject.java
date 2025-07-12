package com.dangminhphuc.dev;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
public class ConstructorBindingObject {
    private final int number;
    private final String text;
    private final String textCustomized;
    private List<String> list;
    private Map<String, String> map;
    private Item item;

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Item {
        private String id;
    }
}
