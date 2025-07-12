package com.dangminhphuc.dev.beanvalidation.custom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Foo {
    @EvenNumber
    private int number;
}
