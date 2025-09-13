package com.dangminhphuc.dev.webmvc.dispatcher;

import org.eclipse.jetty.server.handler.ContextHandler;

public interface ServletInitializer {
    ContextHandler contextHandler();
}
