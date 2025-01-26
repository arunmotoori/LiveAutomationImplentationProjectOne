package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.Base;
import pages.HeaderOptions;

public class Logout extends Base {
	
	WebDriver driver;
	
	@BeforeMethod
	public void setup() {
		
		driver = openBrowserAndApplicationPageURL();
		headerOptions = new HeaderOptions(driver);
		
	}
	
	@AfterMethod
	public void teardown() {
		closeBrowser(driver);
	}
	
	@Test(priority = 1)
	public void verifyLoggingOutUsingMyAccountLogoutOption() {
		
		loginPage = headerOptions.navigateToLoginPage();
		myAccountPage = loginPage.loginInToApplication(prop.getProperty("existingEmail"),prop.getProperty("validPassword"));
		headerOptions = myAccountPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		accountLogoutPage = headerOptions.selectLogoutOption();
		Assert.assertTrue(accountLogoutPage.didWeNavigateToAccountLogoutPage());
		headerOptions = accountLogoutPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		Assert.assertTrue(headerOptions.isLoginOptionUnderMyAccountDropMenuDisplayed());
		accountLogoutPage = headerOptions.getAccountLogoutPage();
		homePage = accountLogoutPage.clickOnContinueButton();
		Assert.assertEquals(getPageTitle(accountLogoutPage.getDriver()),"Your Store");
		
	}
	
	@Test(priority = 2)
	public void verifyLoggingOutUsingRightColumnLogoutOption() {
		
		loginPage = headerOptions.navigateToLoginPage();
		myAccountPage = loginPage.loginInToApplication(prop.getProperty("existingEmail"),prop.getProperty("validPassword"));
		rightColumnOptions = myAccountPage.getRightColumnOptions();
		accountLogoutPage = rightColumnOptions.clickOnLogoutOption();
		Assert.assertTrue(accountLogoutPage.didWeNavigateToAccountLogoutPage());
		headerOptions = accountLogoutPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		Assert.assertTrue(headerOptions.isLoginOptionUnderMyAccountDropMenuDisplayed());
		accountLogoutPage = headerOptions.getAccountLogoutPage();
		homePage = accountLogoutPage.clickOnContinueButton();
		Assert.assertEquals(getPageTitle(accountLogoutPage.getDriver()),"Your Store");
		
	}
	
	

}
