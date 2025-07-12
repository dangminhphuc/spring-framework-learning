package com.dangminhphuc.dev.beans.advance.annotation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component("autowireByNameBean")
public class AutowireByNameBean {
    @Value("autowireByName")
    private String mode;

    @Autowired  // autowire byName
    private SimpleBean1st singletonBean;

    public AutowireByNameBean() {
    }
}
