package PageObjects;

import Base.TestBase;

import SeleniumActions.IButton;
import SeleniumActions.ILink;
import SeleniumActions.IPageutil;
import Utilities.IExplicitWait;
import Utilities.ReadExcelData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HistoricalDataPage extends TestBase implements ILink, IExplicitWait, IPageutil, IButton {
    ReadExcelData readExcelData = ReadExcelData.getInstance();

    By daysTradeList = By.xpath("//div[@id='historical-trade']//ul[@class='dayslisting']//li/a");
    By filterData = By.xpath("//button[@id='filter']");
    By download = By.xpath("//span[@id='dwldcsv']");
    By tradeInfo = By.xpath("//a[@id='infoTrade']");
    By OneYearTradeData = By.xpath("//div[@id='historical-trade']//ul[@class='dayslisting']//li//a[text()='1Y']");

    public void setDaysTradeList(String noOfDays) {
        try {
            List<WebElement> results = getDriver().findElements(daysTradeList);
            for (WebElement list : results) {
                if (list.getText().contains(noOfDays)) {
                    clickOnLink(getDriver(), list, "NoOf Trade Days " + noOfDays, "Async");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object[][] downloadAndFetchData(String DaysOfTradeList) {
        waitForPageToLoad(getDriver(), 10);
        Object data[][] = null;
        try {
            click(getDriver(), explicitWaitVisibilityOfElementLocated(getDriver(), OneYearTradeData, 5), " One Year trade data ", "Async");
            click(getDriver(), explicitWaitVisibilityOfElementLocated(getDriver(), filterData, 5), " filter ", "");
            click(getDriver(), explicitWaitVisibilityOfElementLocated(getDriver(), download, 5), " download csv  ", "");

            String file = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads";
            String filePath = String.valueOf(getLastModifiedFromFolder(file, getDriver()));
            readExcelData.convertCSVToExcel(filePath);
            data = readExcelData.readData("data.xls");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public Map<String, String> get52WeeksHighAndLowValues(String DaysOfTradeList) {

        Map<String, String> values = new HashMap<>();
        try {
            Object[][] data = downloadAndFetchData(DaysOfTradeList);
            LinkedList<Double> list = new LinkedList<>();
            for (int i = 1; i < data.length; i++) {
                list.add(Double.parseDouble((String) data[i][3]));
            }
            values.put("Expected_52WeeksHighValue", String.format("%.2f", (list.stream().max(Double::compare).get())));
            list.clear();
            for (int i = 1; i < data.length; i++) {
                list.add(Double.parseDouble((String) data[i][4]));
            }
            values.put("Expected_52WeeksLowValue", String.format("%.2f", (list.stream().min(Double::compare).get())));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    public StockDetailsPage clickOnTradeInfoLink() {
        clickOnLink(getDriver(), explicitWaitVisibilityOfElementLocated(getDriver(), tradeInfo, 5), "Stock details link", "");

        return new StockDetailsPage();
    }


}
