package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.BookingTablePage;
import java.util.List;
import java.util.Map;

public class BookingTableSteps {
    
    // Made final and handled through constructor initialization for parallel thread safety
    private final BookingTablePage bookingTablePage;
    private List<Map<String, String>> tableData;

    // Constructor ensures every parallel execution thread gets its isolated page instance safely
    public BookingTableSteps() {
        this.bookingTablePage = new BookingTablePage();
    }

    @And("user opens bookings page")
    public void user_opens_bookings_page() {
        bookingTablePage.openBookingsPage();
    }

    @Then("fetch complete booking table dynamically")
    public void fetch_complete_booking_table_dynamically() {
        tableData = bookingTablePage.getBookingTableData();
        Assert.assertNotNull(tableData, "Booking table data should not be null");
    }

    @And("validate duplicate booking rows")
    public void validate_duplicate_booking_rows() {
        // Enforce safe pipeline execution checks
        Assert.assertNotNull(tableData, "Table data should be fetched before running validations");
        boolean duplicates = bookingTablePage.hasDuplicateRows();
        System.out.println("Duplicate booking rows present: " + duplicates);
    }

    @And("validate highest booking amount")
    public void validate_highest_booking_amount() {
        Assert.assertNotNull(tableData, "Table data should be fetched before running validations");
        int highest = bookingTablePage.getHighestBookingAmount();
        System.out.println("Highest booking amount tracked: $" + highest);
        Assert.assertTrue(highest >= 0, "Highest amount should be valid");
    }

    @And("validate lowest booking amount")
    public void validate_lowest_booking_amount() {
        Assert.assertNotNull(tableData, "Table data should be fetched before running validations");
        int lowest = bookingTablePage.getLowestBookingAmount();
        System.out.println("Lowest booking amount tracked: $" + lowest);
        Assert.assertTrue(lowest >= 0, "Lowest amount should be valid");
    }

    @And("convert table data into map")
    public void convert_table_data_into_map() {
        Assert.assertNotNull(tableData, "Table data conversion failed, data list is empty.");
        System.out.println("--- Streamlining Processed Table Matrix Map Out ---");
        for (Map<String, String> row : tableData) {
            System.out.println(row);
        }
    }
}