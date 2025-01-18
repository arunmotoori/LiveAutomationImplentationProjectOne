package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class ForgottenPasswordPage extends RootPage {
	
	WebDriver driver;
	
	public ForgottenPasswordPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//ul[@class='breadcrumb']//a[text()='Forgotten Password']")
	private WebElement forgottenPasswordBreadcrumb;
	
	public boolean didWeNavigateToForgottenPasswordPage() {
		return elementUtilities.isElementDisplayed(forgottenPasswordBreadcrumb);
	}

}
