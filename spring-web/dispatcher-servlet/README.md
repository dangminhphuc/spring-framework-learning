# DispatcherServlet

The DispatcherServlet is the Front Controller in Spring MVC. It receives every incoming HTTP request and delegates the
processing to specialized components inside the `WebApplicationContext`.

DispatcherServlet itself does not contain business logic. Instead, it coordinates the request lifecycle by looking up
special bean types that handle different responsibilities such as mapping requests, executing controllers, resolving
views, and handling exceptions.

## Request Flow Overview

```
HTTP Request 
    │ 
    ▼ 
DispatcherServlet 
    │ 
    ├─► HandlerMapping → find Controller 
    ├─► HandlerAdapter → invoke Controller 
    ├─► (Exception?) → HandlerExceptionResolver 
    ├─► ViewResolver → render View 
    │ 
    ▼ 
HTTP Response
```

## Special Bean Types

### HandlerMapping

Finds the appropriate handler (controller) for an incoming request.

Determines which method or controller should process the request.

### HandlerAdapter

Executes the chosen handler.

Provides flexibility so that different handler types (e.g., annotated controllers, functional handlers) can be invoked
in a uniform way.

### HandlerExceptionResolver

Resolves exceptions thrown during handler execution.

1. extends `SimpleMappingExceptionResolver`
- alternatively, use @Bean SimpleMappingExceptionResolver or XML configuration
- required: `InternalResourceViewResolver`

### ViewResolver

Translates a view name returned by a controller into an actual View object (e.g., JSP, Thymeleaf, JSON renderer).

Responsible for rendering the final response.

### LocaleResolver / LocaleContextResolver

Determines the locale (language, region) of the current request.

Enables internationalization (i18n) support.

### ThemeResolver

Chooses a theme (look-and-feel) for the view layer.

Useful for customizable UI styles.

### MultipartResolver

Handles multipart requests, such as file uploads.

Parses request data before passing it to controllers.

### FlashMapManager

Manages flash attributes, which allow attributes to survive a redirect.

Commonly used for passing messages between requests.

