package generic;

public interface IConstants {
	String configPath=System.getProperty("user.dir")+"\\resources\\PropertyFiles\\config.properties";
	String objectPath=System.getProperty("user.dir")+"\\resources\\PropertyFiles\\object.properties";
	String excelPath=System.getProperty("user.dir")+"\\resources\\PropertyFiles\\excel.properties";
	String chromeKey="webdriver.chrome.driver";
	String chromePath=System.getProperty("user.dir")+"\\resources\\Drivers\\chromedriver.exe";
	String fireFoxKey="webdriver.gecko.driver";
	String fireFoxPAth=System.getProperty("user.dir")+"\\resources\\Drivers\\geckodriver.exe";
	String extentPath=System.getProperty("user.dir")+"\\resources\\ExtentReport\\ExtentReport.html";
	int wait=10;
	int exwait=20;
}
