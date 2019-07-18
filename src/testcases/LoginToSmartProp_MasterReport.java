package testcases;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import generic.CommonClass;
import generic.CommonMethods;
import listeners.Retry;
import utilities.TestUtils;

public class LoginToSmartProp_MasterReport extends CommonClass {
	@Test(dataProvider="getData",dataProviderClass=TestUtils.class,retryAnalyzer = Retry.class)
	public void  loginToSmartProp_MasterReport(Hashtable<String, String> data,Method m) throws Exception {
		CommonMethods cm = new CommonMethods();
		cm.clickEle("CustomerLoginButton");
		cm.enterText("UsernameTextBox", data.get("Username"));
		cm.enterText("PasswordTextBox", data.get("Password"));
		cm.clickEle("SignInButton");
		Assert.fail();
		Thread.sleep(3000);
	}
}
