package hooks;

import base.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import reports.ExtentManager;
import reports.ExtentTestManager;
import utilities.ConfigReader;
import utilities.ScreenshotUtil;

public class Hooks {
    @Before
    public void setup(Scenario scenario) {
        ConfigReader.loadConfig();
        DriverFactory.initDriver(ConfigReader.get("browser"));
        ExtentTestManager.setTest(
            ExtentManager.getExtentReports().createTest(scenario.getName())
        );
    }

    @After
    public void tearDown(Scenario scenario) {
        String status = scenario.isFailed() ? "FAIL" : "PASS";
        String screenshotPath = ScreenshotUtil.captureScreenshot(scenario.getName(), status);
        
        if (scenario.isFailed()) {
            ExtentTestManager.getTest().fail("Scenario failed. Screenshot: " + screenshotPath);
        } else {
            ExtentTestManager.getTest().pass("Scenario passed. Screenshot: " + screenshotPath);
        }
        
        DriverFactory.quitDriver();
        ExtentManager.flushReports();
        ExtentTestManager.unload();
    }
}