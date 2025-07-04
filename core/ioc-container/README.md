# The IoC Container

> Exploring the Inversion of Control (IoC) Container in Spring Framework — the engine behind dependency injection.

---

## Overview

This module dives into the **Spring IoC container** — the foundation of the framework’s dependency injection mechanism.
We explore how Spring manages object creation, wiring, lifecycle, scopes, and configuration through various styles: XML,
annotations, and Java config.

---

## Key Concepts

| Topic                       | Description                                                        |
|-----------------------------|--------------------------------------------------------------------|
| **Container Overview**      | What is IoC? What is the container?                                |
| **Bean Overview**           | What is a bean? Lifecycle basics                                   |
| **Dependency Injection**    | Constructor, setter, and field-based wiring                        |
| **Scopes**                  | Define bean lifecycle: singleton, prototype, etc.                  |
| **Bean Customization**      | Lifecycle callbacks, `BeanPostProcessor`, `@PostConstruct`, etc.   |
| **Bean Inheritance**        | Allow sharing property values/configs across bean definitions      |
| **Extension Points**        | Hooks to customize container behavior (`BeanFactoryPostProcessor`) |
| **Annotation Config**       | Use `@Component`, `@Autowired`, `@Value`, etc.                     |
| **Classpath Scanning**      | Automatically detect and register beans via scanning               |
| **JSR-330**                 | Standard annotations from `javax.inject` like `@Inject`, `@Named`  |
| **Java Config**             | Use `@Configuration` and `@Bean` to register beans via code        |
| **Environment Abstraction** | Handle profiles, properties, and system envs                       |
| **LoadTimeWeaver**          | Supports class instrumentation for JPA, AOP proxies                |
| **ApplicationContext**      | Enhanced `BeanFactory` with internationalization, events, etc.     |
| **BeanFactory API**         | The lower-level, minimal interface to the container                |

---

## Reference

* [Spring Docs - IoC Container and Beans](https://docs.spring.io/spring-framework/reference/core/beans.html)

