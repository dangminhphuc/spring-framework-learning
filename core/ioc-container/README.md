# 🧩 ioc-container

> Exploring the Inversion of Control (IoC) Container in Spring Framework — the engine behind dependency injection.

---

## 📚 Overview

This module dives into the **Spring IoC container** — the foundation of the framework’s dependency injection mechanism.
We explore how Spring manages object creation, wiring, lifecycle, scopes, and configuration through various styles: XML,
annotations, and Java config.

---

## 📦 Structure

```text
ioc-container/
├── pom.xml
├── [container-overview](container-overview)
├── bean-overview/                # What is a bean? Lifecycle basics
├── dependencies/                 # Dependency injection, autowiring
├── scopes/                       # Singleton, prototype, request, session...
├── bean-customization/           # init-method, destroy-method, lifecycle callbacks
├── bean-inheritance/             # Bean definition inheritance
├── container-extension/          # Post-processors, resolvers, factory hooks
├── annotations-config/           # Annotation-based configuration (@Component, @Autowired)
├── classpath-scanning/           # Automatic detection with @ComponentScan
├── jsr330/                       # `javax.inject` annotations support
├── java-config/                  # Java-based configuration with @Configuration
├── environment/                  # Profiles, property resolution
├── loadtime-weaver/              # Weaving for instrumentation (JPA, AOP)
├── context-capabilities/         # Extra goodies from ApplicationContext
├── beanfactory-api/              # Low-level BeanFactory exploration
└── README.md
```

---

## 🧠 Key Concepts

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

---

## 📎 Reference

* [Spring Docs - IoC Container and Beans](https://docs.spring.io/spring-framework/reference/core/beans.html)

