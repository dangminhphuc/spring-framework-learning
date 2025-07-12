package com.dangminhphuc.dev.beans.advance.annotation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component("autowireConstructorBean")
public class AutowireConstructorBean {
    private final String mode;
    private final SimpleBean1st simpleBean;

    @Autowired
    public AutowireConstructorBean(@Value("autowireConstructor") String mode,
                                   SimpleBean1st simpleBean) {
        this.mode = mode;
        this.simpleBean = simpleBean;
    }

}
