package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class RightColumnOptions extends RootPage {

	WebDriver driver;

	public RightColumnOptions(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//aside[@id='column-right']//a[text()='Register']")
	private WebElement registerOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Logout']")
	private WebElement logoutOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Login']")
	private WebElement loginOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Forgotten Password']")
	private WebElement forgottendPasswordOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='My Account']")
	private WebElement myAccountOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Address Book']")
	private WebElement addressBookOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Wish List']")
	private WebElement wishListOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Order History']")
	private WebElement orderHistoryOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Downloads']")
	private WebElement downloadsOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Recurring payments']")
	private WebElement recurringPaymentsOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Reward Points']")
	private WebElement rewardPointsOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Returns']")
	private WebElement returnsOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Transactions']")
	private WebElement transactionsOption;
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Newsletter']")
	private WebElement newsletterOption;
	
	public LoginPage clickOnNewsletterOption() {
		elementUtilities.clickOnElement(newsletterOption);
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnTransactionsOption() {
		elementUtilities.clickOnElement(transactionsOption);
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnReturnsOption() {
		elementUtilities.clickOnElement(returnsOption);
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnRewarPointsOption() {
		elementUtilities.clickOnElement(rewardPointsOption);
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnRecurringPaymentsOption() {
		elementUtilities.clickOnElement(recurringPaymentsOption);
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnDownloadsOption() {
		elementUtilities.clickOnElement(downloadsOption);
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnOrderHistoryOption() {
		elementUtilities.clickOnElement(orderHistoryOption);
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnWishListOption() {
		elementUtilities.clickOnElement(wishListOption);
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnAddressBookOption() {
		elementUtilities.clickOnElement(addressBookOption);
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnMyAccountOption() {
		elementUtilities.clickOnElement(myAccountOption);
		return new LoginPage(driver);
	}
	
	public ForgottenPasswordPage clickOnForgottenPasswordOption() {
		elementUtilities.clickOnElement(forgottendPasswordOption);
		return new ForgottenPasswordPage(driver);
	}
	
	public LoginPage clickOnLoginOption() {
		elementUtilities.clickOnElement(loginOption);
		return new LoginPage(driver);
	}
	
	public boolean didWeGetLoggedIn() {
		return elementUtilities.isElementDisplayed(logoutOption);
	}
	
	public RegisterPage clickOnRegisterOption() {
		elementUtilities.clickOnElement(registerOption);
		return new RegisterPage(driver);
	}

}
