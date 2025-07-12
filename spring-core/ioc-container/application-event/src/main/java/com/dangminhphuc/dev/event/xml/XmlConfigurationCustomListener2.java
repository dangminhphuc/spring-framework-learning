package com.dangminhphuc.dev.event.xml;

import com.dangminhphuc.dev.event.EventCustomize1st;
import org.springframework.context.ApplicationListener;

public class XmlConfigurationCustomListener2 implements ApplicationListener<EventCustomize1st> {

    public XmlConfigurationCustomListener2() {
    }

    @Override
    public void onApplicationEvent(EventCustomize1st event) {
        // NOOP
    }
}
