package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

public interface IExplicitWait {
    default void waitForPageToLoad(WebDriver driver, Integer time) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebDriverWait clickableWait = new WebDriverWait(driver, time);
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

    default WebElement explicitWaitInvisibilityOfElementLocated(WebDriver driver, By element, int time) {

        WebDriverWait waitForPresence = new WebDriverWait(driver, time);

        return waitForPresence.until(ExpectedConditions.presenceOfElementLocated(element));

    }

    default WebElement explicitWaitElementToBeClickable(WebDriver driver, By element, int time) {

        WebDriverWait waitForPresence = new WebDriverWait(driver, time);

        return waitForPresence.until(ExpectedConditions.elementToBeClickable(element));
    }

    default WebElement explicitWaitVisibilityOfElementLocated(WebDriver driver, By element, int time) {

        WebDriverWait waitForPresence = new WebDriverWait(driver, time);

        return waitForPresence.until(ExpectedConditions.visibilityOfElementLocated(element));
    }
}
