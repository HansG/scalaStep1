# Runtime-only stage: minimal JRE image with pre-built FAT JAR
FROM eclipse-temurin:25-jre

# Set working directory
WORKDIR /app

# Copy the pre-built FAT JAR from local target directory
# (Built with: sbt assembly in DevContainer)
COPY target/scala-3.8.3/hello-scala-assembly-0.1.0-SNAPSHOT.jar /app/app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
