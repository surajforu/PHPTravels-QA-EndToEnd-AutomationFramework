package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.BookingPage;
import pages.HotelResultsPage;

public class BookingSteps {
    
    // Declared final to ensure object memory addresses remain isolated per execution thread
    private final HotelResultsPage hotelResultsPage;
    private final BookingPage bookingPage;

    // Framework Constructor: Thread-safe instantiation point used by Cucumber
    public BookingSteps() {
        this.hotelResultsPage = new HotelResultsPage();
        this.bookingPage = new BookingPage();
    }

    @And("user selects first available hotel")
    public void user_selects_first_available_hotel() {
        hotelResultsPage.selectFirstHotel();
    }

    @And("user enters traveller details")
    public void user_enters_traveller_details() {
        bookingPage.enterTravellerDetails();
    }

    @And("user confirms hotel booking")
    public void user_confirms_hotel_booking() {
        bookingPage.confirmBooking();
    }

    @Then("booking confirmation message should be displayed")
    public void booking_confirmation_message_should_be_displayed() {
        Assert.assertTrue(bookingPage.isBookingConfirmed(), "Booking confirmation message was not displayed");
    }

    @And("user logs out")
    public void user_logs_out() {
        bookingPage.logout();
    }
}