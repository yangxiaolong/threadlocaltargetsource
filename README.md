# README #

Accompanying source code for blog entry at https://tech.asimio.net/2024/09/12/Propagating-data-to-Async-Threads-with-ThreadLocalTargetSource-TaskDecorator-Spring-Boot.html

### Requirements ###

* Java 17+
* Maven 3.3+

### Building and running from command line ###

```
mvn spring-boot:run
```

### Available endpoints ###

```
curl -H "X-TENANT-ID:tenant_1" http://localhost:8080/demo/sync
curl -H "X-TENANT-ID:tenant_2" http://localhost:8080/demo/async
```

Look at the application logs, and/or run `DemoControllerIntegrationTest` class.

### Who do I talk to? ###

* ootero at asimio dot net
* https://www.linkedin.com/in/ootero
