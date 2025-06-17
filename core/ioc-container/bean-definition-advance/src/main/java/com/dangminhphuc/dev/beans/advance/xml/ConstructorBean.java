package com.dangminhphuc.dev.beans.advance.xml;

public class ConstructorBean {
    private final String property;
    private final SimpleBean simpleBean;

    public ConstructorBean(String property, SimpleBean simpleBean) {
        this.property = property;
        this.simpleBean = simpleBean;
    }

    public String getProperty() {
        return property;
    }

    public SimpleBean getSimpleBean() {
        return simpleBean;
    }
}
