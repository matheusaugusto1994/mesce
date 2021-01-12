FROM maven:3.6.2-jdk-11

COPY target/mesce.jar mesce.jar

ENTRYPOINT [ "java", "-Xms128m", "-Xmx640m", "-XX:MetaspaceSize=128M", "-XX:MaxMetaspaceSize=256m", "-jar", "/mesce.jar"]
EXPOSE 8080