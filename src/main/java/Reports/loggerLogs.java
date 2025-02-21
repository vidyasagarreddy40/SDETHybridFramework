package Reports;

import Base.TestBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class loggerLogs extends TestBase {
    public extentReportGeneration extentreportgeneration = new extentReportGeneration();
    private static final ThreadLocal<Logger> logLocal = new ThreadLocal<>();
    private static loggerLogs instance = new loggerLogs();
    private static String browserName;
    private static String browserVersion;
    public static loggerLogs getInstance() {
        if(instance==null){
            instance= new loggerLogs();
        }
        return instance;
    }

    private loggerLogs() {

    }

    public  void createLogs() {
        logLocal.set(LogManager.getLogger(this.getClass()));
    }

    private String currentMethodName(String className) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int threadCount;
        for (threadCount = 0; threadCount < stackTrace.length; threadCount++) {
            if (stackTrace[threadCount].toString().contains(className) && !stackTrace[threadCount].getMethodName().equalsIgnoreCase("currentMethodName")) {
                break;
            }
        }
        return stackTrace[threadCount].getMethodName();
    }

    public void setBrowserAndversion(String browserName, String browserVersion) {
        Capabilities cap = ((RemoteWebDriver) new TestBase().getDriver()).getCapabilities();
        browserName = cap.getBrowserName().toLowerCase();
        browserVersion = cap.getVersion().toLowerCase();
        this.browserName = browserName;
        this.browserVersion = browserVersion;
    }

    public String getBrowserNameAndVersion() {
        setBrowserAndversion(browserName, browserVersion);
        return browserName + " " + browserVersion;
    }

    public void logger(String logType, String logMessage, String classname, Object... otherVarArgs) {
        try {
            String currentmethodname = currentMethodName(String.valueOf(classname));
            String stringFormatMsg = String.format("%1$s: %2$s_%3$s: %4$s", getBrowserNameAndVersion(), classname, currentmethodname, logMessage);
            switch (logType) {
                case "info":
                    logLocal.get().info(stringFormatMsg, otherVarArgs);
                    extentreportgeneration.reportLogs("pass",logMessage,getDriver());
                    break;
                case "debug":
                    logLocal.get().debug(stringFormatMsg, otherVarArgs);
                    break;
                case "error":
                    logLocal.get().error(stringFormatMsg, otherVarArgs);
                    extentreportgeneration.reportLogs("fail",logMessage,getDriver());
                    TestBase.softAssert.fail(logMessage);
                    break;
                case "warn":
                    logLocal.get().warn(stringFormatMsg, otherVarArgs);
                    extentreportgeneration.reportLogs("warning",logMessage,getDriver());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
