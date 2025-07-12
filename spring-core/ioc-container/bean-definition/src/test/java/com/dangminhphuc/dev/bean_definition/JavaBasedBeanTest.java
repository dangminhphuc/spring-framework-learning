package com.dangminhphuc.dev.bean_definition;


import com.dangminhphuc.dev.bean_definition.java.Bean3rd;
import com.dangminhphuc.dev.bean_definition.java.JavaBasedConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaBasedBeanTest {
    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaBasedConfiguration.class);

    @Test
    public void javaBased() {
        Bean3rd bean = this.context.getBean(Bean3rd.class);

        assertEquals("Hello Paul", bean.getMessage());
    }
}
