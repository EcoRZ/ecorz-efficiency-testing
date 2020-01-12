#!/bin/sh
echo "********************************************************"
echo "Starting Configuration Server"
echo "********************************************************"
java -Dspring.cloud.config.server.git.username=$REG_LOGIN_USER -Dspring.cloud.config.server.git.password=$REG_LOGIN_PW -jar /usr/local/configserver/@project.build.finalName@.jar
