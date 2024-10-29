package com.JavaProj;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;

public class StepDefinitions {

    private boolean restaurantOpen;
    private boolean reservationConfirmed;

    @Given("the restaurant is open")
    public void the_restaurant_is_open() {
        restaurantOpen = true;
    }

    @Given("the restaurant is closed")
    public void the_restaurant_is_closed() {
        restaurantOpen = false;
    }

    @When("I make a reservation for {int} people at {int} PM")
    public void i_make_a_reservation_for_people_at_PM(int people, int time) {
        if (restaurantOpen && people > 0 && time > 0 && time <= 24) {
            reservationConfirmed = true;
        } else {
            reservationConfirmed = false;
        }
    }

    @Then("the reservation should be confirmed")
    public void the_reservation_should_be_confirmed() {
        assertTrue(reservationConfirmed);
    }

    @Then("the reservation should not be confirmed")
    public void the_reservation_should_not_be_confirmed() {
        assertFalse(reservationConfirmed);
    }
}