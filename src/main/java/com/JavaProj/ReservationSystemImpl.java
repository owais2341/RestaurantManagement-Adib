package com.JavaProj;

import java.time.LocalDateTime;
import java.util.*;


public class ReservationSystemImpl implements ReservationSystem {
    private RestaurantManager restaurantManager;
    private ReservationDAO reservationDAO;
    private Map<String, TreeMap<LocalDateTime, Reservation>> reservations = new HashMap<>();
    private Map<String, Reservation> reservationById = new HashMap<>();

    public ReservationSystemImpl(RestaurantManager restaurantManager, ReservationDAO reservationDAO) {
        this.restaurantManager = restaurantManager;
        this.reservationDAO = reservationDAO;
    }


    @Override
    public String addReservation(String restaurantId, String name, String email, String phone, int partySize, LocalDateTime dateTime) {
      try{  
        validateInputs(restaurantId, name, email, phone, partySize, dateTime);

        Restaurant restaurant = restaurantManager.getRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new RuntimeException(String.format("Invalid restaurantId: %s", restaurantId));
        }

        if (!restaurant.isReservationDateTimeValid(dateTime, 2)) {
            throw new RuntimeException(String.format("Invalid reservation time or date for restaurantId: %s", restaurantId));
        }

        TreeMap<LocalDateTime, Reservation> existingReservations = reservations.getOrDefault(restaurantId, new TreeMap<>());
        if (isCapacityExceeded(restaurant, existingReservations, dateTime, partySize)) {
            throw new RuntimeException(String.format("Reservation exceeds restaurant capacity: %d", restaurant.getCapacity()));
        }

        String reservationId = UUID.randomUUID().toString();
        Reservation newReservation = new Reservation(reservationId, restaurantId, name, email, phone, partySize, dateTime);
        System.out.println("Adding restaurant: " + newReservation);

        existingReservations.put(dateTime, newReservation);
        reservations.put(restaurantId, existingReservations);
        reservationById.put(reservationId, newReservation);

        // Save the new reservation to the database
        reservationDAO.saveReservation(newReservation);

        return reservationId;
    }catch (Exception e) {
        e.printStackTrace(); // Print the stack trace for debugging
        throw new RuntimeException("Error adding reservation: " + e.getMessage());
    }
    }

    @Override
    public String modifyReservation(String reservationId, String restaurantId, String name, String email, String phone, int partySize, LocalDateTime dateTime) {
        cancelReservation(reservationId);
        return addReservation(restaurantId, name, email, phone, partySize, dateTime);
    }

    @Override
    public void cancelReservation(String reservationId) {
        Reservation reservation = reservationById.get(reservationId);
        if (reservation != null) {
            TreeMap<LocalDateTime, Reservation> restaurantReservations = reservations.get(reservation.getRestaurantId());
            if (restaurantReservations != null) {
                restaurantReservations.remove(reservation.getDateTime());
                if (restaurantReservations.isEmpty()) {
                    reservations.remove(reservation.getRestaurantId());
                }
            }
            reservationById.remove(reservationId);

            // Delete the reservation from the database
            reservationDAO.deleteReservation(reservationId);
        }
    }

    private void validateInputs(String restaurantId, String name, String email, String phone, int partySize, LocalDateTime dateTime) {
        if (restaurantId == null || name == null || email == null || phone == null || dateTime == null) {
            throw new RuntimeException("All input parameters must be non-null");
        }
        if (!phone.matches("^\\+92[0-9]{10}$")) {
            throw new RuntimeException(String.format("Invalid phone number: %s", phone));
        }
        if (partySize <= 0) {
            throw new RuntimeException(String.format("Invalid party size: %d", partySize));
        }
    }

    private boolean isCapacityExceeded(Restaurant restaurant, TreeMap<LocalDateTime, Reservation> existingReservations, LocalDateTime dateTime, int partySize) {
        int totalPartySize = partySize;
        LocalDateTime reservationEndTime = dateTime.plusHours(2);

        // Find reservations that overlap with the new reservation
        SortedMap<LocalDateTime, Reservation> overlappingReservations = existingReservations.subMap(dateTime.minusHours(2), reservationEndTime);
        for (Reservation res : overlappingReservations.values()) {
            totalPartySize += res.getPartySize();
        }

        return totalPartySize > restaurant.getCapacity();
    }
}