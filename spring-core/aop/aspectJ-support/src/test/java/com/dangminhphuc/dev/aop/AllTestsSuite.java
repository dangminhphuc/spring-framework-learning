package com.dangminhphuc.dev.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    MyServiceTest.class,
    ConsoleLoggerServiceTest.class,
    LoggingAspectTest.class,
    AopTest.class,
    SpringIntegrationTest.class,
    AdvancedAopTest.class
})
@DisplayName("AspectJ Support Module - Complete Test Suite")
public class AllTestsSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}