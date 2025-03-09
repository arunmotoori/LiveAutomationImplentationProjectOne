package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class AffiliatePage extends RootPage {
	
	WebDriver driver;
	
	public AffiliatePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(id="input-cheque")
	private WebElement chequePayeeNameField;
	
	@FindBy(name = "agree")
	private WebElement agrementOption;
	
	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement continueButton;
	
	public MyAccountPage updateAffiliateAccount(String chequePayeeNameText) {
		enterChequePayeeName(chequePayeeNameText);
		selectAgrement();
		return clickOnContinueButton();
	}
	
	public void enterChequePayeeName(String chequePayeeNameText) {
		elementUtilities.enterTextIntoElement(chequePayeeNameField,chequePayeeNameText);
	}
	
	public void selectAgrement() {
		elementUtilities.clickOnElement(agrementOption);
	}
	
	public MyAccountPage clickOnContinueButton() {
		elementUtilities.clickOnElement(continueButton);
		return new MyAccountPage(driver);
	}
	
	
	
	
	
	

}
