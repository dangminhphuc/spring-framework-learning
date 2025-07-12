package com.dangminhphuc.dev.beans.advance.annotation;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component("singletonBean")
public class SimpleBean1st {
    public SimpleBean1st() {
    }
}
