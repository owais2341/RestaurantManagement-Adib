package com.JavaProj;

import java.io.IOException;

import com.JavaProj.Handler.RestaurantReservationHttpServer;


public class Main {
     public static void main(String[] args) throws IOException {

     RestaurantDAO restaurantDAO = new RestaurantDAO();
     ReservationDAO reservationDAO = new ReservationDAO();

     RestaurantManager restaurantManager = new RestaurantManagerImpl(restaurantDAO);
     ReservationSystem reservationSystem = new ReservationSystemImpl(restaurantManager, reservationDAO);

     RestaurantReservationHttpServer server = new RestaurantReservationHttpServer(restaurantManager,reservationSystem);
     server.start();
}
}
