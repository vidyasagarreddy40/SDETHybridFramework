package Reports;

import Base.TestBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.asserts.SoftAssert;

public class loggerlogsCopy {

    public Logger log;
    private static final ThreadLocal<Logger> logLocal = new ThreadLocal<>();
    public static String browserName;
    public static String browserVersion;
    SoftAssert softAssert = new SoftAssert();

    public loggerlogsCopy() {
       // log = LogManager.getLogger(this.getClass());
    }

    private String currentClassName() {
        return this.getClass().getSimpleName();
    }

    public synchronized void createLogs() {
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
                    break;
                case "debug":
                    logLocal.get().warn(stringFormatMsg, otherVarArgs);
                    break;
                case "error":
                    logLocal.get().error(stringFormatMsg, otherVarArgs);
                    break;
                case "warn":
                    // logLocal.get().warn("RP_MESSAGE#FILE#{}#{}", otherVarArgs[0], stringFormatMsg);
                    logLocal.get().warn(stringFormatMsg, otherVarArgs);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
