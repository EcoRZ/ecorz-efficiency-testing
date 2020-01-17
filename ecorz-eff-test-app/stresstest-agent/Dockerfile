FROM openjdk:8-jdk-alpine
RUN apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/stresstest_agent
RUN mkdir -p /usr/local/jmeter
ARG JAR_FILE
ARG JAR_JMETER_FILE
ARG DOCKER_JAR_DIR
ARG DOCKER_JAR_JMETER_DIR
ARG JMETER_DIR
ADD target/${JAR_FILE} ${DOCKER_JAR_DIR}/
ADD ${JMETER_DIR}/target/${JAR_JMETER_FILE} ${DOCKER_JAR_JMETER_DIR}/
ADD target/dockerfile/run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
