package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class MyAccountInformationPage extends RootPage {

	WebDriver driver;

	public MyAccountInformationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="input-firstname")
	private WebElement firstNameField;
	
	@FindBy(id="input-lastname")
	private WebElement lastNameField;
	
	@FindBy(id="input-email")
	private WebElement emailField;
	
	@FindBy(id="input-telephone")
	private WebElement telephoneField;
	
	public String getFirstNameDomAttribute(String attributeName) {
		return firstNameField.getDomAttribute(attributeName);
	}
	
	public String getLastNameDomAttribute(String attributeName) {
		return lastNameField.getDomAttribute(attributeName);
	}
	
	public String getEmailDomAttribute(String attributeName) {
		return emailField.getDomAttribute(attributeName);
	}
	
	public String getEmailDomProperty(String propertyName) {
		return emailField.getDomProperty(propertyName);
	}
	
	public String getTelephoneDomAttribute(String attributeName) {
		return telephoneField.getDomAttribute(attributeName);
	}

}
