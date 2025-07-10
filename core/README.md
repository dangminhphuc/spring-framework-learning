# Core Technologies of the Spring Framework

> Core technologies of the Spring Framework — the foundation for all other modules.

---

## Overview

This module introduces the core technologies of the Spring Framework — the essential building blocks that enable
dependency injection, aspect-oriented programming, expression evaluation, resource abstraction, and more. These
components provide the foundation for building modern, modular, and maintainable Spring applications.

Key features included in this module:

- IoC Container
  Central to Spring, the Inversion of Control container manages object creation, configuration, and dependency
  injection.

- Resources
  A consistent abstraction for accessing files and other external resources across different environments (classpath,
  filesystem, URLs, etc.).

- Validation & Conversion
  Type conversion and validation infrastructure for binding, form processing, and data transformation.

- Spring Expression Language (SpEL)
  A powerful expression language used to dynamically access, query, and manipulate object graphs at runtime.

- AOP Basics
  Core support for aspect-oriented programming to separate cross-cutting concerns such as logging, security, and
  transactions.

- Spring AOP API
  Programmatic and annotation-based APIs for defining and applying aspects. Integrates seamlessly with AspectJ for
  advanced scenarios.

- Null-safety
  Support for @Nullable and @NonNull annotations across the framework to aid static analysis and improve code safety.

- Data Buffers & Codecs
  Infrastructure for efficient I/O handling in reactive and non-blocking contexts, commonly used in WebFlux and
  messaging.

- Logging
  Unified logging abstraction built on SLF4J, allowing consistent logging behavior with pluggable backends.

- Ahead-of-Time (AOT) Processing
  Build-time processing for optimizing Spring applications, especially for native image generation with GraalVM.

These technologies form the backbone of Spring and are essential for both traditional applications and modern
cloud-native microservices.

---

## Related

* [Spring Framework Reference - Core Technologies](https://docs.spring.io/spring-framework/reference/core.html)
