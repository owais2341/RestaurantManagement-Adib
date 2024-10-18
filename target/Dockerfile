# Use a base image with Java runtime
FROM openjdk:18-jdk-slim

# Set the working directory
WORKDIR /Main

# Copy the JAR file into the image
COPY RestaurantReservationSystem-1.0-SNAPSHOT.jar /Main/RestaurantReservationSystem-1.0-SNAPSHOT.jar

# Run the application
CMD ["java", "-jar", "/Main/RestaurantReservationSystem-1.0-SNAPSHOT.jar"]
