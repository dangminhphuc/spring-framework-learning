package com.dangminhphuc.dev.error.customize;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bar {
    @Min(value = 1, message = "{bar.number.min}")
    private int number;

    @NotNull(message = "{bar.string.notnull}")
    private String string;
}
