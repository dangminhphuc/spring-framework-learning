package com.dangminhphuc.dev.bean_definition;

import com.dangminhphuc.dev.bean_definition.annotation.Bean2nd;
import com.dangminhphuc.dev.bean_definition.annotation.AnnotationConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnnotationBeanTest {
    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnnotationConfiguration.class);

    @Test
    public void annotation() {
        Bean2nd bean = this.context.getBean(Bean2nd.class);

        assertEquals("Hello Paul", bean.getMessage());
    }
}
