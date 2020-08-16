FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY target/thesis2020version2-*.jar thesis2020version2.jar
EXPOSE 18080
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar thesis2020version2.jar