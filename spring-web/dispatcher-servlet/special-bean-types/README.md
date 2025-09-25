#

1. war: run on servlet container
- implement WebApplicationInitializer
- extends AbstractAnnotationConfigDispatcherServletInitializer

How to run:
- copy /target/*.war to web server
- run `mvn jetty:run`

2. jar: run on embedded server