# Core Technologies of the Spring Framework

> Core technologies of the Spring Framework — the foundation for all other modules.

---

## Overview

This module contains essential infrastructure features of the Spring Framework. These features provide the foundation
for dependency injection, resource loading, type conversion, expression parsing, AOP, and more.

Spring Core is the heart of the framework, and learning it deeply helps you understand how higher-level modules like
Spring Boot and Spring Data work under the hood.

---

---

## Core Concepts

| Feature                     | Description                                                                       |
|-----------------------------|-----------------------------------------------------------------------------------|
| **IoC Container**           | Dependency injection and bean lifecycle management                                |
| **Resources**               | Unified API for accessing classpath, URL, file resources                          |
| **Validation & Conversion** | Data binding, validation, type conversion via `PropertyEditor`, `Converter`, etc. |
| **SpEL**                    | Dynamic expression language for manipulating object graphs                        |
| **AOP Basics**              | Cross-cutting concerns via proxies and method interception                        |
| **Spring AOP APIs**         | Programmatic and declarative support for AOP with annotations or XML              |
| **Null-safety**             | Support for `@NonNull`, `@Nullable` and null contract annotations                 |
| **Data Buffers & Codecs**   | Reactive abstractions for buffer and encoding/decoding (WebFlux support)          |
| **Logging**                 | Commons Logging bridge with support for Log4j, SLF4J, etc.                        |
| **AOT**                     | Support for GraalVM native images via ahead-of-time optimizations                 |

---

## Related

* [Spring Framework Reference - Core Technologies](https://docs.spring.io/spring-framework/reference/core.html)

---
