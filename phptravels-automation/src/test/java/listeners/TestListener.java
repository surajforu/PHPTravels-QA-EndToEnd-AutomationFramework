package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import reports.ExtentTestManager;
import utilities.ScreenshotUtil;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.setTest(
            ExtentManager.getExtentReports().createTest(result.getMethod().getMethodName())
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String path = ScreenshotUtil.captureScreenshot(result.getName(), "PASS");
        ExtentTestManager.getTest().pass("Test passed", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String path = ScreenshotUtil.captureScreenshot(result.getName(), "FAIL");
        ExtentTestManager.getTest().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(path).build());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String path = ScreenshotUtil.captureScreenshot(result.getName(), "SKIP");
        ExtentTestManager.getTest().skip("Test skipped", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flushReports();
        ExtentTestManager.unload();
    }
}