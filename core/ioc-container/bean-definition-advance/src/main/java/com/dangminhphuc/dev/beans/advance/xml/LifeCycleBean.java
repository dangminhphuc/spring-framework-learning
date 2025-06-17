package com.dangminhphuc.dev.beans.advance.xml;

public class LifeCycleBean {
    public LifeCycleBean() {
        System.out.println("ON CONSTRUCTOR");
    }

    public void onInit() {
        System.out.println("ON INIT");
    }

    public void onDestroy() {
        System.out.println("ON DESTROY");
    }
}
