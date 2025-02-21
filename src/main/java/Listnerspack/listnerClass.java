package Listnerspack;

import Base.TestBase;
import Reports.extentReportGeneration;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class listnerClass extends TestBase implements ITestListener {


    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        extentreportgeneration.reportLogs("fail", iTestResult.getThrowable().getMessage(),getDriver());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        extentReportGeneration.extent.flush();
    }
}
