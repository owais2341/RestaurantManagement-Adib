package com.JavaProj;

import java.time.LocalDateTime;

class Reservation {
    private String reservationId;
    private String restaurantId;
    private String name;
    private String email;
    private String phone;
    private int partySize;
    private LocalDateTime dateTime;

    public Reservation(String reservationId, String restaurantId, String name, String email, String phone, int partySize, LocalDateTime dateTime) {
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.partySize = partySize;
        this.dateTime = dateTime;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getPartySize() {
        return partySize;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}