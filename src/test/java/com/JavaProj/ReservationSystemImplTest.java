package com.JavaProj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationSystemImplTest {
    private ReservationDAO reservationDAO;

    @BeforeEach
    public void setUp() {
        // Instantiate the ReservationDAO, which connects to the actual MongoDB instance
        reservationDAO = new ReservationDAO();
    }

    @AfterEach
    public void tearDown() {
        // Optionally clear the database after each test to prevent duplicate key errors
        reservationDAO.deleteAllReservations(); // Ensure this method clears all reservations
    }

    @Test
    public void testSaveReservation() {
        String uniqueId = UUID.randomUUID().toString(); // Generate unique ID for each test
        Reservation reservation = new Reservation(uniqueId, "restaurant1", "John Doe", "john@example.com", "1234567890", 4, LocalDateTime.now());
        reservationDAO.saveReservation(reservation);
        
        Reservation fetchedReservation = reservationDAO.getReservationById(uniqueId);
        assertNotNull(fetchedReservation);
        assertEquals("John Doe", fetchedReservation.getName());
    }

    @Test
    public void testGetReservationById() {
        String uniqueId = UUID.randomUUID().toString(); // Generate unique ID for each test
        Reservation reservation = new Reservation(uniqueId, "restaurant2", "Jane Doe", "jane@example.com", "0987654321", 2, LocalDateTime.now());
        reservationDAO.saveReservation(reservation);

        Reservation fetchedReservation = reservationDAO.getReservationById(uniqueId);
        assertNotNull(fetchedReservation);
        assertEquals("Jane Doe", fetchedReservation.getName());
    }

    @Test
    public void testDeleteReservation() {
        String uniqueId = UUID.randomUUID().toString(); // Generate unique ID for each test
        Reservation reservation = new Reservation(uniqueId, "restaurant3", "Jim Beam", "jim@example.com", "1112223333", 3, LocalDateTime.now());
        reservationDAO.saveReservation(reservation);
        
        reservationDAO.deleteReservation(uniqueId);
        Reservation fetchedReservation = reservationDAO.getReservationById(uniqueId);
        assertNull(fetchedReservation); // Should return null after deletion
    }

    // Add more tests as necessary...
}
