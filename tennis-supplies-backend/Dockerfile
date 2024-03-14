# Use JDK 21 as base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container at /app
COPY target/tennis-supplies-backend-0.0.1-SNAPSHOT.jar /app/tennis-supplies-backend.jar

# Expose port 8082 to the outside world
EXPOSE 8082

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "tennis-supplies-backend.jar"]
