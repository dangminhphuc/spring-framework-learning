#

## Bean Post Processor

💡 Challenge: Dynamic Bean Customization with BeanPostProcessor

### Context:

You are building a service management system with multiple services like EmailService, NotificationService, and
ReportService.

The requirement is:

* Before each service bean is initialized, add the prefix "Service: " to the bean name.
* After each service bean is initialized, print the message: "Service [Name] is ready for use!"

### Specific Requirements:

1. Create an interface `Service` with a method `execute()`.
2. Implement at least 3 service classes.
3. Create a `BeanPostProcessor` that:
   * Before Initialization: Adds the prefix "Service: " to the bean name. 
   * After Initialization: Prints the message "Service [Name] is ready for use!".
4. Write a Main class to run the application and call the execute() method of each service.