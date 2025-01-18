package tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.Base;
import pages.HeaderOptions;
import pages.LogoutPage;
import pages.MyAccountPage;
import utils.CommonUtilities;

public class Login extends Base {
	
	WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		
		driver = openBrowserAndApplicationPageURL();
		headerOptions = new HeaderOptions(driver);
		headerOptions.clickOnMyAccountDropMenu();
		loginPage = headerOptions.selectLoginOption();
		
	}
	
	@AfterMethod
	public void teardown() {
		closeBrowser(driver);
	}
	
	@Test(priority = 1)
	public void verifyLoggingIntoApplicationUsingValidCredentails() {
		
		Assert.assertTrue(loginPage.didWeNavigateToLogin());
		loginPage.enterEmail(prop.getProperty("existingEmail"));
		loginPage.enterPassword(prop.getProperty("validPassword"));
		myAccountPage = loginPage.clickOnLoginButton();
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());
	
	}
	
	@Test(priority=2)
	public void verifyLoggingIntoApplicationUsingInvalidCredentials() {
		
		loginPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		loginPage.enterPassword(prop.getProperty("mismatchingPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getPageLevelWarning(),expectedWarning);
	
	}
	
	@Test(priority=3)
	public void verifyLoggingIntoApplicationUsingInvalidEmailAndValidPassword() {
		
		loginPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		loginPage.enterPassword(prop.getProperty("validPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getPageLevelWarning(),expectedWarning);
		
	}
	
	@Test(priority=4)
	public void verifyLoggingIntoApplicationUsingValidEmailAndInvalidPassword() {
		
		loginPage.enterEmail(prop.getProperty("existingEmail"));
		loginPage.enterPassword(prop.getProperty("mismatchingPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getPageLevelWarning(),expectedWarning);
		
	}
	
	@Test(priority=5)
	public void verifyLoggingIntoApplicationWithoutProvidingAnyCredentials() {
		
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getPageLevelWarning(),expectedWarning);
		
	}
	
	@Test(priority=6)
	public void verifyForgottenPasswordOptionIsAvailable() {
		
		forgottenPasswordPage = loginPage.clickOnForgottenPasswordLink();
		Assert.assertTrue(forgottenPasswordPage.didWeNavigateToForgottenPasswordPage());
		
	}
	
	@Test(priority=7)
	public void verifyLoggingIntoApplicationUsingKeyboardKeys() {
		
		actions = clickKeyboradKeyMultipleTimes(getActions(driver),Keys.TAB,23);
		actions = typeTextUsingActions(actions,prop.getProperty("existingEmail"));
		actions = clickKeyboradKeyMultipleTimes(actions,Keys.TAB,1);
		actions = typeTextUsingActions(actions,prop.getProperty("validPassword"));
		actions = clickKeyboradKeyMultipleTimes(actions,Keys.TAB,2);
		actions = clickKeyboradKeyMultipleTimes(actions,Keys.ENTER,1);
		myAccountPage = new MyAccountPage(driver);
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());
		
	}
	
	@Test(priority=8)
	public void verifyPlaceHoldersOfFieldsInLoginPage() {
		
		Assert.assertEquals(loginPage.getEmailFieldPlaceholderText(),"E-Mail Address");
		Assert.assertEquals(loginPage.getPasswordFieldPlaceholderText(),"Password");
		
	}
	
	@Test(priority=9)
	public void verifyBrowsingBackAfterLogin() {
		
		myAccountPage = loginPage.loginInToApplication(prop.getProperty("existingEmail"),prop.getProperty("validPassword"));
		navigateBackInBrowser(myAccountPage.getDriver());
		refreshPage(myAccountPage.getDriver());
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());
		
	}
	
	@Test(priority=10)
	public void verifyBrowsingBackAfterLogout() {
		
		myAccountPage = loginPage.loginInToApplication(prop.getProperty("existingEmail"),prop.getProperty("validPassword"));
		headerOptions = myAccountPage.getHeaderOptions();
		logoutPage = headerOptions.selectLogoutOption();
		navigateBackInBrowser(logoutPage.getDriver());
		refreshPage(logoutPage.getDriver());
		loginPage = logoutPage.getLoginPage();
		Assert.assertTrue(loginPage.didWeNavigateToLogin());
		
	}

}
