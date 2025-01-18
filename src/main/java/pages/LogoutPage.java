package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class LogoutPage extends RootPage {
	
	WebDriver driver;
	
	public LogoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
		
	}
	
	public LoginPage getLoginPage() {
		return new LoginPage(driver);
	}

}
