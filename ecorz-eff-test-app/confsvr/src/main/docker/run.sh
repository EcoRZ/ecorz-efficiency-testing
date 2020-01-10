#!/bin/sh
echo "********************************************************"
echo "Starting Configuration Server"
echo "********************************************************"
javaw -jar /usr/local/configserver/@project.build.finalName@.jar
