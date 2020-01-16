FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/stresstest_agent
RUN mkdir -p /usr/local/jmeter
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/local/stresstest_agent/
ADD target/dockerfile/run.sh com.ecorz.stressapp.common.run.sh
RUN chmod +x com.ecorz.stressapp.common.run.sh
CMD ./com.ecorz.stressapp.common.run.sh
