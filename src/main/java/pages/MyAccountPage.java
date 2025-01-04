package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class MyAccountPage extends RootPage {
	
	WebDriver driver;
	
	public MyAccountPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(linkText="Edit your account information")
	private WebElement editYourAccountInformationOption;
	
	@FindBy(linkText="Subscribe / unsubscribe to newsletter")
	private WebElement subscribeUnsubscribeToNewsletterOption;
	
	public NewsletterPage clickOnSubscribeOrUnscriberToNewsletterOption() {
		subscribeUnsubscribeToNewsletterOption.click();
		return new NewsletterPage(driver);
	}
	
	public boolean didWeNavigateToMyAccountPage() {
		return editYourAccountInformationOption.isDisplayed();
	}
	
	public MyAccountInformationPage clickOnEditYourAccountInformationOption() {
		editYourAccountInformationOption.click();
		return new MyAccountInformationPage(driver);
	}

}
