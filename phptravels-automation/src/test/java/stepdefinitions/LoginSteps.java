package stepdefinitions;

import base.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utilities.ConfigReader;
import utilities.ExcelUtil;
import java.time.Duration;
import java.util.Map;

public class LoginSteps {
    private LoginPage loginPage;

    @Given("user launches browser")
    public void user_launches_browser() {
        // 1. Navigate to the base URL
        DriverFactory.getDriver().get(ConfigReader.get("baseUrl"));
        
        // 2. Handle the "Important Notice: Demo Environment" popup modal dynamically
        try {
            WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(5));
            
            // Locator for the "I Understand & Continue" button inside the modal
            By demoModalButton = By.xpath("//button[contains(text(), 'I Understand & Continue')]");
            
            // Wait for the button to be clickable, then click it to clear the overlay
            WebElement clearNoticeBtn = wait.until(ExpectedConditions.elementToBeClickable(demoModalButton));
            clearNoticeBtn.click();
            
            // Optional: Give it a tiny fraction of a second to complete fade out transition animation
            Thread.sleep(500); 
        } catch (Exception e) {
            System.out.println("Demo notice modal did not appear or was already dismissed. Proceeding with test.");
        }
    }

    @When("user enters login username {string} and password {string}")
    public void user_enters_login_username_and_password(String username, String password) {
        // Safely navigate to the login page now that the modal overlay is clear
        DriverFactory.getDriver().get(ConfigReader.get("loginUrl"));
        loginPage = new LoginPage();
        loginPage.login(username, password);
    }

    @And("user clicks on login button")
    public void user_clicks_on_login_button() {
        // Login button click is already performed inside LoginPage.login() execution sequence.
    }

    @Then("validate login result for {string}")
    public void validate_login_result_for(String expectedResult) {
        SoftAssert softAssert = new SoftAssert();
        if (expectedResult.equalsIgnoreCase("valid")) {
            softAssert.assertTrue(loginPage.isLoginSuccessful(), "Valid login should be successful");
        } else {
            softAssert.assertTrue(loginPage.isErrorDisplayed() || loginPage.isEmailFieldVisible(),
                "Invalid or blank login should show error or remain on login page");
        }
        softAssert.assertTrue(DriverFactory.getDriver().getTitle().length() > 0, "Page title should not be blank");
        softAssert.assertAll();
    }

    @When("user logs in with valid credentials from excel row {int}")
    public void user_logs_in_with_valid_credentials_from_excel_row(Integer rowNumber) {
        DriverFactory.getDriver().get(ConfigReader.get("loginUrl"));
        Map<String, String> data = ExcelUtil.getLoginData(rowNumber);
        loginPage = new LoginPage();
        loginPage.login(data.get("username"), data.get("password"));
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Excel valid login failed");
    }
    @And("user navigates to homepage {string}")
    public void user_navigates_to_homepage(String url) {
        // Replace 'loginPage' with whatever name your page variable has at the top of the file
    	DriverFactory.getDriver().get(url);
    }
}