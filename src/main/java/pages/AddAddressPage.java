package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class AddAddressPage extends RootPage {
	
	WebDriver driver;
	
	public AddAddressPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(id="input-firstname")
	private WebElement firstNameField;
	
	@FindBy(id="input-lastname")
	private WebElement lastNameField;
	
	@FindBy(id="input-company")
	private WebElement companyField;
	
	@FindBy(id = "input-address-1")
	private WebElement addressOneField;
	
	@FindBy(id = "input-city")
	private WebElement cityField;
	
	@FindBy(id = "input-postcode")
	private WebElement postCodeField;
	
	@FindBy(id = "input-zone")
	private WebElement regionDropdownField;
	
	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement continueButton;
	
	public AddressBookPage enterMandatoryFieldsAndAddAddress(String firstNameText,String lastNameText,String addressOneText,String cityText,String postCodeText) {
		enterFirstName(firstNameText);
		enterLastName(lastNameText);
		enterAddressFieldOne(addressOneText);
		enterCity(cityText);
		enterPostCode(postCodeText);
		selectAnyOptionFromRegionDropdownField();
		return clickOnContinueButton();
	}
	
	public AddressBookPage clickOnContinueButton() {
		elementUtilities.clickOnElement(continueButton);
		return new AddressBookPage(driver);
	}
	
	public void selectAnyOptionFromRegionDropdownField() {
		elementUtilities.selectOptionFromDropdownFieldUsingIndex(regionDropdownField,3);
	}
	
	public void enterPostCode(String postCodeText) {
		elementUtilities.enterTextIntoElement(postCodeField, postCodeText);
	}
	
	public void enterCity(String cityText) {
		elementUtilities.enterTextIntoElement(cityField, cityText);
	}
	
	public void enterAddressFieldOne(String addressOneText) {
		elementUtilities.enterTextIntoElement(addressOneField, addressOneText);
	}
	
	public void enterCompany(String companyText) {
		elementUtilities.enterTextIntoElement(companyField, companyText);
	}
	
	public void enterFirstName(String firstNameText) {
		elementUtilities.enterTextIntoElement(firstNameField,firstNameText);
	}
	
	public void enterLastName(String lastNameText) {
		elementUtilities.enterTextIntoElement(lastNameField, lastNameText);
	}

}
