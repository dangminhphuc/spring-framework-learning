# Spring Framework Learning

---

## Overview

This repository is a structured through the Spring ecosystem. Instead of using `main()`
methods, all features are explored using **unit tests**, ensuring isolated, verifiable, and repeatable learning
examples.

Each major Spring module (Core, Boot, Data, etc.) is represented as a Maven submodule. Within each, related features are
broken down into smaller, focused submodules.

---

## Structure

```markdown
spring-framework/
├── spring-core/              
│ ├── ioc-container/
│ ├── validation-data-binding-type-conversion/
│ └── ...
└── ...
```

---

## Environment

* Java 17+
* Maven 3.13+
* JUnit 5.11+
* IDE: IntelliJ IDEA (recommended)

Optional:

* Lombok (enabled in IDE)

---

## How to Run

> No `main()` methods here — just tests.

From root directory:

```bash
# Run all tests
mvn clean test

# Run tests for a specific module
cd $path
mvn test
```

Or run directly in IntelliJ via the test class runner.
