package com.dangminhphuc.dev.databinder;

import jakarta.validation.constraints.Min;

public class Bar {

    @Min(value = 1, message = "{validation.number.min}")
    private int number;
}
