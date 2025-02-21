package Reports;

import Base.TestBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class extentReportGeneration  {

    public static ExtentReports extent = new ExtentReports();
    public static ExtentSparkReporter spark = new ExtentSparkReporter("target/Report.html");
    public static ExtentTest test;

    private static final ThreadLocal<ExtentTest> extentTest= new ThreadLocal<>();


    public static ExtentReports setReportTheme() {
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("NSE Test Execution Report");
        extent.attachReporter(spark);
        extent.setSystemInfo("SDET","Vidyasagar Reddy R");

        return extent;
    }
    public void createTestInReport(String testName) {
        test = extent.createTest(testName);
        extentTest.set(test);
    }


    public void reportLogs(String logType, String logMesssage, WebDriver driver) {
        switch (logType) {
            case "info":
                extentTest.get().info(logMesssage);
                break;
            case "fail":
                extentTest.get().fail(logMesssage).addScreenCaptureFromBase64String(captureScreenshot(driver));
                break;
            case "warning":
                extentTest.get().warning(logMesssage);
                break;
            case "pass":
               extentTest.get().pass(logMesssage);
                break;
            case "skip":
                extentTest.get().skip(logMesssage);
                break;
        }
    }


    public  String captureScreenshot(WebDriver driver) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        String base64Code = takesScreenshot.getScreenshotAs(OutputType.BASE64);

        return base64Code;
    }

}
