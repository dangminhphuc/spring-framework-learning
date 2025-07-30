package com.dangminhphuc.dev.aop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
public class AopTest {
    private MyService proxiedService;
    private LoggerService mockLoggerService;

    @BeforeEach
    public void setUpStreams() {
        // 1. Create the target object
        MyService target = new MyService();

        // 2. Create the mock collaborator
        this.mockLoggerService = mock(LoggerService.class);

        // 3. Create the aspect instance, injecting the mock
        LoggingAspect aspect = new LoggingAspect(mockLoggerService);

        // 4. Create the proxy factory
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        factory.addAspect(aspect);

        // 5. Get the proxied object
        this.proxiedService = factory.getProxy();
    }

    @Test
    public void testAop() {
        this.proxiedService.performAction("TEST");

        verify(mockLoggerService).log("Logging before method execution!");
    }
}
