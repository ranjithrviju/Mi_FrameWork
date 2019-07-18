package testcases;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.CommonClass;
import listeners.Retry;
import utilities.TestUtils;

public class LoginToSmartProp_MasterReport extends CommonClass {
	@Test(dataProvider="getData",dataProviderClass=TestUtils.class,retryAnalyzer = Retry.class)
	public void  loginToSmartProp_MasterReport(Hashtable<String, String> data,Method m) throws Exception {
		getElement("CustomerLoginButton").click();
		getElement("UsernameTextBox").sendKeys(data.get("Username"));
		getElement("PasswordTextBox").sendKeys(data.get("Password"));
		getElement("SignInButton").click();
//		Assert.fail();
		Thread.sleep(3000);
		System.out.println("The TestCase  Name is : "+m.getName());
	}
}
