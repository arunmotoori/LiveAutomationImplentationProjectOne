package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class ReturnInformationPage extends RootPage {
	
	WebDriver driver;
	
	public ReturnInformationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//ul[@class='breadcrumb']//a[text()='Account']")	
	private WebElement accountBreadCrumb;

	
	public MyAccountPage clickOnAccountBreadCrumb() {
		elementUtilities.clickOnElement(accountBreadCrumb);
		return new MyAccountPage(driver);
	}
}
