RestaurantReservationSystem-1.0-SNAPSHOT

# Use a base image with Java runtime
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the image
COPY target/RestaurantReservationSystem-1.0-SNAPSHOT.jar /app/RestaurantReservationSystem-1.0-SNAPSHOT.jar

# Run the application
CMD ["java", "-jar", "/app/RestaurantReservationSystem-1.0-SNAPSHOT.jar"]
