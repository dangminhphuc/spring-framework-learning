package com.dangminhphuc.dev.beanvalidation.jakarta;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Bar {

    @Min(value = 0, message = "Min value must not be less than 0")
    private int min;

    @Max(value = 100, message = "Max value must not exceed 100")
    private int max;

    @NotBlank(message = "String must not be blank")
    private String string;

    @Size(min = 1, max = 3, message = "Size must be between 1 and 3")
    private List<String> size;

    @Email(message = "Email must be valid")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Pattern must match alphanumeric characters only")
    private String pattern;

    @NotNull
    private Foo foo;
}

class Foo {

}