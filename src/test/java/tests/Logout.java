package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.Base;
import pages.HeaderOptions;
import utils.CommonUtilities;

public class Logout extends Base {

	public WebDriver driver;

	@BeforeMethod
	public void setup() {

		driver = openBrowserAndApplicationPageURL();
		headerOptions = new HeaderOptions(driver);

	}

	@Test(priority = 1)
	public void verifyLoggingOutUsingMyAccountLogoutOption() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		headerOptions = accountSuccessPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		accountLogoutPage = headerOptions.selectLogoutOption();
		Assert.assertTrue(accountLogoutPage.didWeNavigateToAccountLogoutPage());
		headerOptions = accountLogoutPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		Assert.assertTrue(headerOptions.isLoginOptionUnderMyAccountDropMenuDisplayed());
		accountLogoutPage = headerOptions.getAccountLogoutPage();
		homePage = accountLogoutPage.clickOnContinueButton();
		Assert.assertEquals(getPageTitle(accountLogoutPage.getDriver()), "Your Store");
	}

	@Test(priority = 2)
	public void verifyLoggingOutUsingRightColumnLogoutOption() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		rightColumnOptions = accountSuccessPage.getRightColumnOptions();
		accountLogoutPage = rightColumnOptions.clickOnLogoutOption();
		Assert.assertTrue(accountLogoutPage.didWeNavigateToAccountLogoutPage());
		headerOptions = accountLogoutPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		Assert.assertTrue(headerOptions.isLoginOptionUnderMyAccountDropMenuDisplayed());
		accountLogoutPage = headerOptions.getAccountLogoutPage();
		homePage = accountLogoutPage.clickOnContinueButton();
		Assert.assertEquals(getPageTitle(accountLogoutPage.getDriver()), "Your Store");

	}

	@Test(priority = 3)
	public void verifyLoggingOutAndBrowsingBack() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		headerOptions = accountSuccessPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		accountLogoutPage = headerOptions.selectLogoutOption();
		navigateBackInBrowser(driver);
		refreshPage(driver);
		headerOptions = accountLogoutPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		Assert.assertTrue(headerOptions.isLoginOptionUnderMyAccountDropMenuDisplayed());
	}

	@Test(priority = 4)
	public void verifyNoLogoutOptionBeforeLoginInMyAccountDropMenu() {

		headerOptions.clickOnMyAccountDropMenu();
		Assert.assertFalse(headerOptions.isLogoutOptionUnderMyAccountDropMenuDisplayed());

	}

	@Test(priority = 5)
	public void verifyNoLogoutOptionBeforeLoginInRightColumnOptions() {

		headerOptions.clickOnMyAccountDropMenu();
		registerPage = headerOptions.selectRegisterOption();
		rightColumnOptions = registerPage.getRightColumnOptions();
		Assert.assertFalse(rightColumnOptions.isLogoutOptionDisplayed());

	}

	@Test(priority = 6)
	public void verifyLoginImmediatleyAfterLogout() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		headerOptions = accountSuccessPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		accountLogoutPage = headerOptions.selectLogoutOption();
		headerOptions = accountLogoutPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		loginPage = headerOptions.navigateToLoginPage();
		myAccountPage = loginPage.loginInToApplication(emailAddress, prop.getProperty("validPassword"));
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());

	}

	@Test(priority = 7)
	public void verifyBreadcrumbTitleURLAndHeadingOfAccountLogoutPage() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		headerOptions = accountSuccessPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		accountLogoutPage = headerOptions.selectLogoutOption();
		Assert.assertEquals(getPageTitle(accountLogoutPage.getDriver()), "Account Logout");
		Assert.assertEquals(getPageURL(accountLogoutPage.getDriver()),
				getBaseURL() + prop.getProperty("logoutPageURL"));
		Assert.assertTrue(accountLogoutPage.didWeNavigateToAccountLogoutPage());
		Assert.assertEquals(accountLogoutPage.getPageHeading(), "Account Logout");

	}

	@Test(priority = 8)
	public void verifyLogoutUI() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		headerOptions = accountSuccessPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();

		if (browserName.equalsIgnoreCase("chrome")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualLogoutOptions.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualLogoutOptions.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedLogoutOptions.png"));
		} else if (browserName.equalsIgnoreCase("firefox")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualFirefoxLogoutOptions.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualFirefoxLogoutOptions.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedFirefoxLogoutOptions.png"));
		} else if (browserName.equalsIgnoreCase("edge")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualEdgeLogoutOptions.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualEdgeLogoutOptions.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedEdgeLogoutOptions.png"));
		}

		accountLogoutPage = headerOptions.selectLogoutOption();

		if (browserName.equalsIgnoreCase("chrome")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualApplicationLogoutPage.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualApplicationLogoutPage.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedApplicationLogoutPage.png"));
		} else if (browserName.equalsIgnoreCase("firefox")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualFirefoxApplicationLogoutPage.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualFirefoxApplicationLogoutPage.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedFirefoxApplicationLogoutPage.png"));
		} else if (browserName.equalsIgnoreCase("edge")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualEdgeApplicationLogoutPage.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualEdgeApplicationLogoutPage.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedEdgeApplicationLogoutPage.png"));
		}

	}

	@Test(priority = 9)
	public void verifyLogoutInAllSupportedEnvironments() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		headerOptions = accountSuccessPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		accountLogoutPage = headerOptions.selectLogoutOption();
		Assert.assertTrue(accountLogoutPage.didWeNavigateToAccountLogoutPage());
		headerOptions = accountLogoutPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		Assert.assertTrue(headerOptions.isLoginOptionUnderMyAccountDropMenuDisplayed());
		accountLogoutPage = headerOptions.getAccountLogoutPage();
		homePage = accountLogoutPage.clickOnContinueButton();
		Assert.assertEquals(getPageTitle(accountLogoutPage.getDriver()), "Your Store");
	}

}
