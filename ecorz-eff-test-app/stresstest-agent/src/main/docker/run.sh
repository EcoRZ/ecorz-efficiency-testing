#!/bin/sh

arrCP=(${JMETER_CP//:/ })
cp_dump_folder=${arrCP[1]}

echo "Starting StressTest Agent with Configuration Service :  $CONFIGSERVER_URI";
echo "********************************************************"
java -Dspring.cloud.config.uri=$CONFIGSERVER_URI -Dspring.profiles.active=$PROFILE -Dspring.datasource.url="$RESULTS_DB_URI" \
-Decorz.jmeter.home=${JMETER_HOME} -Decorz.jmeter.home.bin=${JMETER_BIN_DIR} -Decorz.jmeter.fs.base.directory=${JMETER_FS_BASE} \
-Decorz.lb.ip=${LB_IP} -Decorz.jmeter.engine.cp=${JMETER_CP} -Decorz.prometheus.user=${PROM_USER} -Decorz.prometheus.password=${PROM_PW} \
-Decorz.prometheus.rest.end=${PROM_REST_END} -Decorz.results.resultsdumpfolder=${cp_dump_folder} -jar ${JAR_DIR}/@project.build.finalName@.jar # > spring.log 2>&1
