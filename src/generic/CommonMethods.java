package generic;
import java.time.LocalDate;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.aventstack.extentreports.Status;

public class CommonMethods extends CommonClass{
	private static Logger log=Logger.getLogger("Common Methods");
	private  WebElement ele;
	private JavascriptExecutor js=(JavascriptExecutor) driver;
	public  void clickEle(String locator) {
		try {
			ele=getElement(locator);
			ele.click();
			log.info("Clickin on "+locator);
			test.log(Status.INFO, "Click on "+locator);
		} catch (Exception e) {
			log.info("Failed to Click on  "+locator+" :    "+e.getMessage());
		}
	}
	public void enterText(String locator, String data) {
		try {
			ele=getElement(locator);
			ele.sendKeys(data);
			log.info("Entered "+data+" in "+locator);
			test.log(Status.INFO, "Enter "+data+" in "+locator);
		} catch (Exception e) {
			log.info("Failed to enter text in "+locator+" :    "+e.getMessage());
		}
	}
	public void mouseHover(WebDriver driver, String locator) {
		try {
			Actions act=new Actions(driver);
			act.moveToElement(getElement(locator)).build().perform();
			log.info("Mouse Hovering to "+locator);
			test.log(Status.INFO, "Mouse Hover on "+locator);
		} catch (Exception e) {
			log.info("Cannot Mouse Hover to +"+locator+" :    "+e.getMessage());
		}
	}
	
	public String datePicker(int days) {
		String datePick=null;
		LocalDate now = LocalDate.now();
		String[] date= now.minusDays(days).toString().split("-",3);
		datePick=date[1]+"/"+date[2]+"/"+date[0];
		log.info("Date to be selected is : "+datePick);
		return datePick ;
	}
	
	public Select dropDown(String locator) {
		Select s = new Select(getElement(locator));
		log.info("Created object of drop down "+locator);
		return s;
	}
	public String getFirstSelectedItem(String locator) {
		String option=null;
		Select s = new Select(getElement(locator));
		option = s.getFirstSelectedOption().getText();
		return option;
	}
	
	public void javaScriptClick(String locator) {
		js.executeScript("arguments[0].click()", getElement(locator));
		log.info("Clickin on "+locator);
		test.log(Status.INFO, "Click on "+locator);
	}
	public void javaScriptEnterText(String locator,String data) {
		js.executeScript("arguments[0].value='"+data+"'", getElement(locator));
		log.info("Entered "+data+" in "+locator);
		test.log(Status.INFO, "Enter "+data+" in "+locator);
	}
}
