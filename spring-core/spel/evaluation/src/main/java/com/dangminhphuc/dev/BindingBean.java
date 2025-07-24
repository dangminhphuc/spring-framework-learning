package com.dangminhphuc.dev;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BindingBean {
    private int number;
    private String string;
    private Object object;
    private Nullable nullable;
    private List<Boolean> booleans;
}
