package listeners;
import org.testng.IClassListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITest;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.internal.annotations.IBeforeMethod;
import org.testng.internal.annotations.IBeforeTest;
import com.aventstack.extentreports.Status;
import generic.CommonClass;
import utilities.ExcelUtility;
import utilities.TestUtils;

public class CustomListeners extends CommonClass implements ITestListener, IClassListener{
	public void onTestStart(ITestResult result) {
	}
	public void onTestSuccess(ITestResult result) {
		String testName = result.getName();
		String sheetName=testName.substring(testName.indexOf("_")+1)+"_TestCase";
		TestUtils.setTestResultExcel(sheetName, testName, "PASS", new ExcelUtility(System.getProperty("user.dir")+excelPro.getProperty("path")));
		test.log(Status.PASS, testName.toUpperCase()+" is PASSED");
		extent.flush();     //If test passes, TestNG marks it as passed
	
	}

	public void onTestFailure(ITestResult result) {

	}

	public void onTestSkipped(ITestResult result) {
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onStart(ITestContext context) {
	}

	public void onFinish(ITestContext context) {

	}

	public void onBeforeClass(ITestClass testClass) {
		String testName = testClass.getName().replaceAll("testcases.", "");
		test=extent.createTest(testName.toUpperCase());
		String sheetName=testName.substring(testName.indexOf("_")+1)+"_TestCase";
		System.out.println(TestUtils.isTestCaseRunnable(sheetName, testName,new ExcelUtility(System.getProperty("user.dir")+excelPro.getProperty("path"))));
		if(TestUtils.isTestCaseRunnable(sheetName, testName,new ExcelUtility(System.getProperty("user.dir")+excelPro.getProperty("path")))) {
			TestUtils.setTestResultExcel(sheetName, testName,"SKIP",new ExcelUtility(System.getProperty("user.dir")+excelPro.getProperty("path")));
			test.log(Status.SKIP, testName.toUpperCase()+" is SKIPPED due to RunMode is NO");
			extent.flush();
			throw new SkipException("Skipped the test case "+testName.toUpperCase()+" as the RunMode is No");
		}
	}

	public void onAfterClass(ITestClass testClass) {
		
	}
}