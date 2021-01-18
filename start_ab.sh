#!/bin/bash

cp ./src/main/resources/application.drab.yaml ./src/main/resources/application.yaml &&
mvn clean package spring-boot:repackage -Pboot &&
java -jar target/ROOT.war
