package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class EditAddressPage extends RootPage {
	
	WebDriver driver;
	
	public EditAddressPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//ul[@class='breadcrumb']//a[text()='Account']")
	private WebElement accountBreadcrumb;
	
	public MyAccountPage clickOnAccountBreadcrumb() {
		elementUtilities.clickOnElement(accountBreadcrumb);
		return new MyAccountPage(driver);
	}
	

}
