package com.dangminhphuc.dev;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExampleBean {
    private int number;
    private String string;
    private Bar bar;
    private List<Foo> fooList;
    private List<Boolean> booleans;
    private Map<String, String> map;
}