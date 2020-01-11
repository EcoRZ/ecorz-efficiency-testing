#!/bin/sh

echo "Starting StressTest Agent with Configuration Service :  $CONFIGSERVER_URI";
echo "********************************************************"
java -Dspring.cloud.config.uri=$CONFIGSERVER_URI -Dspring.profiles.active=$PROFILE -Dspring.datasource.url="$RESULTS_DB_URI" -jar /usr/local/stresstest_agent/@project.build.finalName@.jar
