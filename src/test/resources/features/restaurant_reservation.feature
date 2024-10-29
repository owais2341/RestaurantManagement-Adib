Feature: Restaurant Reservation

  Scenario: Make a reservation
    Given the restaurant is open
    When I make a reservation for 2 people at 7 PM
    Then the reservation should be confirmed

  Scenario: Make a reservation when the restaurant is closed
    Given the restaurant is closed
    When I make a reservation for 2 people at 7 PM
    Then the reservation should not be confirmed