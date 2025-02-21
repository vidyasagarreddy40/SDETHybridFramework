package PageObjects;

import Base.TestBase;
import SeleniumActions.ILabel;
import SeleniumActions.ILink;
import SeleniumActions.IPageutil;
import Utilities.ExplicitWaiting;
import Utilities.IExplicitWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

public class StockDetailsPage extends TestBase implements IPageutil, IExplicitWait, ILabel, ILink {


    By HighValue_52Weeks = By.xpath("//span[@id='week52highVal']");
    By LowValue_52Weeks = By.xpath("//span[@id='week52lowVal']");
    By historicalDataLink = By.xpath("//h2[@id='historic_data']");

    public StockDetailsPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public synchronized Map<String, String> retrieveStock52HighAndLowPrice() {
        waitForPageToLoad(getDriver(),5);
        Map<String, String> values = new HashMap<>();
        values.put("Actual_52WeeksHighValue", getText(getDriver(), explicitWaitVisibilityOfElementLocated(getDriver(), HighValue_52Weeks, 5)).replace(",",""));
        values.put("Actual_52WeeksLowValue", getText(getDriver(), explicitWaitVisibilityOfElementLocated(getDriver(), LowValue_52Weeks, 5)).replace(",",""));

        return values;
    }

    public HistoricalDataPage clickOnHistoricalDataLink() {
        try {
            clickOnLink(getDriver(), explicitWaitVisibilityOfElementLocated(getDriver(), historicalDataLink, 5), "HistoricalData link", "Async");
           // Thread.sleep(5000);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new HistoricalDataPage();
    }

    public StockDetailsPage switchWindowToStockPage(){
        switchToWindowOnURL(getDriver(), "https://www.nseindia.com/get-quotes/equity?symbol=" + HomePage.getStockName);

        return new StockDetailsPage();
    }

}
