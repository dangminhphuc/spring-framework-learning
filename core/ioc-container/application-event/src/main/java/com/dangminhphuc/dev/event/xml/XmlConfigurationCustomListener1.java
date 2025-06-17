package com.dangminhphuc.dev.event.xml;

import com.dangminhphuc.dev.event.EventCustomize1st;
import org.springframework.context.ApplicationListener;

public class XmlConfigurationCustomListener1 implements ApplicationListener<EventCustomize1st> {

    public XmlConfigurationCustomListener1() {
    }

    @Override
    public void onApplicationEvent(EventCustomize1st event) {
        // NOOP
    }

}
