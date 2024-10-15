package com.JavaProj;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class RestaurantManagerImplTest {
    private RestaurantDAO restaurantDAO;
    private RestaurantManagerImpl restaurantManager;

    @BeforeEach
    public void setUp() {
        // Initialize the DAO and Manager
        restaurantDAO = new RestaurantDAO(); // Ensure your MongoDB is running
        restaurantManager = new RestaurantManagerImpl(restaurantDAO);
    }

    @AfterEach
public void tearDown() {
    restaurantDAO.deleteAllRestaurants(); // Ensure you have this method implemented
    
}

    @Test
    public void testAddRestaurant() {
        String restaurantId = restaurantManager.addRestaurant("The Grill", "123 Main St", 100, LocalTime.of(11, 0), LocalTime.of(23, 0), Collections.emptyList());

        Restaurant restaurant = restaurantManager.getRestaurantById(restaurantId);
        assertNotNull(restaurant);
        assertEquals("The Grill", restaurant.getName());
    }
    @Test
    public void testAddRestaurantValidInputs() {
        String restaurantId = restaurantManager.addRestaurant("Sunset Diner", "789 Oak St", 50, LocalTime.of(9, 0), LocalTime.of(17, 0), Collections.emptyList());
    
        Restaurant restaurant = restaurantManager.getRestaurantById(restaurantId);
        assertNotNull(restaurant);
        assertEquals("Sunset Diner", restaurant.getName());
        assertEquals("789 Oak St", restaurant.getAddress());
        assertEquals(50, restaurant.getCapacity());
        assertEquals(LocalTime.of(9, 0), restaurant.getOpen());
        assertEquals(LocalTime.of(17, 0), restaurant.getClose());
    }
    @Test
    public void testValidateParametersInvalidCapacity() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            restaurantManager.addRestaurant("The Grill", "123 Main St", -1, LocalTime.of(11, 0), LocalTime.of(23, 0), Collections.emptyList());
        });
        assertEquals("Capacity must be a positive number.", exception.getMessage());
    }

    @Test
    public void testValidateParametersInvalidClosureDate() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            restaurantManager.addRestaurant("The Grill", "123 Main St", 100, LocalTime.of(11, 0), LocalTime.of(23, 0), Arrays.asList(LocalDate.now().minusDays(1)));
        });
        assertEquals("All closure dates must be in the future.", exception.getMessage());
    }

    @Test
    public void testValidateParametersInvalidOpenCloseTimes() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            restaurantManager.addRestaurant("The Grill", "123 Main St", 100, LocalTime.of(23, 0), LocalTime.of(11, 0), Collections.emptyList());
        });
        assertEquals("Restaurant must be open for at least 8 hours and close time must be strictly after open time.", exception.getMessage());
    }
    
    @Test
    public void testModifyRestaurant() {
        String restaurantId = restaurantManager.addRestaurant("The Grill", "123 Main St", 100, LocalTime.of(11, 0), LocalTime.of(23, 0), Collections.emptyList());
        
        String modifiedId = restaurantManager.modifyRestaurant(restaurantId, "The New Grill", "456 Elm St", 150, LocalTime.of(10, 0), LocalTime.of(22, 0), Collections.emptyList());
        
        Restaurant restaurant = restaurantManager.getRestaurantById(modifiedId);
        assertNotNull(restaurant);
        assertEquals("The New Grill", restaurant.getName());
        assertEquals("456 Elm St", restaurant.getAddress());
    }

    @Test
    public void testRemoveRestaurant() {
        String restaurantId = restaurantManager.addRestaurant("The Grill", "123 Main St", 100, LocalTime.of(11, 0), LocalTime.of(23, 0), Collections.emptyList());
        
        String removedId = restaurantManager.removeRestaurant(restaurantId);
        assertEquals(restaurantId, removedId);
        
        assertNull(restaurantManager.getRestaurantById(restaurantId)); // Should return null after removal
    }

    

    // Add more tests as necessary...
}

