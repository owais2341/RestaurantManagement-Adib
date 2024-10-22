package com.JavaProj.Handler;

import org.json.JSONObject;

import com.JavaProj.ReservationSystem;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class AddReservationHandler implements HttpHandler {
    private final ReservationSystem reservationManager; // Reference to the ReservationManager

    public AddReservationHandler(ReservationSystem reservationManager) {
        this.reservationManager = reservationManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            JSONObject jsonRequest = new JSONObject(requestBody);
            
         
            String reservationId = jsonRequest.optString("reservationId");
            String restaurantId = jsonRequest.optString("restaurantId");
            String name = jsonRequest.optString("name");
            String email = jsonRequest.optString("email");
            String phone = jsonRequest.optString("phone"); // Ensure you get the phone number
            int partySize = jsonRequest.optInt("partySize");
            String dateTimeStr = jsonRequest.optString("dateTime");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // Call addReservation with all required parame
                        // Call addReservation with all requirrame
                      // Call addReservation with all required param
                      // Call addReservation with all required para
            String response = reservationManager.addReservation(restaurantId, name, email, phone, partySize, dateTime);
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
