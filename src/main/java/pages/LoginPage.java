package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class LoginPage extends RootPage {
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//a[@class='btn btn-primary'][text()='Continue']")
	private WebElement continueButton;
	
	public RegisterPage clickOnContinueButton() {
		elementUtilities.clickOnElement(continueButton);
		return new RegisterPage(driver);
	}
	
	

}
