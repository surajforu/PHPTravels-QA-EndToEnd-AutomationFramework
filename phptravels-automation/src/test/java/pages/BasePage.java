package pages;

import base.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.WaitUtil;

public class BasePage {
    protected WaitUtil waitUtil;

    public BasePage() {
        // Safe, independent initialization of the wait utility
        this.waitUtil = new WaitUtil(); 
    }

    /**
     * Dynamic getter that replaces the old 'protected WebDriver driver' field.
     * Call getDriver() instead of driver across your Page Objects to clear the red lines.
     */
    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    protected void click(By locator) {
        retryForStaleElement(() -> waitUtil.waitForClickable(locator).click());
    }

    protected void type(By locator, String text) {
        retryForStaleElement(() -> {
            WebElement element = waitUtil.waitForVisible(locator);
            element.clear();
            element.sendKeys(text == null ? "" : text);
        });
    }

    protected String getText(By locator) {
        return waitUtil.waitForVisible(locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitUtil.waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void jsClick(By locator) {
        WebElement element = waitUtil.waitForPresence(locator);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    private void retryForStaleElement(Runnable action) {
        int attempts = 0;
        while (attempts <= 2) {
            try {
                action.run();
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
    }
}