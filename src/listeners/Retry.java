package listeners;
import java.io.IOException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import generic.CommonClass;
import utilities.ExcelUtility;
import utilities.TestUtils;

public class Retry extends CommonClass implements IRetryAnalyzer {
	private int count = 0;
	private static int maxTry = 3;
	@Override
	public boolean retry(ITestResult result) {
		if (!result.isSuccess()) {                      //Check if test not succeed
			if (count < maxTry) {                            //Check if maxtry count is reached
				count++;                                     //Increase the maxTry count by 1
				result.setStatus(ITestResult.FAILURE);  //Mark test as failed
				return true;                                 //Tells TestNG to re-run the test
			} else {
				result.setStatus(ITestResult.FAILURE);  
				String testName = result.getName();
				String sheetName=testName.substring(testName.indexOf("_")+1)+"_TestCase";
				TestUtils.setTestResultExcel(sheetName, testName, "FAIL", new ExcelUtility(System.getProperty("user.dir")+excelPro.getProperty("path")));
				TestUtils.captureScreenshot(driver, result.getMethod().getMethodName());
				test.log(Status.FAIL, testName.toUpperCase()+" is FAILED due to :- "+result.getThrowable().toString());
				try {
					test.addScreenCaptureFromPath(TestUtils.screenshotPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
				extent.flush();
				//If maxCount reached,test marked as failed
			}
		} else {
			String testName = result.getName();
			String sheetName=testName.substring(testName.indexOf("_")+1)+"_TestCase";
			TestUtils.setTestResultExcel(sheetName, testName, "PASS", new ExcelUtility(System.getProperty("user.dir")+excelPro.getProperty("path")));
			test.log(Status.PASS, testName.toUpperCase()+" is PASSED");
			extent.flush();     //If test passes, TestNG marks it as passed
		}
		return false;
	}
}
