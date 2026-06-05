package utilities;

import base.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitUtil {

    /**
     * Dynamically constructs a WebDriverWait bound specifically to the current thread's browser.
     */
    private WebDriverWait getWait() {
        int seconds = ConfigReader.getInt("explicitWait");
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(seconds));
    }

    public WebElement waitForClickable(By locator) {
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForVisible(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForPresence(By locator) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public Alert waitForAlert() {
        return getWait().until(ExpectedConditions.alertIsPresent());
    }

    public WebDriver waitForFrame(By locator) {
        return getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    public boolean waitForUrlContains(String text) {
        return getWait().until(ExpectedConditions.urlContains(text));
    }

    public boolean waitForTitleContains(String text) {
        return getWait().until(ExpectedConditions.titleContains(text));
    }
}