package com.dangminhphuc.dev.jdbccore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Foo {
    private long id;
    private int number;
    private String string;
    private boolean bool;
    private Date date;
    private BigDecimal amount;

    public Foo() {
    }


}
