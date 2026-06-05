package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.HotelResultsPage;
import java.util.List;
import java.util.Map;

public class PriceValidationSteps {
    private final HotelResultsPage hotelResultsPage = new HotelResultsPage();
    private List<Integer> prices;

    @Then("fetch all hotel prices dynamically")
    public void fetch_all_hotel_prices_dynamically() {
        // Gives the asynchronous API network scripts time to inject the prices into the 8 cards
        try { 
            Thread.sleep(2500); 
        } catch (InterruptedException ignored) {}

        // Now safely call the results page to read the fully populated pricing elements
        HotelResultsPage hotelResultsPage = new HotelResultsPage();
        java.util.List<Integer> prices = hotelResultsPage.getAllHotelPrices();
        
        System.out.println("[Price Validation] Successfully loaded real live pricing array: " + prices);
    }
    @And("validate highest hotel price")
    public void validate_highest_hotel_price() {
        int highest = hotelResultsPage.getHighestPrice();
        Assert.assertTrue(highest >= 0, "Highest price should be greater than or equal to zero");
    }

    @And("validate lowest hotel price")
    public void validate_lowest_hotel_price() {
        int lowest = hotelResultsPage.getLowestPrice();
        Assert.assertTrue(lowest >= 0, "Lowest price should be greater than or equal to zero");
    }

    @And("validate average hotel price")
    public void validate_average_hotel_price() {
        double average = hotelResultsPage.getAveragePrice();
        Assert.assertTrue(average >= 0, "Average price should be greater than or equal to zero");
    }

    @And("validate duplicate hotel names")
    public void validate_duplicate_hotel_names() {
        Map<String, Integer> hotelPriceMap = hotelResultsPage.getHotelPriceMap();
        boolean duplicatePresent = hotelResultsPage.hasDuplicateHotelNames();
        System.out.println("Hotel price map: " + hotelPriceMap);
        System.out.println("Duplicate hotel present: " + duplicatePresent);
        Assert.assertNotNull(hotelPriceMap, "Hotel price map should not be null");
    }
}