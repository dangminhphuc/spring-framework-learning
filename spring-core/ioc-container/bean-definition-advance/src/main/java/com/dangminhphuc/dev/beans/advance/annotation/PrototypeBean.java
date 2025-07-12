package com.dangminhphuc.dev.beans.advance.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component("prototypeBean")
public class PrototypeBean {
    public PrototypeBean() {
    }
}
