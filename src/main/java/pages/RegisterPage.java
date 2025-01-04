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
	private WebElement pageLevelWaring;

	@FindBy(xpath = "//ul[@class='breadcrumb']//a[text()='Register']")
	private WebElement registerPageBreadcrumb;
	
	@FindBy(css="label[for='input-firstname']")
	private WebElement firstNameFieldLabel;
	
	@FindBy(css="label[for='input-lastname']")
	private WebElement lastNameFieldLabel;
	
	@FindBy(css="label[for='input-email']")
	private WebElement emailFieldLabel;
	
	@FindBy(css="label[for='input-telephone']")
	private WebElement telephoneFieldLabel;
	
	@FindBy(css="label[for='input-password']")
	private WebElement passwordFieldLabel;
	
	@FindBy(css="label[for='input-confirm']")
	private WebElement passwordConfirmFieldLabel;
	
	@FindBy(css="div[class='pull-right']")
	private WebElement privacyPolicyFieldLabel;

	public String getPasswordFieldDomAttribute(String attributeName) {
		return passwordField.getDomAttribute(attributeName);
	}
	
	public String getPasswordConfirmFieldDomAttribute(String attributeName) {
		return passwordConfirmField.getDomAttribute(attributeName);
	}
	
	public boolean isPrivacyPolicyFieldSelected() {
		return privacyPolicyField.isSelected();
	}
	
	public String getFirstNameCSSValue(String propertyName) {
		return firstNameField.getCssValue(propertyName);
	}
	
	public String getLastNameCSSValue(String propertyName) {
		return lastNameField.getCssValue(propertyName);
	}
	
	public String getEmailCSSValue(String propertyName) {
		return emailField.getCssValue(propertyName);
	}
	
	public String getTelephoneCSSValue(String propertyName) {
		return telephoneField.getCssValue(propertyName);
	}
	
	public String getPasswordCSSValue(String propertyName) {
		return passwordField.getCssValue(propertyName);
	}
	
	public String getPasswordConfirmCSSValue(String propertyName) {
		return passwordConfirmField.getCssValue(propertyName);
	}
	
	public String getContinueButtonCSSValue(String propertyName) {
		return continueButton.getCssValue(propertyName);
	}
	
	public void clearPasswordField() {
		passwordField.clear();
	}
	
	public void clearTelephoneField() {
		telephoneField.clear();
	}
	
	public void clearFirstNameField() {
		firstNameField.clear();
	}
	
	public void clearLastNameField() {
		lastNameField.clear();
	}
	
	public boolean isFirstNameWarningMessageDisplayed() {
		return firstNameWarning.isDisplayed();
	}
	
	public boolean isLastNameWarningMessageDisplayed() {
		return lastNameWarning.isDisplayed();
	}
	
	public boolean isEmailWarningMessageDisplayed() {
		return emailWarning.isDisplayed();
	}
	
	public boolean isTelephoneWarningMessageDisplayed() {
		return telephoneWarning.isDisplayed();
	}
	
	public boolean isPasswordWarningMessageDisplayed() {
		return passwordWarning.isDisplayed();
	}
	
	public WebElement getPrivacyPolicyFieldLabelElement() {
		return privacyPolicyFieldLabel;
	}
	
	public WebElement getPasswordConfirmFieldLabelElement() {
		return passwordConfirmFieldLabel;
	}
	
	public WebElement getPasswordFieldLabelElement() {
		return passwordFieldLabel;
	}
	
	public WebElement getTelephoneFieldLabelElement() {
		return telephoneFieldLabel;
	}
	
	public WebElement getEmailFieldLabelElement() {
		return emailFieldLabel;
	}
	
	public WebElement getFirstNameFieldLabelElement() {
		return firstNameFieldLabel;
	}
	
	public WebElement getLastNameFieldLabelElement() {
		return lastNameFieldLabel;
	}
	
	public String getFirstNameFieldPlaceholderText() {
		return firstNameField.getDomAttribute("placeholder");
	}
	
	public String getLastNameFieldPlaceholderText() {
		return lastNameField.getDomAttribute("placeholder");
	}
	
	public String getEmailFieldPlaceholderText() {
		return emailField.getDomAttribute("placeholder");
	}
	
	public String getTelephoneFieldPlaceholderText() {
		return telephoneField.getDomAttribute("placeholder");
	}
	
	public String getPasswordFieldPlaceholderText() {
		return passwordField.getDomAttribute("placeholder");
	}
	
	public String getPasswordConfirmFieldPlaceholderText() {
		return passwordConfirmField.getDomAttribute("placeholder");
	}
	
	public void clearEmailField() {
		emailField.clear();
	}
	
	public String getEmailValidationMessage() {
		return emailField.getDomProperty("validationMessage");
	}

	public boolean didWeNavigateToRegisterPage() {
		return registerPageBreadcrumb.isDisplayed();
	}
	
	public String getPasswordConfirmationWarning() {
		return passwordConfirmationWarning.getText();
	}

	public String getPageLevelWarning() {
		return pageLevelWaring.getText();
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

	public void enterLastName(String lastNameText) {
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
