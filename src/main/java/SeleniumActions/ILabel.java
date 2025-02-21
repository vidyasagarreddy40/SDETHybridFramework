package SeleniumActions;

import Reports.extentReportGeneration;
import Reports.loggerLogs;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface ILabel {
    loggerLogs logs = loggerLogs.getInstance();

    default String getText(WebDriver driver, WebElement element) {
        String status = "";
        String elementInfo = element.toString();

        try {
            if (element == null) {
                status = "fail";
            }
            String a, actualText;

            if (element.getAttribute("value") == null) {
                a = "";
            } else {
                a = element.getAttribute("value");
            }

            if (element.getText() == null || element.getText().isEmpty()) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].focus;", element);
                js.executeScript("arguments[0].scrollIntoView(true);", element);
                actualText = (String) js.executeScript("return arguments[0].innerHTML", element);
            } else {
                actualText = element.getText();
            }
            a = a.trim();
            actualText = actualText.trim();
            if(a.length()>=actualText.length()){
                status=a;
            }else{
                status=actualText;
            }
        } catch (Exception e) {
            status = "fail";
            e.printStackTrace();
            logs.logger("error", elementInfo + " is failed to fetch the value from" + "/n" + e, Thread.currentThread().getStackTrace()[2].getClassName(), "",Thread.currentThread().getStackTrace()[2]);
        } finally {
            if (!status.equalsIgnoreCase("fail")) {
                logs.logger("info", elementInfo + " has fetched the value - "+status , ILabel.class.getSimpleName(), "",Thread.currentThread().getStackTrace()[2]);
            }
            return status;
        }
    }
}
