package com.dangminhphuc.dev.beans.advance.java;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class AutowireBean {
    private String id;
    private SimpleBean1st singletonBean;

    @Autowired
    public AutowireBean(SimpleBean1st singletonBean) {
        this.singletonBean = singletonBean;
    }
}
