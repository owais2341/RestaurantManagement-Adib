# Step 1: Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file into the container
COPY target/RestaurantReservationSystem-1.0-SNAPSHOT.jar /app/RestaurantReservationSystem.jar

# Step 4: Expose the port that your application will run on
EXPOSE 8080

# Step 5: Define the entry point for the container to run the JAR
ENTRYPOINT ["java", "-jar", "RestaurantReservationSystem.jar"]
