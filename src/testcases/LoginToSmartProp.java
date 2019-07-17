package testcases;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.CommonClass;
import listeners.Retry;
import utilities.TestUtils;

public class LoginToSmartProp extends CommonClass {
	@Test(dataProvider="getData",dataProviderClass=TestUtils.class,retryAnalyzer = Retry.class)
	public void navigateTo_MasterReport(Hashtable<String, String> data) throws Exception {
		getElement("CustomerLoginButton").click();
		getElement("UsernameTextBox").sendKeys(data.get("Username"));
		getElement("PasswordTextBox").sendKeys(data.get("Password"));
		getElement("SignInButton").click();
//		Assert.fail();
		Thread.sleep(3000);
	}
}
