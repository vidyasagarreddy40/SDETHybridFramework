package Base;

import Reports.extentReportGeneration;
import Reports.loggerLogs;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

public class TestBase {
    public static ThreadLocal<WebDriver> tDriver = new ThreadLocal<>();
    static loggerLogs logs;
    public ExtentReports extent;
    public ExtentSparkReporter spark;
    public ExtentTest test;
    public extentReportGeneration extentreportgeneration = new extentReportGeneration();
    public static SoftAssert softAssert = new SoftAssert();
    Properties properties;
    public TestBase() {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(".//src//main//resources//propertyfiles//propertiesFile.properties"));
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDriver(String driverName) {
        try {
            driverName = driverName.toLowerCase();
            switch (driverName) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    chromeOptions.addArguments("--disable-extensions");
                    tDriver.set(new ChromeDriver(chromeOptions));
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--remote-allow-origins=*");
                    edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
                    edgeOptions.addArguments("--disable-extensions");
                    tDriver.set(new EdgeDriver(edgeOptions));
                    break;
                case "firfox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--remote-allow-origins=*");
                    firefoxOptions.addArguments("--disable-blink-features=AutomationControlled");
                    firefoxOptions.addArguments("--disable-extensions");
                    tDriver.set(new FirefoxDriver(firefoxOptions));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebDriver getDriver() {
        return tDriver.get();
    }

    @BeforeSuite
    public void beforeSuite() {


    }

    @BeforeTest
    public void logSet(){
        extent = new ExtentReports();
        spark = new ExtentSparkReporter("target/Report.html");
        extentreportgeneration.setReportTheme();
    }

    @Parameters("browser")
    @BeforeMethod()
    public void setUp(String browser, ITestResult result) {

        logs = loggerLogs.getInstance();
        setDriver(browser);
        logs.createLogs();
        extentreportgeneration.createTestInReport(result.getMethod().getXmlTest().getName() + " - " + result.getMethod().getMethodName());
        logs.logger("info", "Initialising the browser", TestBase.class.getSimpleName(), "");
        getDriver().manage().deleteAllCookies();
        getDriver().get(properties.getProperty("url"));
        logs.logger("info", "Navigated to "+properties.getProperty("url"), TestBase.class.getSimpleName(), "");
        getDriver().manage().window().maximize();
        logs.logger("info", "Window is maximised", TestBase.class.getSimpleName(), "");
    }

    @AfterMethod
    public void tearDown(ITestResult iTestResult) {
        if (getDriver() != null) {
            getDriver().quit();
            tDriver.remove();
        }
    }
    public void asserting(Object actual, Object expected){
        try {
            if (actual.equals(expected)) {
                logs.logger("info", Thread.currentThread().getStackTrace()[2].getMethodName() + " - " + "Assertion is passed : actual is - " + actual + " as expected to : " + expected, "");
            } else {
                softAssert.fail(Thread.currentThread().getStackTrace()[2].getMethodName() + " - " + "Assertion is failed : actual is - " + actual + " not as expected : " + expected);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
