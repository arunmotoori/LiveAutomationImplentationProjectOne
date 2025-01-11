package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class AccountSuccessPage extends RootPage {
	
	WebDriver driver; 
	
	public AccountSuccessPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Logout']")
	private WebElement logoutOption;
	
	@FindBy(xpath="//ul[@class='breadcrumb']//a[text()='Success']")
	private WebElement accountSuccessPageBreadcrumb;
	
	@FindBy(id="content")
	private WebElement accountSuccessPageContent;
	
	@FindBy(linkText="Continue")
	private WebElement continueButton;
	
	public MyAccountPage clickOnContinueButton() {
		elementUtilities.clickOnElement(continueButton);
		return new MyAccountPage(driver);
	}
	
	public String getContent() {
		return elementUtilities.getElementText(accountSuccessPageContent);
	}
	
	public boolean didWeNavigateToAccountSuccessPage() {
		return elementUtilities.isElementDisplayed(accountSuccessPageBreadcrumb);
	}
	
	public boolean isUserLoggedIn() {
		return elementUtilities.isElementDisplayed(logoutOption);
	}
	

}
