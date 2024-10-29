
package com.JavaProj;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface RestaurantManager {
    
    String addRestaurant(String name, String address, int capacity, LocalTime open, LocalTime close, List<LocalDate> closures);
    List<Restaurant> getAllRestaurants(); 
    String modifyRestaurant(String restaurantId, String name, String address, int capacity, LocalTime open, LocalTime close, List<LocalDate> closures);  
    String removeRestaurant(String restaurantId);
    Restaurant getRestaurantById(String restaurantId) ;
}


