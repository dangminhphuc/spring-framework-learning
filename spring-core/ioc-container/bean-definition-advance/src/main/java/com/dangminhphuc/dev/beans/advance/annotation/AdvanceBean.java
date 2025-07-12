package com.dangminhphuc.dev.beans.advance.annotation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Getter
@Component("advanceBean")
public class AdvanceBean {

    @Autowired
    private SimpleBean2nd primaryBean;

    @Autowired
    @Qualifier("qualifiedBean")
    private SimpleBean2nd qualifiedBean;

    public AdvanceBean() {
    }
}
