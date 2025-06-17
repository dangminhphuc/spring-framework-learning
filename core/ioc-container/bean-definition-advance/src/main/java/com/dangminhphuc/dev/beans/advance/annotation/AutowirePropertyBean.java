package com.dangminhphuc.dev.beans.advance.annotation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AutowirePropertyBean {
    @Value("autowireByType")
    private String mode;

    @Autowired  // autowire byType
    private SimpleBean1st simpleBean;

    public AutowirePropertyBean() {
    }
}
