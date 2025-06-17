package com.dangminhphuc.dev.beans.advance.xml;

public class AutowireBean {

    // autowire by type
    private LifeCycleBean LIFE_CYCLE_BEAN;

    // autowire by name
    private PropertyBean propertyBean;

    public AutowireBean() {
    }

    // autowire by constructor
    public AutowireBean(PropertyBean propertyBean) {
        this.propertyBean = propertyBean;
    }

    public LifeCycleBean getLIFE_CYCLE_BEAN() {
        return LIFE_CYCLE_BEAN;
    }

    public void setLIFE_CYCLE_BEAN(LifeCycleBean LIFE_CYCLE_BEAN) {
        this.LIFE_CYCLE_BEAN = LIFE_CYCLE_BEAN;
    }

    public PropertyBean getPropertyBean() {
        return propertyBean;
    }

    public void setPropertyBean(PropertyBean propertyBean) {
        this.propertyBean = propertyBean;
    }
}
