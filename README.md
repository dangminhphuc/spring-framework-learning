# Spring Learning Project

---

## Overview

This repository is a structured, test-centric learning path through the Spring ecosystem. Instead of using `main()`
methods, all features are explored using **unit tests**, ensuring isolated, verifiable, and repeatable learning
examples.

Each major Spring module (Core, Boot, Data, etc.) is represented as a Maven submodule. Within each, related features are
broken down into smaller, focused submodules.

---

## Structure

```markdown
spring-framework/
├── pom.xml                         
├── [spring-core](./core)/                    
│ ├── [ioc-container](./core/ioc-container)/              
│ └── README.md
└── README.md
```

---

## Goals

* Deepen understanding of core Spring modules through real examples
* Learn by writing expressive, focused unit tests
* Avoid using `main()` — embrace testing as the entry point
* Structure everything into a maintainable multi modules Maven project

---

## Environment

* Java 17+
* Maven 3.9+
* JUnit 5
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

---

Ready to explore Spring the smart way. Let's go!
