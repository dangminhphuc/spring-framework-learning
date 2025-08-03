package com.dangminhphuc.dev.aop;

import com.dangminhphuc.dev.service.ConsoleLoggerService;
import com.dangminhphuc.dev.MyService;
import org.aopalliance.aop.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AOPAPITest {

    @Nested
    class AsMainMethod {
        @Test
        @DisplayName("Main method")
        void mainTest() {
            MyService service = new MyService();

            Pointcut pointcut = new ActionPerformPointcut();
            Advice advice = new MyBeforeAdvice(new ConsoleLoggerService());

            Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setTarget(service);
            proxyFactory.addAdvisor(advisor);

            MyService proxy = (MyService) proxyFactory.getProxy();
            proxy.performAction();
        }
    }
}
