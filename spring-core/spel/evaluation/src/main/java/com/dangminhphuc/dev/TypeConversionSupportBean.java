package com.dangminhphuc.dev;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TypeConversionSupportBean {
    private int number;
    private String string;
    private Nullable nullable;
    private List<Boolean> booleans;
}
