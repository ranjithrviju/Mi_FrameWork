package generic;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import utilities.ExcelUtility;
import utilities.TestUtils;

public class CommonClass implements IConstants {
	public static Properties config;
	public static Properties object;
	public static Properties excelPro;
	public static WebDriver driver;
	public static  WebDriverWait driverWait;
	public static ExtentReports extent=TestUtils.getExtentReport();
	public static ExtentTest test;
	
	//-------------------------------------------------------------------INITIALIZE THE LOGS-----------------------------------------------------------------------------------------------------------------------//
	
	@BeforeSuite
	public static void initialize() {
		try {
			config=new Properties();
			config.load(new FileInputStream(configPath));
		} catch (Exception e) {
		}
		try {
			object=new Properties();
			object.load(new FileInputStream(objectPath));
		} catch (Exception e) {
		}
		try {
			excelPro=new Properties();
			excelPro.load(new FileInputStream(excelPath));
		} catch (Exception e) {
		}
	}
	
	//-------------------------------------------------------------------TO GET THE ELEMENT-----------------------------------------------------------------------------------------------------------------------//
	
	public static synchronized WebElement getElement(String locator) {
		String locators=object.getProperty(locator);
		String[] objects=locators.split("-", 2);
		String locType=objects[0];
		String locValue=objects[1];
		WebElement ele=null;
		By by=null;
		if(locType.equals("IDE")) {
			by=By.id(locValue);
		}
		else if(locType.equals("NAME")) {
			by=By.name(locValue);
		}
		else if(locType.equals("XPH")) {
			by=By.xpath(locValue);
		}
		if(driver.findElements(by).size()>0) {
			try {
				ele=driver.findElement(by);
			} catch (NoSuchElementException e) {
			}
		}
		return ele;
	}

	//-------------------------------------------------------------------TO GET LIST OF ELEMENT------------------------------------------------------------------------------------------------------------------//
	
	public static synchronized List<WebElement> getListOfElement(String locator) {
		String locators=object.getProperty(locator);
		String[] objects=locators.split("-", 2);
		String locType=objects[0];
		String locValue=objects[1];
		List<WebElement> ele=null;
		By by=null;
		if(locType.equals("IDE")) {
			by=By.id(locValue);
		}
		else if(locType.equals("NAME")) {
			by=By.name(locValue);
		}
		else if(locType.equals("XPH")) {
			by=By.xpath(locValue);
		}
		if(driver.findElements(by).size()>0) {
			try {
				ele=driver.findElements(by);
			} catch (NoSuchElementException e) {
			}
		}
		return ele;
	}
	
	//-------------------------------------------------------------------LAUNCH THE BROWSER------------------------------------------------------------------------------------------------------------------//
	
	@Parameters("browser")
	@BeforeMethod

	public static void openApplication(String browser) {
		if(browser.equals("Chrome")) {
			System.setProperty(chromeKey, chromePath);
			driver=new ChromeDriver();
			driverWait=new WebDriverWait(driver, exwait);
		}
		else if(browser.equals("Firefox")) {
			System.setProperty(fireFoxKey, fireFoxPAth);
			driver=new FirefoxDriver();
			driverWait=new WebDriverWait(driver, exwait);
		}
		driver.manage().window().maximize();
		driver.get(config.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);
	}
	
	//---------------------------------------------------CHECKING THE RUNMODE OF TESTCASE--------------------------------------------------------------------------------------------------------//
	
	public static void checkRunMode(Method method){
		String testname = method.getName();
		String sheetName=testname.substring(testname.indexOf("_")+1)+"_TestCase";
		if(!TestUtils.isTestCaseRunnable(sheetName, testname,new ExcelUtility(System.getProperty("user.dir")+excelPro.getProperty("path")))) {
			closebrowser();
			throw new SkipException("Skipped the test case "+testname.toUpperCase()+" as the RunMode is No");
		}
	}
	
	//-------------------------------------------------------------------CLOSING THE BROWSER------------------------------------------------------------------------------------------------------------------//
	@AfterMethod
	public static void closebrowser() {
		if(driver!=null) {
			driver.quit();
		}
	}
}
