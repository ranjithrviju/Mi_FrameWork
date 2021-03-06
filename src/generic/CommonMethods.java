package generic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
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

	//-----------------------------------------------------------------------------------------CLICK()-----------------------------------------------------------------------------------------------------------------//

	public  void clickEle(String locator) {
		try {
			ele=getElement(locator);
			ele.click();
			log.info("Clickin on "+locator);
			test.log(Status.INFO, "Click on "+locator);
		} catch (Exception e) {
			log.info("Failed to Click on  "+locator+" :    "+e.getMessage());
			e.printStackTrace();
		}
	}

	//---------------------------------------------------------------------------------SENDKEYS()-----------------------------------------------------------------------------------------------------------------//

	public void enterText(String locator, String data) {
		try {
			ele=getElement(locator);
			ele.sendKeys(data);
			log.info("Entered "+data+" in "+locator);
			test.log(Status.INFO, "Enter "+data+" in "+locator);
		} catch (Exception e) {
			log.info("Failed to enter text in "+locator+" :    "+e.getMessage());
			e.printStackTrace();
		}
	}

	//---------------------------------------------------------------------------------MOVE TO ELEMENT()-----------------------------------------------------------------------------------------------------------------//

	public void actionsClass(WebDriver driver, String locator,String action) {
		try {
			Actions act=new Actions(driver);
			if(action=="moveToElement") {
				act.moveToElement(getElement(locator)).build().perform();
				log.info("Mouse Hovering to "+locator);
				test.log(Status.INFO, "Mouse Hover on "+locator);
			}
			if(action=="click") {
				act.moveToElement(getElement(locator)).click().build().perform();
				log.info("Mouse Hovering to "+locator);
				test.log(Status.INFO, "Mouse Hover on "+locator);
			}
		} catch (Exception e) {
			log.info("Cannot Mouse Hover to +"+locator+" :    "+e.getMessage());
			e.printStackTrace();
		}
	}

	//---------------------------------------------------------------------------------DATE SELECTER-----------------------------------------------------------------------------------------------------------------//

	public String datePicker(int days) {
		String datePick=null;
		LocalDate now = LocalDate.now();
		String[] date= now.minusDays(days).toString().split("-",3);
		datePick=date[1]+"/"+date[2]+"/"+date[0];
		log.info("Date to be selected is : "+datePick);
		return datePick ;
	}

	//---------------------------------------------------------------------------------SELECT CLASS-----------------------------------------------------------------------------------------------------------------//

	public Select dropDown(String locator) {
		Select s = new Select(getElement(locator));
		log.info("Created object of drop down "+locator);
		return s;
	}

	//---------------------------------------------------------------------------------GET FIRST SELECTED OPTION-----------------------------------------------------------------------------------------------------------------//

	public String getFirstSelectedItem(String locator) {
		String option=null;
		Select s = new Select(getElement(locator));
		option = s.getFirstSelectedOption().getText();
		return option;
	}

	//---------------------------------------------------------------------------------JAVASCRIPT CLICK-----------------------------------------------------------------------------------------------------------------//

	public void javaScriptClick(String locator) {
		js.executeScript("arguments[0].click()", getElement(locator));
		log.info("Clicking on "+locator);
		test.log(Status.INFO, "Click on "+locator);
	}

	//---------------------------------------------------------------------------------JAVASCRIPT ENTER TEXT-----------------------------------------------------------------------------------------------------------------//

	public void javaScriptEnterText(String locator,String data) {
		js.executeScript("arguments[0].value='"+data+"'", getElement(locator));
		log.info("Entered "+data+" in "+locator);
		test.log(Status.INFO, "Enter "+data+" in "+locator);
	}

	//---------------------------------------------------------------------------------GET LIST-----------------------------------------------------------------------------------------------------------------//

	public  List<String> getList(List<WebElement> eleList ) {
		List<String> list=new ArrayList<>();
		for (WebElement ele : eleList) {
			list.add(ele.getText());
		}
		return list;
	}

	//---------------------------------------------------------------------------------ACCEPT ALERT-----------------------------------------------------------------------------------------------------------------//

	public Alert acceptAlert(WebDriver driver) {
		test.info("Switching to Alert");
		return driver.switchTo().alert();
	}
	
	//---------------------------------------------------------------------------------CLICK ELEMENT FROM LIST-----------------------------------------------------------------------------------------------------------------//
	
	public WebElement clickElementInList(String locator, String option) {
		List<WebElement> eles = getListOfElement(locator);
		for (WebElement ele : eles) {
			if(ele.getText().contains(option)) {
				ele.click();
				log.info("Clicked on "+option);
				test.info("Clicked on "+option);
				return ele;
			}
		}
		return null;
	}
	
}
