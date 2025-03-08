package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class CheckoutSuccessPage extends RootPage {

	WebDriver driver;

	public CheckoutSuccessPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(linkText = "Continue")
	private WebElement continueButton;
	
	public HomePage clickOnContinueButton() {
		elementUtilities.clickOnElement(continueButton);
		return new HomePage(driver);
	}

}
