package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class AccountLogoutPage extends RootPage {
	
	WebDriver driver;
	
	public AccountLogoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//a[text()='Continue']")
	private WebElement continueButton;
	
	@FindBy(xpath="//ul[@class='breadcrumb']//a[text()='Logout']")
	private WebElement logoutPageBreadcrumb;
	
	public HomePage clickOnContinueButton() {
		elementUtilities.clickOnElement(continueButton);
		return new HomePage(driver);
	}
	
	public boolean didWeNavigateToAccountLogoutPage() {
		return elementUtilities.isElementDisplayed(logoutPageBreadcrumb);
	}
	
	public LoginPage getLoginPage() {
		return new LoginPage(driver);
	}

}
