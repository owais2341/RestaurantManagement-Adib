# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk-slim
# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/RestaurantReservationSystem-1.0-SNAPSHOT.jar /app/RestaurantReservationSystem.jar

# Expose the port that your application will run on
EXPOSE 8080

# Define the entry point for the container to run the JAR
ENTRYPOINT ["java", "-jar", "RestaurantReservationSystem.jar"]
