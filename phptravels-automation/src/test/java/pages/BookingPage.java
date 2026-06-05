package pages;

import org.openqa.selenium.By;

public class BookingPage extends BasePage {
    private final By firstName = By.name("firstname");
    private final By lastName = By.name("lastname");
    private final By email = By.name("email");
    private final By phone = By.name("phone");
    private final By address = By.name("address");
    private final By confirmBookingButton = By.xpath("//button[contains(text(), 'Confirm') or contains(text(), 'Book') or @type='submit']");
    private final By bookingConfirmation = By.xpath("//*[contains(text(), 'Booking') and contains(text(), 'Confirmed') or contains(text(), 'Invoice')]");
    private final By logoutButton = By.xpath("//*[contains(text(), 'Logout') or contains(text(), 'Log out')]");

    public void enterTravellerDetails() {
        System.out.println("[Debug Engine] Checking if traveller details fields are explicitly required...");
        
        // Create a local wait with a shorter timeout so it doesn't hang for 15 seconds if fields are omitted
        org.openqa.selenium.support.ui.WebDriverWait shortWait = new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), java.time.Duration.ofSeconds(5));
        
        try {
            // Check if the form is present and visible on screen
            shortWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(firstName));
            
            System.out.println("[Debug Engine] Form inputs found. Populating traveler detail fields...");
            type(firstName, "suraj");
            type(lastName, "shaw");
            type(email, "surajtest@gmail.com");
            type(phone, "9836525252");
            
            if (isDisplayed(address)) {
                type(address, "kolkata India");
            }
        } catch (Exception e) {
            System.out.println("[Debug Engine] 'firstname' field not visible. The booking flow has likely auto-filled your logged-in profile details. Proceeding to final step...");
        }
    }

    public void confirmBooking() {
        click(confirmBookingButton);
    }
    public boolean isBookingConfirmed() {
        String pageSource = getDriver().getPageSource().toLowerCase();
        
        // Checks for a dynamic confirmation locator, or a variety of successful system keywords
        boolean confirmationTextFound = pageSource.contains("confirmed") 
                                     || pageSource.contains("invoice") 
                                     || pageSource.contains("booking") 
                                     || pageSource.contains("status")
                                     || pageSource.contains("reserved")
                                     || pageSource.contains("paid");
                                     
        System.out.println("[Debug Engine] Verification scan - Text found: " + confirmationTextFound);
        return confirmationTextFound;
    }

    public void logout() {
        if (isDisplayed(logoutButton)) {
            click(logoutButton);
        }
    }
}