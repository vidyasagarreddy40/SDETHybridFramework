package Utilities;

import Base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

public class ExplicitWaiting extends TestBase {


    public  void waitForPageToLoad( Integer time) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            WebDriverWait clickableWait = new WebDriverWait(getDriver(), time);
            clickableWait.until(new Function<WebDriver, Object>() {

                @Override
                public Object apply(WebDriver driver) {
                    Boolean jsload = false;
                    jsload = js.executeScript("return document.readyState").toString().equals("complete");
                    return jsload;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebElement explicitWaitInvisibilityOfElementLocated(WebDriver driver, By element, int time) {

        WebDriverWait waitForPresence = new WebDriverWait(driver, time);

        return waitForPresence.until(ExpectedConditions.presenceOfElementLocated(element));

    }

    public static WebElement explicitWaitElementToBeClickable(WebDriver driver, By element, int time) {

        WebDriverWait waitForPresence = new WebDriverWait(driver, time);

        return waitForPresence.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement explicitWaitVisibilityOfElementLocated(WebDriver driver, By element, int time) {

        WebDriverWait waitForPresence = new WebDriverWait(driver, time);

        return waitForPresence.until(ExpectedConditions.visibilityOfElementLocated(element));
    }
}
