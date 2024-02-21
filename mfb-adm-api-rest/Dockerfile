FROM openjdk:11-jre-slim
VOLUME /tmp
EXPOSE 8001
ADD ./target/*.jar servicio-adm.jar
ENTRYPOINT ["java","-jar","/servicio-adm.jar"]
#docker build -t freddix26/supremapp-adm:v2 .
#docker push freddix26/supremapp-adm:v2
