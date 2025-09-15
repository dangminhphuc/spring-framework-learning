[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] Spring Framework                                                   [pom]
[INFO] Spring Core                                                        [pom]
[INFO] Spring IoC Container                                               [pom]
[INFO] Bean Definition                                                    [jar]
[INFO] Bean Definition Advance                                            [jar]
[INFO] Extension Points                                                   [jar]
[INFO] Internationalization                                               [jar]
[INFO] Application Event                                                  [jar]
[INFO] Validation, Data Binding and Type Conversion                       [pom]
[INFO] Data Binding                                                       [jar]
[INFO] Type Conversion                                                    [jar]
[INFO] spel                                                               [pom]
[INFO] Aspect Oriented Programming                                        [pom]
[INFO] AspectJ support                                                    [jar]
[INFO] AOP API                                                            [jar]
[INFO] Data Access                                                        [pom]
[INFO] Transaction Management                                             [pom]
[INFO] Transaction XML-Based Configuration                                [jar]
[INFO] Transaction Bound Events                                           [jar]
[INFO] DAO Support                                                        [jar]
[INFO] JDBC with JDBC                                                     [jar]
[INFO] web                                                                [pom]
[INFO] web-mvc                                                            [pom]
[INFO] dispatcher-servlet                                                 [jar]
[INFO] 
[INFO] -----------< com.dangminhphuc.dev:spring-framework-learning >-----------
[INFO] Building Spring Framework 1.0.0                                   [1/24]
[INFO]   from pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for Spring Framework 1.0.0:
[INFO] 
[INFO] Spring Framework ................................... FAILURE [  0.000 s]
[INFO] Spring Core ........................................ SKIPPED
[INFO] Spring IoC Container ............................... SKIPPED
[INFO] Bean Definition .................................... SKIPPED
[INFO] Bean Definition Advance ............................ SKIPPED
[INFO] Extension Points ................................... SKIPPED
[INFO] Internationalization ............................... SKIPPED
[INFO] Application Event .................................. SKIPPED
[INFO] Validation, Data Binding and Type Conversion ....... SKIPPED
[INFO] Data Binding ....................................... SKIPPED
[INFO] Type Conversion .................................... SKIPPED
[INFO] spel ............................................... SKIPPED
[INFO] Aspect Oriented Programming ........................ SKIPPED
[INFO] AspectJ support .................................... SKIPPED
[INFO] AOP API ............................................ SKIPPED
[INFO] Data Access ........................................ SKIPPED
[INFO] Transaction Management ............................. SKIPPED
[INFO] Transaction XML-Based Configuration ................ SKIPPED
[INFO] Transaction Bound Events ........................... SKIPPED
[INFO] DAO Support ........................................ SKIPPED
[INFO] JDBC with JDBC ..................................... SKIPPED
[INFO] web ................................................ SKIPPED
[INFO] web-mvc ............................................ SKIPPED
[INFO] dispatcher-servlet ................................. SKIPPED
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.150 s
[INFO] Finished at: 2025-09-13T22:36:08+07:00
[INFO] ------------------------------------------------------------------------
[ERROR] Unknown lifecycle phase "./web/web-mvc/dispatcher-servlet/". You must specify a valid lifecycle phase or a goal in the format <plugin-prefix>:<goal> or <plugin-group-id>:<plugin-artifact-id>[:<plugin-version>]:<goal>. Available lifecycle phases are: pre-clean, clean, post-clean, validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy, pre-site, site, post-site, site-deploy. -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/LifecyclePhaseNotFoundException
