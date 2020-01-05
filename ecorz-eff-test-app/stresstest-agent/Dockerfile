FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/stresstest_agent
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/local/stresstest_agent/
ADD target/dockerfile/run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
