FROM tomcat:latest
EXPOSE 8080
ARG WAR_FILE=target/Track-Loader-Service-1.0-SNAPSHOT.war
COPY ${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war
