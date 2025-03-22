package tests;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.Base;
import pages.HeaderOptions;
import pages.MyAccountPage;
import utils.CommonUtilities;

public class Login extends Base {

	public WebDriver driver;

	@BeforeMethod
	public void setup() {

		driver = openBrowserAndApplicationPageURL();
		headerOptions = new HeaderOptions(driver);
		loginPage = headerOptions.navigateToLoginPage();

	}

	@Test(priority = 1)
	public void verifyLoggingIntoApplicationUsingValidCredentails() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		rightColumnOptions = accountSuccessPage.getRightColumnOptions();
		rightColumnOptions.clickOnLogoutOption();
		loginPage = headerOptions.navigateToLoginPage();
		Assert.assertTrue(loginPage.didWeNavigateToLogin());
		loginPage.enterEmail(emailAddress);
		loginPage.enterPassword(prop.getProperty("validPassword"));
		myAccountPage = loginPage.clickOnLoginButton();
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());
	}

	@Test(priority = 2)
	public void verifyLoggingIntoApplicationUsingInvalidCredentials() {

		loginPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		loginPage.enterPassword(prop.getProperty("mismatchingPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getPageLevelWarning(), expectedWarning);

	}

	@Test(priority = 3)
	public void verifyLoggingIntoApplicationUsingInvalidEmailAndValidPassword() {

		loginPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		loginPage.enterPassword(prop.getProperty("validPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getPageLevelWarning(), expectedWarning);

	}

	@Test(priority = 4)
	public void verifyLoggingIntoApplicationUsingValidEmailAndInvalidPassword() {

		loginPage.enterEmail(prop.getProperty("existingEmail"));
		loginPage.enterPassword(prop.getProperty("mismatchingPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getPageLevelWarning(), expectedWarning);

	}

	@Test(priority = 5)
	public void verifyLoggingIntoApplicationWithoutProvidingAnyCredentials() {

		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getPageLevelWarning(), expectedWarning);

	}

	@Test(priority = 6)
	public void verifyForgottenPasswordOptionIsAvailable() {

		forgottenPasswordPage = loginPage.clickOnForgottenPasswordLink();
		Assert.assertTrue(forgottenPasswordPage.didWeNavigateToForgottenPasswordPage());

	}

	@Test(priority = 7)
	public void verifyLoggingIntoApplicationUsingKeyboardKeys() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		rightColumnOptions = accountSuccessPage.getRightColumnOptions();
		rightColumnOptions.clickOnLogoutOption();
		loginPage = headerOptions.navigateToLoginPage();
		actions = clickKeyboradKeyMultipleTimes(getActions(driver), Keys.TAB, 23);
		actions = typeTextUsingActions(actions, emailAddress);
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.TAB, 1);
		actions = typeTextUsingActions(actions, prop.getProperty("validPassword"));
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.TAB, 2);
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.ENTER, 1);
		myAccountPage = new MyAccountPage(driver);
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());

	}

	@Test(priority = 8)
	public void verifyPlaceHoldersOfFieldsInLoginPage() {

		Assert.assertEquals(loginPage.getEmailFieldPlaceholderText(), "E-Mail Address");
		Assert.assertEquals(loginPage.getPasswordFieldPlaceholderText(), "Password");

	}

	@Test(priority = 9)
	public void verifyBrowsingBackAfterLogin() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		rightColumnOptions = accountSuccessPage.getRightColumnOptions();
		rightColumnOptions.clickOnLogoutOption();
		loginPage = headerOptions.navigateToLoginPage();
		myAccountPage = loginPage.loginInToApplication(emailAddress, prop.getProperty("validPassword"));
		navigateBackInBrowser(myAccountPage.getDriver());
		refreshPage(myAccountPage.getDriver());
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());

	}

	@Test(priority = 10)
	public void verifyBrowsingBackAfterLogout() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		rightColumnOptions = accountSuccessPage.getRightColumnOptions();
		rightColumnOptions.clickOnLogoutOption();
		loginPage = headerOptions.navigateToLoginPage();
		myAccountPage = loginPage.loginInToApplication(emailAddress, prop.getProperty("validPassword"));
		headerOptions = myAccountPage.getHeaderOptions();
		accountLogoutPage = headerOptions.selectLogoutOption();
		navigateBackInBrowser(accountLogoutPage.getDriver());
		refreshPage(accountLogoutPage.getDriver());
		loginPage = accountLogoutPage.getLoginPage();
		Assert.assertTrue(loginPage.didWeNavigateToLogin());

	}

	@Test(priority = 11)
	public void verifyLoggingIntoApplicationUsingInactiveCredentials() {

		loginPage.loginInToApplication(prop.getProperty("inactiveEmail"), prop.getProperty("validPassword"));
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getPageLevelWarning(), expectedWarning);

	}

	@Test(priority = 12)
	public void verifyNumberOfUnsuccessfulLoginAttemps() {

		String invalidEmail = CommonUtilities.generateBrandNewEmail();
		loginPage.loginInToApplication(invalidEmail, prop.getProperty("mismatchingPassword"));
		loginPage.loginInToApplication(invalidEmail, prop.getProperty("mismatchingPassword"));
		loginPage.loginInToApplication(invalidEmail, prop.getProperty("mismatchingPassword"));
		loginPage.loginInToApplication(invalidEmail, prop.getProperty("mismatchingPassword"));
		loginPage.loginInToApplication(invalidEmail, prop.getProperty("mismatchingPassword"));
		loginPage.loginInToApplication(invalidEmail, prop.getProperty("mismatchingPassword"));
		String expectedWarning = "Warning: Your account has exceeded allowed number of login attempts. Please try again in 1 hour.";
		Assert.assertEquals(loginPage.getPageLevelWarning(), expectedWarning);

	}

	@Test(priority = 13)
	public void verifyLoginPasswordFieldForVisibility() {

		Assert.assertEquals(loginPage.getPasswordFieldDomAttribute("type"), "password");

	}

	@Test(priority = 14)
	public void verifyCopyingOfTextEnteredIntoPasswordField() throws InterruptedException {

		loginPage.enterPassword(prop.getProperty("validPassword"));
		loginPage.copyPasswordFromPasswordField();
		loginPage.pasteCopiedTextIntoEmailField();
		loginPage.clickOnLoginButton();
		Assert.assertNotEquals(loginPage.getPastedTextFromEmailField(), prop.getProperty("validPassword"));

	}

	@Test(priority = 15)
	public void verifyPasswordIsNotVisibleInPageSource() {

		loginPage.enterPassword(prop.getProperty("randomPassword"));
		Assert.assertFalse(getPageSourceCode(loginPage.getDriver()).contains(prop.getProperty("randomPassword")));
		loginPage.clickOnLoginButton();
		Assert.assertFalse(getPageSourceCode(loginPage.getDriver()).contains(prop.getProperty("randomPassword")));

	}

	@Test(priority = 16)
	public void verifyLoggingIntoApplicationUsingChangedPassword() {

		myAccountPage = loginPage.loginInToApplication(prop.getProperty("existingEmailTwo"),
				prop.getProperty("validPasswordTwo"));
		changePasswordPage = myAccountPage.clickOnChangeYourPasswordOption();
		changePasswordPage.enterNewPasswordIntoPasswordField(prop.getProperty("validPasswordThree"));
		changePasswordPage.enterNewPasswordIntoPasswordConfirmField(prop.getProperty("validPasswordThree"));
		myAccountPage = changePasswordPage.clickOnContinueButton();
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());
		String expectedMessage = "Success: Your password has been successfully updated.";
		Assert.assertEquals(myAccountPage.getPageLevelSuccessMessage(), expectedMessage);
		rightColumnOptions = myAccountPage.getRightColumnOptions();
		accountLogoutPage = rightColumnOptions.clickOnLogoutOption();
		homePage = accountLogoutPage.clickOnContinueButton();
		headerOptions = homePage.getHeaderOptions();
		loginPage = headerOptions.navigateToLoginPage();
		loginPage.loginInToApplication(prop.getProperty("existingEmailTwo"), prop.getProperty("validPasswordTwo"));
		expectedMessage = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getPageLevelWarning(), expectedMessage);
		myAccountPage = loginPage.loginInToApplication(prop.getProperty("existingEmailTwo"),
				prop.getProperty("validPasswordThree"));
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());
		swapPasswords(prop);

	}

	@Test(priority = 17)
	public void verifyLoginPageNavigations() {

		headerOptions = loginPage.getHeaderOptions();
		contactUsPage = headerOptions.selectPhoneIconOption();
		Assert.assertTrue(getPageTitle(contactUsPage.getDriver()).equals("Contact Us"));
		navigateBackInBrowser(contactUsPage.getDriver());

		loginPage = headerOptions.selectHeartIconOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = headerOptions.selectWishListOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		shoppingCartPage = headerOptions.selectShoppingCartIcon();
		Assert.assertEquals(getPageTitle(shoppingCartPage.getDriver()), "Shopping Cart");
		navigateBackInBrowser(shoppingCartPage.getDriver());

		shoppingCartPage = headerOptions.selectShoppingCartOption();
		Assert.assertEquals(getPageTitle(shoppingCartPage.getDriver()), "Shopping Cart");
		navigateBackInBrowser(shoppingCartPage.getDriver());

		shoppingCartPage = headerOptions.selectCheckoutIcon();
		Assert.assertEquals(getPageTitle(shoppingCartPage.getDriver()), "Shopping Cart");
		navigateBackInBrowser(shoppingCartPage.getDriver());

		shoppingCartPage = headerOptions.selectCheckoutOption();
		Assert.assertEquals(getPageTitle(shoppingCartPage.getDriver()), "Shopping Cart");
		navigateBackInBrowser(shoppingCartPage.getDriver());

		homePage = headerOptions.selectLogo();
		Assert.assertEquals(getPageTitle(homePage.getDriver()), "Your Store");
		navigateBackInBrowser(homePage.getDriver());

		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertEquals(getPageTitle(searchPage.getDriver()), "Search");
		navigateBackInBrowser(searchPage.getDriver());

		homePage = headerOptions.selectHomeBreadcrumbOption();
		Assert.assertEquals(getPageTitle(homePage.getDriver()), "Your Store");
		navigateBackInBrowser(homePage.getDriver());

		loginPage = headerOptions.selectAccountBreadcrumbOptionWithoutLogin();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = loginPage.selectLoginBreadcrumbOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		registerPage = loginPage.clickOnContinueButton();
		Assert.assertEquals(getPageTitle(registerPage.getDriver()), "Register Account");
		navigateBackInBrowser(registerPage.getDriver());

		forgottenPasswordPage = loginPage.clickOnForgottenPasswordLink();
		Assert.assertTrue(forgottenPasswordPage.didWeNavigateToForgottenPasswordPage());
		navigateBackInBrowser(forgottenPasswordPage.getDriver());

		loginPage.clickOnLoginButton();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		rightColumnOptions = loginPage.getRightColumnOptions();
		loginPage = rightColumnOptions.clickOnLoginOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		registerPage = rightColumnOptions.clickOnRegisterOption();
		Assert.assertEquals(getPageTitle(registerPage.getDriver()), "Register Account");
		navigateBackInBrowser(registerPage.getDriver());

		forgottenPasswordPage = rightColumnOptions.clickOnForgottenPasswordOption();
		Assert.assertEquals(getPageTitle(forgottenPasswordPage.getDriver()), "Forgot Your Password?");
		navigateBackInBrowser(forgottenPasswordPage.getDriver());

		loginPage = rightColumnOptions.clickOnMyAccountOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = rightColumnOptions.clickOnAddressBookOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = rightColumnOptions.clickOnWishListOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = rightColumnOptions.clickOnOrderHistoryOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = rightColumnOptions.clickOnDownloadsOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = rightColumnOptions.clickOnRecurringPaymentsOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = rightColumnOptions.clickOnRewarPointsOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = rightColumnOptions.clickOnReturnsOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = rightColumnOptions.clickOnTransactionsOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = rightColumnOptions.clickOnNewsletterOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		footerOptions = loginPage.getFoooterOptions();
		aboutUsPage = footerOptions.selectAboutUsOption();
		Assert.assertEquals(getPageTitle(aboutUsPage.getDriver()), "About Us");
		navigateBackInBrowser(aboutUsPage.getDriver());

		deliveryInformationPage = footerOptions.selectDeliveryInformationOption();
		Assert.assertEquals(getPageTitle(deliveryInformationPage.getDriver()), "Delivery Information");
		navigateBackInBrowser(deliveryInformationPage.getDriver());

		privacyPolicyPage = footerOptions.selectPrivacyPolicyOption();
		Assert.assertEquals(getPageTitle(privacyPolicyPage.getDriver()), "Privacy Policy");
		navigateBackInBrowser(privacyPolicyPage.getDriver());

		termsAndConditionsPage = footerOptions.selectTermsAndConditionsOption();
		Assert.assertEquals(getPageTitle(termsAndConditionsPage.getDriver()), "Terms & Conditions");
		navigateBackInBrowser(termsAndConditionsPage.getDriver());

		contactUsPage = footerOptions.selectContactUsOption();
		Assert.assertEquals(getPageTitle(contactUsPage.getDriver()), "Contact Us");
		navigateBackInBrowser(contactUsPage.getDriver());

		productReturnsPage = footerOptions.selectReturnsOption();
		Assert.assertEquals(getPageTitle(productReturnsPage.getDriver()), "Product Returns");
		navigateBackInBrowser(productReturnsPage.getDriver());

		siteMapPage = footerOptions.selectSiteMapOption();
		Assert.assertEquals(getPageTitle(siteMapPage.getDriver()), "Site Map");
		navigateBackInBrowser(siteMapPage.getDriver());

		brandPage = footerOptions.selectBrandsOption();
		Assert.assertEquals(getPageTitle(brandPage.getDriver()), "Find Your Favorite Brand");
		navigateBackInBrowser(brandPage.getDriver());

		giftCertificatePage = footerOptions.selectGiftCertificatesOption();
		Assert.assertEquals(getPageTitle(giftCertificatePage.getDriver()), "Purchase a Gift Certificate");
		navigateBackInBrowser(giftCertificatePage.getDriver());

		loginPage = footerOptions.selectAffiliateOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Affiliate Program");
		navigateBackInBrowser(loginPage.getDriver());

		specialOffersPage = footerOptions.selectSpecialsOption();
		Assert.assertEquals(getPageTitle(specialOffersPage.getDriver()), "Special Offers");
		navigateBackInBrowser(specialOffersPage.getDriver());

		loginPage = footerOptions.selectMyAccountOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = footerOptions.selectOrderHistoryOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = footerOptions.selectWishListOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

		loginPage = footerOptions.selectNewsletterOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");

	}

	@Test(priority = 18)
	public void verifyDifferrentWaysOfNavigatingToLoginPage() {

		Assert.assertTrue(loginPage.didWeNavigateToLogin());
		rightColumnOptions = loginPage.getRightColumnOptions();
		rightColumnOptions.clickOnLoginOption();
		Assert.assertTrue(loginPage.didWeNavigateToLogin());
		registerPage = loginPage.clickOnContinueButton();
		loginPage = registerPage.selectLoginPageOption();
		Assert.assertTrue(loginPage.didWeNavigateToLogin());

	}

	@Test(priority = 19)
	public void verifyLoginPageBreadcrumbURLTitleHeading() {

		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		Assert.assertEquals(getPageURL(loginPage.getDriver()), getBaseURL() + prop.getProperty("loginPageURL"));
		Assert.assertTrue(loginPage.didWeNavigateToLogin());
		Assert.assertEquals(loginPage.getFirstHeading(), "New Customer");
		Assert.assertEquals(loginPage.getSecondHeading(), "Returning Customer");

	}

	@Test(priority = 20)
	public void verifyLoginPageUI() throws IOException {

		if (browserName.equalsIgnoreCase("chrome")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualLoginPageUI.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualLoginPageUI.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedLoginPageUI.png"));
		} else if (browserName.equalsIgnoreCase("firefox")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualFirefoxLogoutOptions.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualFirefoxLogoutOptions.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedFirefoxLogoutOptions.png"));
		} else if (browserName.equalsIgnoreCase("edge")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualEdgeLoginPageUI.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualEdgeLoginPageUI.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedEdgeLoginPageUI.png"));
		}

	}

	@Test(priority = 21)
	public void verifyLoginInAllEnvironments() {
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		registerPage = headerOptions.navigateToRegisterPage();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),
				prop.getProperty("lastName"), emailAddress, prop.getProperty("telephoneNumber"),
				prop.getProperty("validPassword"));
		rightColumnOptions = accountSuccessPage.getRightColumnOptions();
		rightColumnOptions.clickOnLogoutOption();
		loginPage = headerOptions.navigateToLoginPage();
		Assert.assertTrue(loginPage.didWeNavigateToLogin());
		loginPage.enterEmail(emailAddress);
		loginPage.enterPassword(prop.getProperty("validPassword"));
		myAccountPage = loginPage.clickOnLoginButton();
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());
	}

}
