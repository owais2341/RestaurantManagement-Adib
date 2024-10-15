package com.JavaProj.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.JavaProj.ReservationSystem;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    private ReservationSystem reservationSystem; // Use the service layer

    // Endpoint to add a new reservation
    @PostMapping("/reservations")
    public String addReservation(
        @RequestParam String restaurantId,
        @RequestParam String name,
        @RequestParam String email,
        @RequestParam String phone,
        @RequestParam int partySize,
        @RequestParam String dateTime // Pass as string and convert to LocalDateTime
    ) {
        LocalDateTime reservationDateTime = LocalDateTime.parse(dateTime); // Convert String to LocalDateTime
        String reservationId = reservationSystem.addReservation(
            restaurantId, name, email, phone, partySize, reservationDateTime
        );
        return "Reservation created successfully with ID: " + reservationId;
    }

    // Endpoint to modify a reservation
    @PutMapping("/reservations/{id}")
    public String modifyReservation(
        @PathVariable String id,
        @RequestParam String restaurantId,
        @RequestParam String name,
        @RequestParam String email,
        @RequestParam String phone,
        @RequestParam int partySize,
        @RequestParam String dateTime
    ) {
        LocalDateTime reservationDateTime = LocalDateTime.parse(dateTime); // Convert String to LocalDateTime
        String updatedReservationId = reservationSystem.modifyReservation(
            id, restaurantId, name, email, phone, partySize, reservationDateTime
        );
        return "Reservation with ID: " + updatedReservationId + " updated successfully!";
    }

    // Endpoint to get a reservation by ID
    // @GetMapping("/reservations/{id}")
    // public Reservation getReservationById(@PathVariable String id) {
    //     return reservationSystem.getReservationById(id);
    // }

    // Endpoint to cancel a reservation
    @DeleteMapping("/reservations/{id}")
    public String cancelReservation(@PathVariable String id) {
        reservationSystem.cancelReservation(id);
        return "Reservation with ID: " + id + " canceled successfully!";
    }

    // Endpoint to get reservations by restaurant ID
    // @GetMapping("/restaurants/{restaurantId}/reservations")
    // public List<Reservation> getReservationsByRestaurantId(@PathVariable String restaurantId) {
    //     return reservationSystem.getReservationsByRestaurantId(restaurantId);
    // }
}
