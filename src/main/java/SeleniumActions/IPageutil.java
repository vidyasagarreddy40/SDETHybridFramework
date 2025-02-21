package SeleniumActions;

import Base.TestBase;
import Reports.extentReportGeneration;
import Reports.loggerLogs;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Set;

import static java.lang.System.getProperty;

public interface IPageutil {
    loggerLogs logs = loggerLogs.getInstance();

    default String verifyURL(WebDriver driver, String expectedURL) {
        String status = "false";

        try {
            String actualURL = driver.getCurrentUrl();
            if (actualURL.toLowerCase().contains(expectedURL)) ;
            {
                status = "pass";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (status.equalsIgnoreCase("pass")) {
                logs.logger("info", Thread.currentThread().getStackTrace()[2] + " successfully navigated to " + expectedURL + " - " + Thread.currentThread().getStackTrace()[2].getClassName(), "");
            } else {
                logs.logger("error", Thread.currentThread().getStackTrace()[2] + " failed to navigated to " + expectedURL + " - " + Thread.currentThread().getStackTrace()[2].getClassName(), "");
                TestBase.softAssert.fail("failed to navigate to " + expectedURL);
            }
        }
        return status;
    }

    default String switchToWindowOnURL(WebDriver driver, String url) {
        String status = "false";
        try {
            String parentWindow = driver.getWindowHandle();
            Set<String> childWindows = driver.getWindowHandles();
            for (String child : childWindows) {
                if (!child.equalsIgnoreCase(parentWindow)) {
                    driver.switchTo().window(child);
                    if (driver.getCurrentUrl().contains(url)) {
                        status = "pass";
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (status.equalsIgnoreCase("pass")) {
                logs.logger("info", Thread.currentThread().getStackTrace()[2] + " successfully switch to the window of " + url + " - " + Thread.currentThread().getStackTrace()[2].getClassName(), "");
            } else {
                logs.logger("error", Thread.currentThread().getStackTrace()[2] + " failed to switch to the window of " + url + " - " + Thread.currentThread().getStackTrace()[2].getClassName(), "");
            }
        }
        return status;
    }

    default File getLastModifiedFromFolder(String directoryFilePath, WebDriver driver) {
        File chosenFile = null;
        try {
            File directory = new File(directoryFilePath);
            File[] files = directory.listFiles(File::isFile);
            long lastModifiedTime = Long.MIN_VALUE;


            if (files != null) {
                for (File file : files) {
                    if (file.lastModified() > lastModifiedTime) {
                        chosenFile = file;
                        lastModifiedTime = file.lastModified();
                    }
                }
            }
        } catch (Exception e) {
            logs.logger("error", Thread.currentThread().getStackTrace()[2] + " failed to fetch file from " + directoryFilePath + " - " +Thread.currentThread().getStackTrace()[2].getClassName() + " - "+ e.getMessage(), "");
        }finally {
            logs.logger("info", Thread.currentThread().getStackTrace()[2] + " successfully fetch file from " + directoryFilePath + " - " + Thread.currentThread().getStackTrace()[2].getClassName(), "");
        }

        return chosenFile;
    }


}
