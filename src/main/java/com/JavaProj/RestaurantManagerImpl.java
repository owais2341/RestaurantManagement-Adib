package com.JavaProj;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import org.json.JSONObject;

import com.mongodb.MongoWriteException;


public class RestaurantManagerImpl implements RestaurantManager {
    private Map<String, Restaurant> restaurantMap = new HashMap<>();
    private RestaurantDAO restaurantDAO;
    //private final ReservationDAO reservationDAO;

    public RestaurantManagerImpl( RestaurantDAO restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    @Override
    public String addRestaurant(String name, String address, int capacity, LocalTime open, LocalTime close, List<LocalDate> closures) {
        try {
            validateParameters(name, address, capacity, open, close, closures);
            String restaurantId = generateRestaurantId();
            
            // Check if restaurant already exists before adding
            if (restaurantMap.containsKey(restaurantId)) {
                return "Restaurant with ID " + restaurantId + " already exists.";
            }
            
            Restaurant restaurant = new Restaurant(restaurantId, name, address, capacity, open, close, closures);
            restaurantDAO.saveRestaurant(restaurant); // Insert to database
            restaurantMap.put(restaurantId, restaurant); // Add to local cache
            
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "Restaurant added successfully");
            jsonResponse.put("restaurantId", restaurantId);
        
            return restaurantId;
        } catch (Exception e) {
            e.printStackTrace(); // For debugging
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> allRestaurants = new ArrayList<>();
        
        // Fetch from the restaurantMap first
        allRestaurants.addAll(restaurantMap.values());
        
        // Fetch from the database for any not in the cache
        List<Restaurant> restaurantsFromDB = restaurantDAO.getAllRestaurants();
        for (Restaurant restaurant : restaurantsFromDB) {
            if (!restaurantMap.containsKey(restaurant.getId())) {
                restaurantMap.put(restaurant.getId(), restaurant); // Cache the restaurant
            }
            allRestaurants.add(restaurant);
        }
        
        return allRestaurants;
    }
    @Override
    public String modifyRestaurant(String restaurantId, String name, String address, int capacity, LocalTime open, LocalTime close, List<LocalDate> closures) {
        if (!restaurantMap.containsKey(restaurantId)) {
            // Attempt to retrieve from DAO if not found in local cache
            Restaurant existingRestaurant = restaurantDAO.getRestaurantById(restaurantId);
            if (existingRestaurant != null) {
                restaurantMap.put(restaurantId, existingRestaurant); // Cache it
            } else {
                throw new RuntimeException("Restaurant with ID " + restaurantId + " does not exist.");
            }
        }
        // Now, we can safely modify
        return addRestaurant(name, address, capacity, open, close, closures);
    }

    @Override
    public String removeRestaurant(String restaurantId) {
        if (!restaurantMap.containsKey(restaurantId)) {
            throw new RuntimeException("Restaurant with ID " + restaurantId + " does not exist.");
        }
        restaurantMap.remove(restaurantId);

        // Remove the restaurant from the database
        restaurantDAO.deleteRestaurant(restaurantId);
        
        return restaurantId;
    }

    @Override
    public Restaurant getRestaurantById(String restaurantId) {

        Restaurant restaurant =  restaurantMap.get(restaurantId);

        if(restaurant == null){
            restaurant = restaurantDAO.getRestaurantById(restaurantId);
            if(restaurant != null){
                restaurantMap.put(restaurantId, restaurant);
            }
        }
        return restaurant;

    }

    private void validateParameters(String name, String address, int capacity, LocalTime open, LocalTime close, List<LocalDate> closures) {
        if (name == null || address == null || open == null || close == null || closures == null) {
            throw new RuntimeException("All input parameters must be non-null.");
        }
        if (capacity <= 0) {
            throw new RuntimeException("Capacity must be a positive number.");
        }
        if (!closures.stream().allMatch(date -> date.isAfter(LocalDate.now()))) {
            throw new RuntimeException("All closure dates must be in the future.");
        }
        if (open.isAfter(close) || open.plusHours(8).isAfter(close)) {
            throw new RuntimeException("Restaurant must be open for at least 8 hours and close time must be strictly after open time.");
        }
    }

    private String generateRestaurantId() {
        return "R" + (restaurantMap.size() + 1);
    }
}