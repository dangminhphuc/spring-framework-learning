# Evaluation

## `ExpressionParser` interface

The ExpressionParser interface is responsible for parsing an expression string.
It provides methods to parse expressions and create `Expression` objects.

```java
ExpressionParser parser = new SpelExpressionParser();
Expression exp = parser.parseExpression("'Hello World'");
String message = (String) exp.getValue();
```

## `EvaluationContext`

### `SimpleEvaluationContext` class

Exposes a subset of essential SpEL language features and configuration options.

When creating a `SimpleEvaluationContext` you need to choose the level of support that you need for data binding in SpEL
expressions:

* Data binding for read-only access
* Data binding for read and write access
* A custom PropertyAccessor (typically not reflection-based), potentially combined with a DataBindingPropertyAccessor

### `StandardEvaluationContext` class