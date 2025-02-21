package SeleniumActions;

import Reports.extentReportGeneration;
import Reports.loggerLogs;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface ILink {

    loggerLogs logs = loggerLogs.getInstance();


    default String clickOnLink(WebDriver driver, WebElement element, String elementName, String triggereventValue) {
        String status = "";
        String triggerEvent = "";
        try {
            if (element == null) {
                status = "fail";
            }
            if (triggereventValue != null) {
                triggerEvent = triggereventValue;
            }
            if (triggerEvent.equalsIgnoreCase("js")) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].focus;", element);
                js.executeScript("arguments[0].scrollIntoView(true);", element);
                js.executeScript("window.onbeforeunload=null;", element);
                js.executeScript("arguments[0].click();", element);

                try {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                } catch (Exception e) {

                }
            }
            if (triggerEvent.equalsIgnoreCase("Async")) {
                element.click();
                Thread.sleep(3000);
                try {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                } catch (Exception e) {

                }

            } else {
                try {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                } catch (Exception e) {

                } finally {
                    element.click();
                }
            }
            status = "pass";
        } catch (Exception e) {
            status = "fail";
            e.printStackTrace();
            logs.logger("error", Thread.currentThread().getStackTrace()[2]+" - "+elementName + " is failed to click" + "/n" + e, ILink.class.getSimpleName(), "");
        } finally {
            if (status.equalsIgnoreCase("pass")) {
                logs.logger("info", Thread.currentThread().getStackTrace()[2]+"-"+"Clicked on the " + elementName + " in " + driver.getTitle(), ILink.class.getSimpleName(), "");
            }
            return status;
        }
    }
}
