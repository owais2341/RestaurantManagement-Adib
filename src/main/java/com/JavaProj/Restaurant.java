package com.JavaProj;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Restaurant {
    String id;
    String name;
    String address;
    int capacity;
    LocalTime open;
    LocalTime close;
    List<LocalDate> closures;
    public Restaurant() {
        // No-arg constructor
    }
    public Restaurant(String id, String name, String address, int capacity, LocalTime open, LocalTime close, List<LocalDate> closures) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.open = open;
        this.close = close;
        this.closures = closures;
    }

    public boolean isReservationDateTimeValid(LocalDateTime dateTime, int durationInHours) {
        if(dateTime == null) {
            return false;
        }
        if (dateTime.toLocalDate().isBefore(LocalDate.now())) {
            return false;
        }
        if (closures.contains(dateTime.toLocalDate())) {
            return false;
        }
        LocalTime reservationTime = dateTime.toLocalTime();
        LocalTime reservationEndTime = reservationTime.plusHours(durationInHours);
        if (reservationTime.isBefore(open) || reservationEndTime.isAfter(close)) {
            return false;
        }
        return true;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalTime getOpen() {
        return open;
    }

    public LocalTime getClose() {
        return close;
    }

    public List<LocalDate> getClosures() {
        return closures;
    }
}