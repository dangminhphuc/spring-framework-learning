# Spring Core

## Container Extension Points
Container extension points are predefined integration interfaces in Spring that allow developers to extend the IoC
container without subclassing ApplicationContext classes. These interfaces enable customization of bean creation, event
handling, resource loading, and other container behaviors while maintaining code modularity and separation of concerns.

These extension points are grouped into several categories as follows:
1. Bean Lifecycle Management
* BeanFactoryPostProcessor: Modify bean definitions before initialization. 
* BeanPostProcessor: Customize beans before and after initialization. 
* InitializingBean & DisposableBean: Define initialization and destruction callbacks.

2. Event Handling
* ApplicationListener: React to application events (e.g., startup, shutdown). 
* SmartApplicationListener: Filter and respond to specific events.

3. Bean Creation
* FactoryBean: Programmatically create beans with custom logic. 
* SmartFactoryBean: Advanced bean creation with additional capabilities.

4. Resource Loading
* ResourceLoader: Load resources from file systems, classpaths, or URLs. 
* ResourcePatternResolver: Support for resource patterns (e.g., classpath*).

5. AOP Integration
* MethodInterceptor: Intercept and customize method calls. 
* Advisor: Combine advice with pointcuts for AOP.

6. Configuration and Environment
* Environment: Manage profiles and properties. 
* PropertySource: Access configuration properties from different sources.

7. Application Context Awareness
* ApplicationContextAware: Access the ApplicationContext within beans. 
* BeanNameAware: Obtain the name of the bean in the context.

8. Task Scheduling
* SchedulingConfigurer: Configure task scheduling. 
* TaskExecutor: Manage asynchronous task execution.