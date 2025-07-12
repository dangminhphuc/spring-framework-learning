package com.dangminhphuc.dev.beans.advance.java;


import lombok.Getter;

@Getter
public class ConstructorBean {
    private final String id;
    private final SimpleBean1st singletonBean;

    public ConstructorBean(String id, SimpleBean1st singletonBean) {
        this.id = id;
        this.singletonBean = singletonBean;
    }
}
