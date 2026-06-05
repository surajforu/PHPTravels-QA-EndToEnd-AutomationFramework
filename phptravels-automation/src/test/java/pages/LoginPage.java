package pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By emailInput = By.name("email");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.xpath("//button[contains(text(), 'Login') or @type='submit']");
    private final By dashboard = By.xpath("//*[contains(text(), 'Dashboard') or contains(text(), 'My Account')]");
    private final By errorMessage = By.xpath("//*[contains(@class, 'alert') or contains(text(), 'Invalid')]");

    public void login(String username, String password) {
        type(emailInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    public boolean isLoginSuccessful() {
        return isDisplayed(dashboard) || getDriver().getCurrentUrl().toLowerCase().contains("dashboard");
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public boolean isEmailFieldVisible() {
        return isDisplayed(emailInput);
    }
}