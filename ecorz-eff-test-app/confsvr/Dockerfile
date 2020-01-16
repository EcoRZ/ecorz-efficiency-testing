FROM openjdk:8-jdk-alpine
RUN apk update && apk upgrade && apk add netcat-openbsd && apk add curl
RUN mkdir -p /usr/local/configserver
RUN echo $JAVA_HOME
RUN cd /tmp/
RUN wget --header='Cookie: oraclelicense=accept-securebackup-cookie' "http://download.oracle.com/otn-pub/java/jce/8/jce_policy-8.zip"
RUN unzip jce_policy-8.zip
RUN rm jce_policy-8.zip
RUN yes |cp -v ./UnlimitedJCEPolicyJDK8/*.jar /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/local/configserver/
ADD target/dockerfile/com.ecorz.stressapp.common.run.sh com.ecorz.stressapp.common.run.sh
RUN chmod +x com.ecorz.stressapp.common.run.sh
CMD ./com.ecorz.stressapp.common.run.sh
