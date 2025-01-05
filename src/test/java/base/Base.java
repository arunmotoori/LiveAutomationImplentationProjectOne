package base;

import org.openqa.selenium.WebDriver;

public class Base {
	
	WebDriver driver;
	
	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	public String getPageURL(WebDriver driver) {
		return driver.getCurrentUrl();
	}
	
	public void navigateBackInBrowser(WebDriver driver) {
		driver.navigate().back();
	}

}
