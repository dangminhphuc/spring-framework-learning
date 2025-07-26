# AOP

## Core Concepts

## AOP Proxies

**Proxy**: AOP uses proxies to intercept method calls. The proxy can be a JDK dynamic proxy or a CGLIB proxy.

- **JDK Dynamic Proxy**: Used when the target class implements at least one interface. The proxy implements the same
  interface(s).
- **CGLIB Proxy**: Used when the target class does not implement any interfaces. CGLIB creates a subclass of the target
  class.

## `@AspectJ` support

- @AspectJ refers to a style of declaring aspects as regular Java classes annotated with annotations.
- Spring interprets the same annotations as AspectJ 5, using a library supplied by AspectJ for pointcut **parsing** and
  **matching**.
- The AOP runtime is still pure Spring AOP, though, and there is no dependency on the AspectJ compiler or weaver.