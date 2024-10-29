package com.JavaProj;


import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import java.time.LocalTime;
import java.util.ArrayList;

import org.bson.Document;

public class RestaurantDAO {
    private MongoCollection<Document> collection;

    public RestaurantDAO() {
        MongoClient mongoClient = MongoClients.create("mongodb://root:my-password@my-mongodb:27017/admin");
        MongoDatabase database = mongoClient.getDatabase("restaurant-db");
        collection = database.getCollection("restaurants");
    }

    public void saveRestaurant(Restaurant restaurant) {
        // First, check if the restaurant already exists in the database
        Document existingRestaurant = collection.find(new Document("_id", restaurant.getId())).first();
        if (existingRestaurant != null) {
            System.out.println("Restaurant with ID " + restaurant.getId() + " already exists. Skipping insertion.");
            return;
        }
        Document doc = new Document("_id", restaurant.getId())
                .append("name", restaurant.getName())
                .append("address", restaurant.getAddress())
                .append("capacity", restaurant.getCapacity())
                .append("open_time", restaurant.getOpen().toString())
                .append("close_time", restaurant.getClose().toString());
        
        try {
            collection.insertOne(doc); 
            System.out.println("Restaurant added successfully with ID: " + restaurant.getId());
        } catch (MongoWriteException e) {
            if (e.getError().getCategory().equals(com.mongodb.ErrorCategory.DUPLICATE_KEY)) {
                System.out.println("Duplicate Key Error: Restaurant with ID " + restaurant.getId() + " already exists.");
            } else {
                throw e; 
            }
        }
    }

    public Restaurant getRestaurantById(String id) {
        Document doc = collection.find(new Document("_id", id)).first();
        if (doc != null) {
            return new Restaurant(
                    doc.getString("_id"),
                    doc.getString("name"),
                    doc.getString("address"),
                    doc.getInteger("capacity"),
                    LocalTime.parse(doc.getString("open_time")),
                    LocalTime.parse(doc.getString("close_time")),
                    new ArrayList<>()
            );
        }
        return null;
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurantList = new ArrayList<>();
        for (Document doc : collection.find()) {
            Restaurant restaurant = new Restaurant(
                    doc.getString("_id"),
                    doc.getString("name"),
                    doc.getString("address"),
                    doc.getInteger("capacity"),
                    LocalTime.parse(doc.getString("open_time")),
                    LocalTime.parse(doc.getString("close_time")),
                    new ArrayList<>()
            );
            restaurantList.add(restaurant);
        }
        return restaurantList;
    }
    public void deleteRestaurant(String id) {
        collection.deleteOne(new Document("_id", id));
    }
    public void deleteAllRestaurants() {
        collection.deleteMany(new Document());
    }
    
}