# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build context to the container
COPY build/libs/Manage-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (default for Spring Boot is 8080)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]