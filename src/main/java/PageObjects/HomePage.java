package PageObjects;

import Base.TestBase;
import SeleniumActions.ILink;
import SeleniumActions.ITextbox;
import Utilities.ExplicitWaiting;
import Utilities.IExplicitWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends TestBase implements ILink, ITextbox, IExplicitWait {

    static String getStockName;

    By search = By.xpath("//input[@id='header-search-input']");
    By searchResults = By.xpath("//div[@id='searchListing']//a");

    public HomePage() {
        PageFactory.initElements(getDriver(), this);
    }


    public StockDetailsPage stockSearching(String stockName) {
        text(getDriver(), getDriver().findElement(search), "search", stockName, "sendkeysandenter");
        waitForPageToLoad(getDriver(),5);
        clickOnStock(stockName);

        return new StockDetailsPage();
    }

    public void clickOnStock(String stockName) {
        List<WebElement> results = getDriver().findElements(searchResults);
        for (WebElement list : results) {
            if (list.getText().toLowerCase().contains(stockName)) {
                getStockName = list.getText();
                clickOnLink(getDriver(), list, "stock", "");
                break;
            }
        }

    }

}
