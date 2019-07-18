package utilities;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;
import generic.CommonClass;

public class TestUtils extends CommonClass {
	private static Logger log=Logger.getLogger("TestUtils");
	public static String screenshotPath;
	private static ExtentReports extent;
	private static ExtentHtmlReporter rep;
	//--------------------------------------------------------------------DATAPROVIDER----------------------------------------------------------------------------------------------------------------------//

	@BeforeTest
	@DataProvider(name="getData")
	public static Object[][] getData(Method m) {
		ExcelUtility excel = new ExcelUtility(System.getProperty("user.dir")+excelPro.getProperty("path"));
		String testCaseName=m.getName();
		int totalRows = excel.getRowCount(excelPro.getProperty("sheetName"));

		//----------Finding TestCase-----------//

		int testCaseRow = 0;
		for (testCaseRow = 0; testCaseRow < totalRows; testCaseRow++) {
			String testCase = excel.getCellValue(excelPro.getProperty("sheetName"), testCaseRow, 0);
			if(testCase.equals(testCaseName))
				break;
		}

		//-----------Finding number of rows in TestCase---------//

		int dataRowStart=testCaseRow+2;
		int rows=0;
		while(!excel.getCellValue(excelPro.getProperty("sheetName"), dataRowStart+rows, 0).equals("")) {
			rows++;
		}

		//-----------Finding number of columns in TestCase---------------//

		int dataColStart=testCaseRow+1;
		int cols=1;
		while(!excel.getCellValue(excelPro.getProperty("sheetName"), dataColStart, cols).equals("")) {
			cols++;
		}

		//-------------Getting the data of TestCase-------------------//

		Object[][] data = new Object[rows][1];
		int i=0;
		for (int row = dataRowStart; row < dataRowStart+rows; row++) {
			Hashtable<String, String> table = new Hashtable<String, String>();
			for (int col = 0; col < cols; col++) {
				String value = excel.getCellValue(excelPro.getProperty("sheetName"), row, col);
				String column = excel.getCellValue(excelPro.getProperty("sheetName"), dataColStart, col);
				table.put(column, value);
			}
			data[i][0]=table;
			i++;
		}
		return data;
	}

	//--------------------------------------------------------TO CAPTURE SCREENSHOT----------------------------------------------------------------------------------------------------------------------//

	public static void captureScreenshot(WebDriver driver, String testCaseName) {
		String date_time = new Date().toString().replaceAll(":", "_").replaceAll(" ", "_");
		screenshotPath=System.getProperty("user.dir")+"\\resources\\ScreenShots\\"+testCaseName.toUpperCase()+date_time+".png";
		try {
			log.info("Capturing Screenshot");
			TakesScreenshot ss = (TakesScreenshot) driver;
			File srcFile = ss.getScreenshotAs(OutputType.FILE);     
			Files.copy(srcFile, new File(screenshotPath));
		} catch (Exception e) {
			log.error("Failed to capture Screenshot");
			e.printStackTrace();
		}
	}

	//---------------------------------------------------------SET DATA RESULT IN EXCEL-------------------------------------------------------------------------------------------------------------------//

	public static void setTestResultExcel(String sheetName,String testCaseName,String data, ExcelUtility excel) {
		try {
			for (int i = 1; i <= excel.getRowCount(sheetName); i++) {
				if(excel.getCellValue(sheetName, "TestCaseID", i).equalsIgnoreCase(testCaseName)) {
					excel.setCellValue(sheetName, "Status", i, data);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//-------------------------------------------------------EXTENT REPORTS---------------------------------------------------------------------------------------------------------------------------------//

	public static ExtentReports getExtentReport() {
		extent=new ExtentReports();
		rep=new ExtentHtmlReporter(extentPath);
		rep.config().setTheme(Theme.DARK);
		rep.config().setReportName("SmartProp Report");
		rep.config().setDocumentTitle("Report For SmartProp");
		extent.attachReporter(rep);
		extent.setSystemInfo("Name", "SmartProp");
		extent.setSystemInfo("Platform", "Chrome Browser Version 74 ");
		extent.setSystemInfo("Author", "Ranjith");
		return extent;
	}

	//--------------------------------------------------------CHECK TESTCASE IS RUNNABLE OR NOT----------------------------------------------------------------------------------------------------//

	public static boolean isTestCaseRunnable(String sheetName, String testCaseName,ExcelUtility excel) {
		boolean isRunnable=false;
		String sheet=sheetName;
		for (int i = 1; i < excel.getRowCount(sheet); i++) {
			if(excel.getCellValue(sheet, "TestCaseID", i).equalsIgnoreCase(testCaseName)) {
				if(excel.getCellValue(sheet, "RunMode", i).equals("YES")) {
					isRunnable=true;
				}
				else if(excel.getCellValue(sheet, "RunMode", i).equals("NO")) {
					isRunnable=false;
				}
			}
		}
		return isRunnable;
	}
}
