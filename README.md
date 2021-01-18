# PHR  HAPI-FHIR Starter Project

see the original [Original readme](https://github.com/bumzack/hapi-fhir-jpaserver-starter/README.orig.md) for license and details.

This fork has been modified to provide
- a simple dummy not at all serious possibility to have HTTP basic auth support
- use a mysql database for persistence
 
## setup

Mysql Server required - add a database like

```
CREATE USER 'drab'@'localhost' IDENTIFIED BY 'drab';
CREATE DATABASE fhir_drab;
GRANT ALL PRIVILEGES ON fhir_drab.* TO 'drab'@'localhost';
```

```
CREATE USER 'khxy'@'localhost' IDENTIFIED BY 'khxy';
CREATE DATABASE fhir_khxy;
GRANT ALL PRIVILEGES ON fhir_khxy.* TO 'khxy'@'localhost';
```




## start server
```

mvn clean package spring-boot:repackage -Pboot && java -jar target/ROOT.war

```
