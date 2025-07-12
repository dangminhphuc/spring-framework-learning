# bean-definition

## Overview

This module dives into the various ways to define Spring beans. It covers XML configuration, annotation-based
definitions, and Java-based configuration using `@Configuration` and `@Bean`. Understanding these methods is key to
managing beans effectively in Spring applications.

## Goals

- Learn how to define beans via XML, annotations, and Java config
- Understand the pros and cons of each method
- Practice declaring beans with `@Component`, `@Service`, `@Repository`, and `@Controller`
- Get familiar with `@Bean` methods in Java config classes

## Key Concepts

- **XML Configuration:** Traditional way using `<bean>` elements to declare beans and set properties.
- **Annotation-Based Configuration:** Using stereotype annotations and classpath scanning to detect beans automatically.
- **Java Configuration:** Using `@Configuration` classes and `@Bean` methods for type-safe and refactor-friendly bean
  definitions.

## Usage Example

```xml
<!-- XML bean definition (beans.xml)-->
<bean2nd id="service" class="com.dangminhphuc.dev.Service"/>
```

```java
// Annotation example
@Component
public class Service {
}
```

```java
// Java config example
@Configuration
public class AppConfig {
    @Bean
    public Service service() {
        return new Service();
    }
}
```
