package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class RegisterPage extends RootPage {

	WebDriver driver;

	public RegisterPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "input-firstname")
	private WebElement firstNameField;

	@FindBy(id = "input-lastname")
	private WebElement lastNameField;

	@FindBy(id = "input-email")
	private WebElement emailField;

	@FindBy(id = "input-telephone")
	private WebElement telephoneField;

	@FindBy(id = "input-password")
	private WebElement passwordField;

	@FindBy(id = "input-confirm")
	private WebElement passwordConfirmField;

	@FindBy(name = "agree")
	private WebElement privacyPolicyField;

	@FindBy(xpath = "//input[@name='newsletter'][@value='1']")
	private WebElement yesNewsletterOption;

	@FindBy(xpath = "//input[@name='newsletter'][@value='0']")
	private WebElement noNewsletterOption;

	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement continueButton;

	@FindBy(xpath = "//input[@id='input-firstname']/following-sibling::div")
	private WebElement firstNameWarning;

	@FindBy(xpath = "//input[@id='input-lastname']/following-sibling::div")
	private WebElement lastNameWarning;

	@FindBy(xpath = "//input[@id='input-email']/following-sibling::div")
	private WebElement emailWarning;

	@FindBy(xpath = "//input[@id='input-telephone']/following-sibling::div")
	private WebElement telephoneWarning;

	@FindBy(xpath = "//input[@id='input-password']/following-sibling::div")
	private WebElement passwordWarning;

	@FindBy(xpath = "//input[@id='input-confirm']/following-sibling::div")
	private WebElement passwordConfirmationWarning;

	@FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
	private WebElement privacyPolicyFieldWaring;

	@FindBy(xpath = "//ul[@class='breadcrumb']//a[text()='Register']")
	private WebElement registerPageBreadcrumb;

	public boolean didWeNavigateToRegisterPage() {
		return registerPageBreadcrumb.isDisplayed();
	}
	
	public String getPasswordConfirmationFieldWarning() {
		return passwordConfirmationWarning.getText();
	}

	public String getPrivacyPolicyFieldWarning() {
		return privacyPolicyFieldWaring.getText();
	}

	public String getPasswordWarning() {
		return passwordWarning.getText();
	}

	public String getEmailWarning() {
		return emailWarning.getText();
	}

	public String getTelephoneWarning() {
		return telephoneWarning.getText();
	}

	public String getLastNameWarning() {
		return lastNameWarning.getText();
	}

	public String getFirstNameWarning() {
		return firstNameWarning.getText();
	}

	public void selectYesNewsletterOption() {
		yesNewsletterOption.click();
	}

	public void selectNoNewletterOption() {
		noNewsletterOption.click();
	}

	public AccountSuccessPage clickOnContinueButton() {
		continueButton.click();
		return new AccountSuccessPage(driver);
	}

	public void selectPrivacyPolicyField() {
		privacyPolicyField.click();
	}

	public void enterConfirmationPassword(String passwordText) {
		passwordConfirmField.sendKeys(passwordText);
	}

	public void enterFirstName(String firstNameText) {
		firstNameField.sendKeys(firstNameText);
	}

	public void enterLastNameField(String lastNameText) {
		lastNameField.sendKeys(lastNameText);
	}

	public void enterEmail(String emailText) {
		emailField.sendKeys(emailText);
	}

	public void enterTelephone(String telephoneText) {
		telephoneField.sendKeys(telephoneText);
	}

	public void enterPassword(String passwordText) {
		passwordField.sendKeys(passwordText);
	}

}
