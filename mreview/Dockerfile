# Start with a base image containing Java runtime
FROM openjdk:11-jdk

# Add Author info
LABEL maintainer="cottage123@gmail.com"

# Add a volume to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/mreview-0.0.1-SNAPSHOT.war

# Add the application's jar to the container
ADD ${JAR_FILE} to-do-springboot.war

# Run the jar file
ENTRYPOINT ["java","-jar","/to-do-springboot.war"]
