package com.dangminhphuc.dev.beans.advance;

import com.dangminhphuc.dev.beans.advance.java.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class JavaConfiguration {
    // method name is bean name

    @Bean
    public SimpleBean1st singletonBean() {
        return new SimpleBean1st();
    }

    @Bean
    @Scope("prototype")
    public SimpleBean1st prototypeBean() {
        return new SimpleBean1st();
    }

    @Bean
    public ConstructorBean constructorBean() {
        return new ConstructorBean("Constructor Bean", singletonBean());
    }

    @Bean
    public SetterBasedBean setterBean() {
        SetterBasedBean bean = new SetterBasedBean();
        bean.setId("Setter");
        bean.setSingletonBean(singletonBean());
        return bean;
    }

    @Bean
    public LifecycleBean lifecycleBean() {
        return new LifecycleBean();
    }

    @Bean
    public AutowireBean autowireBean() {
        return new AutowireBean(singletonBean());
    }

}

