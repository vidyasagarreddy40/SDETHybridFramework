package TestSuite;

import Base.TestBase;
import PageObjects.HistoricalDataPage;
import PageObjects.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class FunctionalCases extends TestBase {


    HomePage homePage = new HomePage();
    HistoricalDataPage historicalDataPage = new HistoricalDataPage();

    @Test()
    public void Weeks52LowAndHighValuesValidationTest() {
        Map<String, String> expectedValues = homePage.stockSearching("tatamotors").switchWindowToStockPage().
                clickOnHistoricalDataLink().get52WeeksHighAndLowValues("1Y");
        Map<String, String> actualValues = historicalDataPage.clickOnTradeInfoLink().retrieveStock52HighAndLowPrice();

        asserting(actualValues.get("Actual_52WeeksLowValue"), expectedValues.get("Expected_52WeeksLowValue"));
        asserting(actualValues.get("Actual_52WeeksHighValue"), expectedValues.get("Expected_52WeeksHighValue"));

        softAssert.assertAll();
    }


}
