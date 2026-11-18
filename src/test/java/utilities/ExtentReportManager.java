
package utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;

    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    String repName;

    // Start the report
    @Override
    public void onStart(ITestContext testContext) {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                .format(new Date());

        repName = "Test-Report-" + timeStamp + ".html";

        // Create reports folder if it does not exist
        new File(".\\reports\\").mkdirs();

        sparkReporter = new ExtentSparkReporter(
                ".\\reports\\" + repName);

        // Report configuration
        sparkReporter.config().setDocumentTitle(
                "RestAssuredAutomationProject");

        sparkReporter.config().setReportName(
                "Pet Store Users API");

        sparkReporter.config().setTheme(Theme.DARK);

        // Initialize ExtentReports
        extent = new ExtentReports();

        extent.attachReporter(sparkReporter);

        // System information
        extent.setSystemInfo("Application", "Pet Store Users API");

        extent.setSystemInfo("Operating System",
                System.getProperty("os.name"));

        extent.setSystemInfo("User Name",
                System.getProperty("user.name"));

        extent.setSystemInfo("Environment", "QA");

        extent.setSystemInfo("Automation Tool", "Rest Assured");

        extent.setSystemInfo("Testing Framework", "TestNG");
    }

    // When test starts
    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest extentTest = extent.createTest(
                result.getMethod().getMethodName());

        test.set(extentTest);

        test.get().log(Status.INFO, "Test Execution Started");
    }

    // When test passes
    @Override
    public void onTestSuccess(ITestResult result) {

        test.get().log(Status.PASS, "Test Passed");

    }

    // When test fails
    @Override
    public void onTestFailure(ITestResult result) {

        test.get().log(Status.FAIL, "Test Failed");

        test.get().fail(result.getThrowable());

    }

    // When test is skipped
    @Override
    public void onTestSkipped(ITestResult result) {

        test.get().log(Status.SKIP, "Test Skipped");

        if (result.getThrowable() != null) {
            test.get().skip(result.getThrowable());
        }

    }

    // When test execution finishes
    @Override
    public void onFinish(ITestContext testContext) {

        extent.flush();

    }

    // Get current ExtentTest object
    public static ExtentTest getTest() {

        return test.get();

    }
}
