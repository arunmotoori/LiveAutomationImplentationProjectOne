package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class OrderHistoryPage extends RootPage {
	
	WebDriver driver;
	
	public OrderHistoryPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//a[@*='View']")
	private WebElement viewOption;
	
	public OrderInformationPage selectViewOption() {
		elementUtilities.clickOnElement(viewOption);
		return new OrderInformationPage(driver);
	}

}
