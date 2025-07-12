# The IoC Container

> Exploring the Inversion of Control (IoC) Container in Spring Framework — the engine behind dependency injection.

---

## Overview

The IoC container is Spring’s core engine for managing object lifecycles and dependencies. It creates, configures, and
wires beans — letting you focus on business logic instead of manual object management.

---

## Features

| Module                                  | Description                                                                                                                       |
|-----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| Introduction to IoC and Beans           | Understand the concept of IoC and how beans serve as the fundamental building blocks managed by the container.                    |
| Container Overview                      | Explore key container types: BeanFactory and ApplicationContext, and their roles in managing beans.                               |
| Bean Definitions & Dependencies         | Learn how to define beans, inject dependencies (constructor, setter, field), and manage bean scopes (singleton, prototype, etc.). |
| Bean Customization                      | Customize beans via lifecycle callbacks, initialization methods, destruction methods, and bean inheritance.                       |
| Container Extension Points              | Enhance the container using BeanPostProcessor, FactoryBean, and other extension interfaces.                                       |
| Annotation-Based Configuration          | Use annotations like @Component, @Autowired, and @Qualifier to configure beans declaratively.                                     |
| Classpath Scanning                      | Automatically detect and register beans using @ComponentScan.                                                                     |
| JSR-330 Support                         | Integrate standard Java annotations such as @Inject, @Named, and @Qualifier.                                                      |
| Java-based Configuration                | Define beans using @Configuration and @Bean methods instead of XML.                                                               |
| Environment Abstraction                 | Access and manage environment properties, profiles, and configuration variables.                                                  |
| Load-Time Weaving                       | Register LoadTimeWeaver for advanced use cases such as weaving AOP aspects during class loading.                                  |
| Advanced Features of ApplicationContext | Leverage internationalization, event publishing, and resource loading.                                                            |
| BeanFactory API                         | Low-level IoC container interface for advanced use cases and manual bean access.                                                  |

---

## Reference

* [Spring Docs - IoC Container and Beans](https://docs.spring.io/spring-framework/reference/core/beans.html)

