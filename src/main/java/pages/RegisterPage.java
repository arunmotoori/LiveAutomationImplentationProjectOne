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
	
	@FindBy(linkText="login page")
	private WebElement loginPageOption;
	
	public LoginPage selectLoginPageOption() {
		elementUtilities.clickOnElement(loginPageOption);
		return new LoginPage(driver);
	}

	public String getPasswordFieldDomAttribute(String attributeName) {
		return elementUtilities.getElementDomAttribute(passwordField, attributeName);
	}
	
	public String getPasswordConfirmFieldDomAttribute(String attributeName) {
		return elementUtilities.getElementDomAttribute(passwordConfirmField, attributeName);
	}
	
	public boolean isPrivacyPolicyFieldSelected() {
		return elementUtilities.isElementSelected(privacyPolicyField);
	}
	
	public String getFirstNameCSSValue(String propertyName) {
		return elementUtilities.getElementCSSValue(firstNameField, propertyName);
	}
	
	public String getLastNameCSSValue(String propertyName) {
		return elementUtilities.getElementCSSValue(lastNameField, propertyName);
	}
	
	public String getEmailCSSValue(String propertyName) {
		return elementUtilities.getElementCSSValue(emailField, propertyName);
	}
	
	public String getTelephoneCSSValue(String propertyName) {
		return elementUtilities.getElementCSSValue(telephoneField, propertyName);
	}
	
	public String getPasswordCSSValue(String propertyName) {
		return elementUtilities.getElementCSSValue(passwordField, propertyName);
	}
	
	public String getPasswordConfirmCSSValue(String propertyName) {
		return elementUtilities.getElementCSSValue(passwordConfirmField, propertyName);
	}
	
	public String getContinueButtonCSSValue(String propertyName) {
		return elementUtilities.getElementCSSValue(continueButton, propertyName);
	}
	
	public void clearPasswordField() {
		elementUtilities.clearTextFromElement(passwordField);
	}
	
	public void clearTelephoneField() {
		elementUtilities.clearTextFromElement(telephoneField);
	}
	
	public void clearFirstNameField() {
		elementUtilities.clearTextFromElement(firstNameField);
	}
	
	public void clearLastNameField() {
		elementUtilities.clearTextFromElement(lastNameField);
	}
	
	public boolean isFirstNameWarningMessageDisplayed() {
		return elementUtilities.isElementDisplayed(firstNameWarning);
	}
	
	public boolean isLastNameWarningMessageDisplayed() {
		return elementUtilities.isElementDisplayed(lastNameWarning);
	}
	
	public boolean isEmailWarningMessageDisplayed() {
		return elementUtilities.isElementDisplayed(emailWarning);
	}
	
	public boolean isTelephoneWarningMessageDisplayed() {
		return elementUtilities.isElementDisplayed(telephoneWarning);
	}
	
	public boolean isPasswordWarningMessageDisplayed() {
		return elementUtilities.isElementDisplayed(passwordWarning);
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
		return elementUtilities.getElementDomAttribute(firstNameField,"placeholder");
	}
	
	public String getLastNameFieldPlaceholderText() {
		return elementUtilities.getElementDomAttribute(lastNameField, "placeholder");
	}
	
	public String getEmailFieldPlaceholderText() {
		return elementUtilities.getElementDomAttribute(emailField,"placeholder");
	}
	
	public String getTelephoneFieldPlaceholderText() {
		return elementUtilities.getElementDomAttribute(telephoneField,"placeholder");
	}
	
	public String getPasswordFieldPlaceholderText() {
		return elementUtilities.getElementDomAttribute(passwordField,"placeholder");
	}
	
	public String getPasswordConfirmFieldPlaceholderText() {
		return elementUtilities.getElementDomAttribute(passwordConfirmField,"placeholder");
	}
	
	public void clearEmailField() {
		elementUtilities.clearTextFromElement(emailField);
	}
	
	public String getEmailValidationMessage() {
		return elementUtilities.getElementDomProperty(emailField,"validationMessage");
	}
	
	public RegisterPage selectRegisterBreadcrumbOption() {
		elementUtilities.clickOnElement(registerPageBreadcrumb);
		return new RegisterPage(driver);
	}

	public boolean didWeNavigateToRegisterPage() {
		return elementUtilities.isElementDisplayed(registerPageBreadcrumb);
	}
	
	public String getPasswordConfirmationWarning() {
		return elementUtilities.getElementText(passwordConfirmationWarning);
	}

	

	public String getPasswordWarning() {
		return elementUtilities.getElementText(passwordWarning);
	}

	public String getEmailWarning() {
		return elementUtilities.getElementText(emailWarning);
	}

	public String getTelephoneWarning() {
		return elementUtilities.getElementText(telephoneWarning);
	}

	public String getLastNameWarning() {
		return elementUtilities.getElementText(lastNameWarning);
	}

	public String getFirstNameWarning() {
		return elementUtilities.getElementText(firstNameWarning);
	}

	public void selectYesNewsletterOption() {
		elementUtilities.clickOnElement(yesNewsletterOption);
	}

	public void selectNoNewletterOption() {
		elementUtilities.clickOnElement(noNewsletterOption);
	}

	public AccountSuccessPage clickOnContinueButton() {
		elementUtilities.clickOnElement(continueButton);
		return new AccountSuccessPage(driver);
	}

	public void selectPrivacyPolicyField() {
		elementUtilities.clickOnElement(privacyPolicyField);
	}

	public void enterConfirmationPassword(String passwordText) {
		elementUtilities.enterTextIntoElement(passwordConfirmField, passwordText);
	}

	public void enterFirstName(String firstNameText) {
		elementUtilities.enterTextIntoElement(firstNameField, firstNameText);
	}

	public void enterLastName(String lastNameText) {
		elementUtilities.enterTextIntoElement(lastNameField, lastNameText);
	}

	public void enterEmail(String emailText) {
		elementUtilities.enterTextIntoElement(emailField, emailText);
	}

	public void enterTelephone(String telephoneText) {
		elementUtilities.enterTextIntoElement(telephoneField, telephoneText);
	}

	public void enterPassword(String passwordText) {
		elementUtilities.enterTextIntoElement(passwordField, passwordText);
	}

}
