package pages.root;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class RootPage {
	
	WebDriver driver;
	
	public RootPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	public WebDriver getDriver() {
		return driver;
	}

}
