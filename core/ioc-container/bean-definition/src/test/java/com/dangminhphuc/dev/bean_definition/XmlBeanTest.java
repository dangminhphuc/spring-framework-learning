package com.dangminhphuc.dev.bean_definition;

import com.dangminhphuc.dev.bean_definition.xml.Bean1st;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlBeanTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("xml-beans.xml");

    @Test
    @DisplayName("Get bean by bean name")
    public void getBean() {
        Bean1st bean = this.context.getBean("xmlBean", Bean1st.class);

        // assert
        assertEquals("Hello Paul", bean.getMessage());
    }
}