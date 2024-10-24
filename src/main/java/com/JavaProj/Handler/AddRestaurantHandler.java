package com.JavaProj.Handler;

import org.json.JSONArray;
import org.json.JSONObject;
import com.JavaProj.RestaurantManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// AddRestaurantHandler.java
class AddRestaurantHandler implements HttpHandler {
    private final RestaurantManager restaurantManager;

    public AddRestaurantHandler(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            JSONObject jsonRequest;
            System.out.println("Received request: " + requestBody);

            // Try to parse the request body to JSON
            try {
                jsonRequest = new JSONObject(requestBody);
            } catch (Exception e) {
                String response = "Invalid JSON format: " + e.getMessage();
                exchange.sendResponseHeaders(400, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return; // Exit the method if parsing fails
            }

            // Validate and extract data
            String restaurantName = jsonRequest.optString("name");
            String address = jsonRequest.optString("address");
            int capacity = jsonRequest.optInt("capacity", -1);
            LocalTime open, close;
            List<LocalDate> closures = new ArrayList<>();

            try {
                open = LocalTime.parse(jsonRequest.optString("open"));
                close = LocalTime.parse(jsonRequest.optString("close"));
                
                JSONArray closuresJsonArray = jsonRequest.optJSONArray("closures");
                if (closuresJsonArray != null) {
                    for (int i = 0; i < closuresJsonArray.length(); i++) {
                        LocalDate closureDate = LocalDate.parse(closuresJsonArray.getString(i));
                        closures.add(closureDate);
                    }
                }
            } catch (Exception e) {
                String response = "Error parsing fields: " + e.getMessage();
                exchange.sendResponseHeaders(400, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return; 

            
            if (capacity <= 0) {
                String response = "Capacity must be a positive number.";
                exchange.sendResponseHeaders(400, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                return; 
            }

          
            String response = restaurantManager.addRestaurant(restaurantName, address, capacity, open, close, closures);
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            String response = "Only POST method is supported";
            exchange.sendResponseHeaders(405, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
