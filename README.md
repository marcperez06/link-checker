---- How to use link checker ----

For use link checker dependency follow this steps

1. Include link-checker dependency on your project ([https://mvnrepository.com/artifact/io.github.marcperez06/link-checker])
- For maven:
'''
<dependency>
    <groupId>io.github.marcperez06</groupId>
    <artifactId>link-checker</artifactId>
    <version>0.0.1</version>
</dependency>
'''

- For Gradle:
'''
implementation 'io.github.marcperez06:link-checker:0.0.1'
'''

2. Call the method getReport() of LinkCheckerService
- Using default configuration, using the default properties file (described on Properties section) or with default configuration
'''
'''
- Using configuration from different properties file
'''
'''
- Creating an specific configuration using LinkCheckerConfigurationBuilder
'''
'''

** Default configuration
min depth: 1
sort not found firt: true
num threads: 1

---- Properties ----

The properties configure when report will stop at reach the minimal number specified on each property enabled,
also defines the number of threads used for parallelization and if want the report of links visited sorted 
with the results NOT_FOUND in the first positions of report under "linksVisited" property

The default path for the properties is under project directory, following the path 

'''
\resources\properties\link_checker.properties

''' 

- Properties Example:
'''
link.checker.min.depth.enabled=true\n
link.checker.min.depth=1\n

link.checker.min.interactions.enabled=true
link.checker.min.interactions=100

link.checker.min.requests.enabled=true
link.checker.min.requests=100

link.checker.num.threads=3
link.checker.sort.not.found.first=true
'''

---- Examples ----
Can find different examples on unit test in github [https://github.com/marcperez06/link-checker/tree/master/src/test/java/link_checker]

--- Github Project ----
[https://github.com/marcperez06/link-checker]

---- Author ----
Marc Pérez Rodríguez