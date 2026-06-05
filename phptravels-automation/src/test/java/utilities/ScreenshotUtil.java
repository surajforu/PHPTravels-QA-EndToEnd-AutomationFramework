package utilities;

import base.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
    private ScreenshotUtil() {}

    public static String captureScreenshot(String scenarioName, String status) {
        WebDriver driver = DriverFactory.getDriver();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        String cleanName = scenarioName.replaceAll("[^a-zA-Z0-9]", "");
        String folder = ConfigReader.get("screenshotPath");
        String path = folder + status + "/" + cleanName + "_" + timestamp + ".png";
        
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(path);
            FileUtils.copyFile(src, dest);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Screenshot capture failed", e);
        }
    }
}