package com.JavaProj.Handler;

import com.JavaProj.Restaurant;
import com.JavaProj.RestaurantManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray; // Import JSONArray for JSON conversion

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

class GetRestaurantHandler implements HttpHandler {
    private final RestaurantManager restaurantManager;

    public GetRestaurantHandler(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            List<Restaurant> restaurants = restaurantManager.getAllRestaurants();
            JSONArray jsonResponse = new JSONArray(restaurants); // Convert list to JSON array
            String response = jsonResponse.toString(); // Convert JSON array to string
            
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            String response = "Only GET method is supported";
            exchange.sendResponseHeaders(405, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
