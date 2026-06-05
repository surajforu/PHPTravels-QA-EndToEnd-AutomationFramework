package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import java.time.Duration;

public class DriverFactory {
    private DriverFactory() {}

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void initDriver(String browserName) {
        if (DRIVER.get() != null) {
            return;
        }
        WebDriver webDriver = null;
        String browser = (browserName == null) ? "chrome" : browserName.toLowerCase().trim();
        
        switch (browser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                // Disable browser notification popups
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-notifications");
                edgeOptions.addArguments("--remote-allow-origins=*");
                webDriver = new EdgeDriver(edgeOptions);
                break;
            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                // Core Fix: Stop location popup allowances from breaking focus
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                
                // Optional: Uncomment the next line if you want the browsers to run completely in the background
                // chromeOptions.addArguments("--headless=new"); 
                
                webDriver = new ChromeDriver(chromeOptions);
                break;
        }

        webDriver.manage().window().maximize();
        // Keep implicit wait strictly at 0 to let WebDriverWait handle sync pipelines perfectly
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        DRIVER.set(webDriver);
    }

    public static WebDriver getDriver() {
        WebDriver webDriver = DRIVER.get();
        if (webDriver == null) {
            throw new IllegalStateException("Driver is not initialized for this thread.");
        }
        return webDriver;
    }

    public static void quitDriver() {
        WebDriver webDriver = DRIVER.get();
        if (webDriver != null) {
            webDriver.quit();
            DRIVER.remove();
        }
    }
}