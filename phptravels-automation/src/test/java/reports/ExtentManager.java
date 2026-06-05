package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import utilities.ConfigReader;

public class ExtentManager {
    private static ExtentReports extentReports;

    private ExtentManager() {}

    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            String reportPath = ConfigReader.get("extentReportPath");
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("PHPTravels Automation Report");
            sparkReporter.config().setReportName("PHPTravels Test Execution Report");
            sparkReporter.config().setTheme(Theme.STANDARD);
            
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Project", "PHPTravels Automation Framework");
            extentReports.setSystemInfo("Tester", "Manish Chaudhary");
            extentReports.setSystemInfo("Framework", "Selenium Cucumber TestNG Maven");
        }
        return extentReports;
    }

    public static synchronized void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}