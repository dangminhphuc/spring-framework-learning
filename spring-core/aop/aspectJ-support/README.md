# AspectJ Support

## Enabling @AspectJ Support

- Using `@EnableAspectJAutoProxy` annotation in a configuration class enables AspectJ support in a Spring application.

## Declaring an Aspect

## Declaring a Pointcut

Naming conventions for pointcuts are crucial for maintainability and readability. Here are some common naming
conventions you can follow:

1. Name by Architectural Layer

This is one of the most common and useful conventions. You define pointcuts that select all join points within a
specific architectural layer of your application.

* webLayer()
* serviceLayer()
* dataAccessLayer()

Example:

```java

@Pointcut("within(com.dangminhphuc.dev.web..*)")
public void webLayer() {
}

@Pointcut("within(com.dangminhphuc.dev.service..*)")
public void serviceLayer() {
}
```

2. Name by Operation Type

Name the pointcut after the type of methods it's supposed to intercept. This is useful for creating cross-cutting
concerns like logging, auditing, or transactions.

* loggableOperations()
* auditableMethods()
* transactionalOperations()

Example:

```java
// A pointcut for all public methods
@Pointcut("execution(public * *(..))")
public void publicMethods() {
}

// A pointcut for methods annotated with @Transactional
@Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
public void transactionalOperations() {
}
```

3. Name by Method Pattern

If your pointcut selects methods based on their names, the pointcut name should reflect that pattern.

* getters()
* setters()
* anyPublicExecution()

Example:

```java

@Pointcut("execution(* get*())")
public void getters() {
}

@Pointcut("execution(* set*(*))")
public void setters() {
}
```

4. Combine and Name Logically

When you combine pointcuts, the new name should reflect the combination.

* serviceLayerExecution()
* secureDataAccess()

Example:

```java

@Aspect
public class SystemArchitecture {

    @Pointcut("within(com.dangminhphuc.dev.service..*)")
    public void serviceLayer() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethods() {
    }

    /**
     * A pointcut that matches any public method in the service layer.
     */
    @Pointcut("serviceLayer() && publicMethods()")
    public void publicServiceMethodExecution() {
    }
}

@Aspect
@Component
public class LoggingAspect {

    // Use the named pointcut from the central definition class
    @Before("com.dangminhphuc.dev.aop.SystemArchitecture.publicServiceMethodExecution()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Executing: " + joinPoint.getSignature().toShortString());
    }
}
```

## Declaring an Advice

* `@Before`: Executes before the method execution.
* `@After`: Executes after the method execution, regardless of its outcome.
* `@AfterReturning`: Executes after the method execution only if it completes successfully.
* `@AfterThrowing`: Executes if the method throws an exception.
* `@Around`: Wraps the method execution, allowing you to control when it is invoked