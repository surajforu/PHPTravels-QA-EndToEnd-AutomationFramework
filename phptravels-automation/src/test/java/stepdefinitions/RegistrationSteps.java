package stepdefinitions;

import base.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.RegistrationPage;
import utilities.ConfigReader;

public class RegistrationSteps {
    private final RegistrationPage registrationPage = new RegistrationPage();

    @When("user opens registration page")
    public void user_opens_registration_page() {
        // Change this to ONLY navigate to the page
        registrationPage.navigateToRegistrationPage(); 
    }

    @When("user enters mandatory registration details")
    public void user_enters_mandatory_registration_details() {
        // This is where the form typing actions belong
        registrationPage.enterMandatoryDetails();
    }

   

    @And("user submits registration form")
    public void user_submits_registration_form() {
        registrationPage.submitRegistration();
    }

    @Then("user should see successful registration message")
    public void user_should_see_successful_registration_message() {
        Assert.assertTrue(registrationPage.isRegistrationSuccessful(), "Registration success message was not displayed");
    }
}