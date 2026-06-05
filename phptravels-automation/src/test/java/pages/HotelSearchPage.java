package pages;

import base.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HotelSearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- Dynamic & Clean Relative Locators ---
    private final By staysTab = By.xpath("//span[normalize-space()='Stays']");
    private final By destinationInput = By.xpath("(//input[@placeholder='Search By City'])[2]   ");
    private final By autocompleteSelectResult = By.xpath("//body[1]/div[2]/div[4]/div[1]/div[1]/div[4]/div[1]/div[1]/form[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]");
    private final By guestroomDropdown = By.xpath("//span[normalize-space()='2 Guests, 1 Room']");
    private final By nationalityDropdown = By.xpath("//span[normalize-space()='Select Nationality']");
    private final By searchCountryField = By.xpath("//input[@placeholder='Search country...']");
    private final By searchHotelsButton = By.xpath("//button[@title='Search Hotels']");
    private final By countryOption = By.xpath("//div[contains(@class, 'input-dropdown-item')]//span[normalize-space()='India']");

    public HotelSearchPage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(15));
    }

    public void openHotelsTab() {
        WebElement staysMenu = wait.until(ExpectedConditions.elementToBeClickable(staysTab));
        try {
            staysMenu.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", staysMenu);
        }
    }

    public void enterDestination(String destination) {
        openHotelsTab(); 
        
        WebElement cityField = wait.until(ExpectedConditions.elementToBeClickable(destinationInput));
        cityField.click();
        cityField.clear();
        cityField.sendKeys(destination);

        WebElement dropdownSelection = wait.until(ExpectedConditions.visibilityOfElementLocated(autocompleteSelectResult));
        try {
            dropdownSelection.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownSelection);
        }
    }

    public void selectDates(String checkin, String checkout) {
        try {
            WebElement checkinField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='checkin' or contains(@name,'checkin')]")));
            WebElement checkoutField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='checkout' or contains(@name,'checkout')]")));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value='" + checkin + "';", checkinField);
            js.executeScript("arguments[0].value='" + checkout + "';", checkoutField);
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.getElementById('checkin').value='" + checkin + "';");
            js.executeScript("document.getElementById('checkout').value='" + checkout + "';");
        }
    }

    public void selectTravellers(int adults, int children) {
        WebElement guests = wait.until(ExpectedConditions.elementToBeClickable(guestroomDropdown));
        guests.click();
    }

    public void selectNationality(String countryName) {
        WebElement nationality = wait.until(ExpectedConditions.elementToBeClickable(nationalityDropdown));
        nationality.click();

        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchCountryField));
        searchField.clear();
        searchField.sendKeys(countryName);

        try {
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(countryOption));
            option.click();
        } catch (Exception e) {
            WebElement option = driver.findElement(countryOption);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
        }
    }

    public void clickSearch() {
        selectNationality("India");

        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchHotelsButton));
        try {
            searchBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
        }
    }
}