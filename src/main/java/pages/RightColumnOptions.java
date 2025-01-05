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
		newsletterOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnTransactionsOption() {
		transactionsOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnReturnsOption() {
		returnsOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnRewarPointsOption() {
		rewardPointsOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnRecurringPaymentsOption() {
		recurringPaymentsOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnDownloadsOption() {
		downloadsOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnOrderHistoryOption() {
		orderHistoryOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnWishListOption() {
		wishListOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnAddressBookOption() {
		addressBookOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage clickOnMyAccountOption() {
		myAccountOption.click();
		return new LoginPage(driver);
	}
	
	public ForgottenPasswordPage clickOnForgottenPasswordOption() {
		forgottendPasswordOption.click();
		return new ForgottenPasswordPage(driver);
	}
	
	public LoginPage clickOnLoginOption() {
		loginOption.click();
		return new LoginPage(driver);
	}
	
	public boolean didWeGetLoggedIn() {
		return logoutOption.isDisplayed();
	}
	
	public RegisterPage clickOnRegisterOption() {
		registerOption.click();
		return new RegisterPage(driver);
	}

}
