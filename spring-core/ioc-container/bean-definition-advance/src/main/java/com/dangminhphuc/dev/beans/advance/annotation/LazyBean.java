package com.dangminhphuc.dev.beans.advance.annotation;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component("lazyBean")
public class LazyBean {
    public LazyBean() {
    }
}
