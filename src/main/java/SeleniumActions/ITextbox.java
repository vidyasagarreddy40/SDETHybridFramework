package SeleniumActions;

import Base.TestBase;
import Reports.extentReportGeneration;
import Reports.loggerLogs;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface ITextbox {
    loggerLogs logs = loggerLogs.getInstance();


    default String text(WebDriver driver, WebElement element, String elementName, String value, String eventType) {

        String status = "";
        String triggerEvent = "";
        try {
            if (element == null) {
                status = "fail";
                return status;
            }
            if (eventType != null) {
                triggerEvent = eventType;
            }
            if(triggerEvent.toLowerCase().equalsIgnoreCase("sendkeysandenter")){
                element.sendKeys(value);
                element.sendKeys(Keys.ENTER);
            }
            if (triggerEvent.equalsIgnoreCase("")) {
                element.sendKeys(value);
                logs.logger("info", value + " is entered in " + elementName, ITextbox.class.getSimpleName(), "");
            }
            status = "pass";
        } catch (Exception e) {
            status = "fail";
            e.printStackTrace();
            logs.logger("error", "failed to enter the" + value + " in " + elementName + "/n" + e, ITextbox.class.getSimpleName(), "");
        } finally {
            if (status.equalsIgnoreCase("pass")) {
                logs.logger("info", Thread.currentThread().getStackTrace()[2]+"-"+value + " is entered in " + elementName, ITextbox.class.getSimpleName(), "");
            }
            else{
                TestBase.softAssert.fail("failed to intract with an "+ elementName);
            }
            return status;
        }
    }
}
