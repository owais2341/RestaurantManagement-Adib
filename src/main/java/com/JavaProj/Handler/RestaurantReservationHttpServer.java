package com.JavaProj.Handler;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.JavaProj.ReservationSystem;
import com.JavaProj.RestaurantManager;
import com.sun.net.httpserver.HttpServer;

public class RestaurantReservationHttpServer {
    private final RestaurantManager restaurantManager; // Reference to the RestaurantManager
    private final ReservationSystem reservationSystem;

    public RestaurantReservationHttpServer(RestaurantManager restaurantManager, ReservationSystem reservationSystem) {
        this.restaurantManager = restaurantManager; // Initialize with the provided RestaurantManager
        this.reservationSystem = reservationSystem;
    }

    public void start() throws IOException {
        // Set up the HTTP server listening on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
      
       
        server.createContext("/restaurant/add", new AddRestaurantHandler(restaurantManager));
        //server.createContext("/restaurant/get", new GetRestaurantHandler(restaurantManager));
        //server.createContext("/reservation/add", new AddReservationHandler(reservationSystem));

        server.setExecutor(null); // Default executor
        System.out.println("HTTP Server is listening on port 8080...");
        server.start();
    }
}
