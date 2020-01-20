#!/bin/sh

echo "Starting StressTest Agent with Configuration Service :  $CONFIGSERVER_URI";
echo "********************************************************"
java -Dspring.cloud.config.uri=$CONFIGSERVER_URI -Dspring.profiles.active=$PROFILE -Dspring.datasource.url="$RESULTS_DB_URI" -Decorz.jmeter.home=${JMETER_HOME} -Decorz.jmeter.home.bin=${JMETER_BIN_DIR} -Decorz.jmeter.fs.base.directory=${JMETER_FS_BASE} -Decorz.lb.ip=${LB_IP} -Decorz.jmeter.engine.cp=${JMETER_CP} -jar ${JAR_DIR}/@project.build.finalName@.jar  > spring.log 2>&1
