# Stage 1: Build the application
FROM gradle:7.6.1-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon -x test

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Set environment variables for PostgreSQL
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d0f8l4emcj7s73d63ggg-a:5432/phonecase_db
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=m1wy5RXNIkS8U1YUTjWGDioFuooqrbnn
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

# Run the application
CMD ["java", "-jar", "app.jar"]