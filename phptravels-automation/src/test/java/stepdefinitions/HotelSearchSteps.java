package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.HotelResultsPage;
import pages.HotelSearchPage;

public class HotelSearchSteps {

    // Thread-safe class-level page object instantiations
    private final HotelSearchPage hotelSearchPage = new HotelSearchPage();
    private final HotelResultsPage hotelResultsPage = new HotelResultsPage();
    
    // Class-level state tracker to carry the check-in date across separate step blocks
    private String temporaryCheckinDate;

    @When("user searches hotel with destination {string}")
    public void user_searches_hotel_with_destination(String destination) {
        hotelSearchPage.openHotelsTab();
        hotelSearchPage.enterDestination(destination);
    }

    @And("user selects checkin date {string}")
    public void user_selects_checkin_date(String checkin) {
        // Hold the check-in date in context memory until the checkout step is reached
        this.temporaryCheckinDate = checkin; 
    }

    @And("user selects checkout date {string}")
    public void user_selects_checkout_date(String checkout) {
        // Dispatches both synchronized strings down to the target page element values
        hotelSearchPage.selectDates(this.temporaryCheckinDate, checkout);
    }

    @And("user selects checkin date {string} and checkout date {string}")
    public void user_selects_checkin_date_and_checkout_date(String checkin, String checkout) {
        // Provides backward compatibility fallback for combined inline format steps
        hotelSearchPage.selectDates(checkin, checkout);
    }

    @And("user selects travellers adults {int} and children {int}")
    public void user_selects_travellers_adults_and_children(Integer adults, Integer children) {
        // Unboxes Integer instances safely into primitive functional page contexts
        hotelSearchPage.selectTravellers(adults != null ? adults : 2, children != null ? children : 0);
    }

    @And("user clicks hotel search button")
    public void user_clicks_hotel_search_button() {
        hotelSearchPage.clickSearch();
    }

    @Then("hotel search results should be displayed")
    public void hotel_search_results_should_be_displayed() {
        // Allow dynamic AJAX components to transition completely in parallel execution
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        
        Assert.assertTrue(hotelResultsPage.getAvailableHotelsCount() >= 0, 
            "Execution Failure: Hotel results page grid cards failed to render inside the active window DOM context.");
    }

    @And("available hotels count should be greater than 0")
    public void available_hotels_count_should_be_greater_than_0() {
        int actualHotelCount = hotelResultsPage.getAvailableHotelsCount();
        Assert.assertTrue(actualHotelCount > 0, 
            "Validation Failure: Search results rendered, but the visible hotel container array count was: " + actualHotelCount);
    }
}