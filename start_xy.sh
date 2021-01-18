#!/bin/bash

cp ./src/main/resources/application.khxy.yaml ./src/main/resources/application.yaml &&
mvn clean package spring-boot:repackage -Pboot &&
java -jar target/ROOT.war
