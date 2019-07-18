package generic;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import utilities.TestUtils;

public class CommonClass implements IConstants {

	private static Logger log=Logger.getLogger("Common Class");
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
		log.info("Initializing all Log files");
		try {
			config=new Properties();
			config.load(new FileInputStream(configPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			object=new Properties();
			object.load(new FileInputStream(objectPath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			excelPro=new Properties();
			excelPro.load(new FileInputStream(excelPath));
		} catch (Exception e) {
			e.printStackTrace();
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
				log.info("Finding element : "+locator+" using :"+locType);
				ele=driver.findElement(by);
			} catch (NoSuchElementException e) {
				log.error("Failed find element : "+locator+" using :"+locType);
				e.printStackTrace();
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
				log.info("Finding element : "+locator+" using :"+locType);
				ele=driver.findElements(by);
			} catch (NoSuchElementException e) {
				log.error("Failed find element : "+locator+" using :"+locType);
				e.printStackTrace();
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
			log.info("Launching the "+browser+" browser");
			test.info("Launching the "+browser+" browser");
			driverWait=new WebDriverWait(driver, exwait);
		}
		else if(browser.equals("Firefox")) {
			System.setProperty(fireFoxKey, fireFoxPAth);
			driver=new FirefoxDriver();
			log.info("Launching the "+browser+" browser");
			test.info("Launching the "+browser+" browser");
			driverWait=new WebDriverWait(driver, exwait);
		}
		driver.manage().window().maximize();
		driver.get(config.getProperty("url"));
		log.info("Navigating to "+config.getProperty("url"));
		test.info("Navigating to "+config.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(wait, TimeUnit.SECONDS);
	}

	//-------------------------------------------------------------------CLOSING THE BROWSER------------------------------------------------------------------------------------------------------------------//
	
	@AfterMethod
	public static void closebrowser() {
		if(driver!=null) {
			log.info("Closing Browser");
			test.info("Closing Browser");
			driver.quit();
		}
	}
}
