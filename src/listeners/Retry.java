package listeners;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import generic.CommonClass;
import utilities.ExcelUtility;
import utilities.TestUtils;

public class Retry extends CommonClass implements IRetryAnalyzer {
	private Logger log=Logger.getLogger("RetryAnalyzer");
	private int count = 0;
	private static int maxTry = 2;
	@Override
	public boolean retry(ITestResult result) {
		String testName = result.getName();
		log.info("Retryig failed test case : "+testName);
		String sheetName=testName.substring(testName.indexOf("_")+1)+"_TestCase";
		if (!result.isSuccess()) {                      //Check if test not succeed
			if (count < maxTry) {                            //Check if maxtry count is reached
				count++;                                     //Increase the maxTry count by 1
				result.setStatus(ITestResult.FAILURE);  //Mark test as failed
				return true;                                 //Tells TestNG to re-run the test
			} else {
				result.setStatus(ITestResult.FAILURE);  
				TestUtils.setTestResultExcel(sheetName, testName, "FAIL", new ExcelUtility(System.getProperty("user.dir")+excelPro.getProperty("path")));
				String screenshot = TestUtils.captureScreenshot(driver, result.getName());
				try {
					test.log(Status.FAIL, MarkupHelper.createLabel(testName.toUpperCase(), ExtentColor.RED).getMarkup(), MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
				} catch (IOException e) {
					e.printStackTrace();
				}
				extent.flush();
				//If maxCount reached,test marked as failed
			}
		} else {
			TestUtils.setTestResultExcel(sheetName, testName, "PASS", new ExcelUtility(System.getProperty("user.dir")+excelPro.getProperty("path")));
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName().toUpperCase()+" PASSED ", ExtentColor.GREEN));
			extent.flush();     //If test passes, TestNG marks it as passed
		}
		return false;
	}
}
