package utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtilities {
	
	WebDriver driver;
	
	public ElementUtilities(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickOnElement(WebElement element) {
		if(isElementDisplayedOnPage(element) && element.isEnabled()) {
			element.click();
		}
	}
	
	public String getElementText(WebElement element){
		String elementText = "";
		if(isElementDisplayed(element)) {
			elementText = element.getText();
		}
		return elementText;
	}
	
	public boolean isElementDisplayed(WebElement element) {
		boolean b = false;
		try {
			b = element.isDisplayed();
		} catch(NoSuchElementException e) {
			b = false;
		}
		return b;
	}
	
	public boolean isElementDisplayedOnPage(WebElement element) {
		boolean b = false;
		b = element.isDisplayed();
		return b;
	}
	
	public String getElementDomAttribute(WebElement element,String attributeName) {
		return element.getDomAttribute(attributeName);
	}
	
	public String getElementDomProperty(WebElement element,String attributeName) {
		return element.getDomProperty(attributeName);
	}
	
	public boolean isElementSelected(WebElement element)  {
		boolean b = false;
		if(isElementDisplayedOnPage(element)) {
			b = element.isSelected();
		}
		return b;
	}
	
	public String getElementCSSValue(WebElement element, String cssPropertyName){
		String value = "";
		value = element.getCssValue(cssPropertyName);
		return value;
	}
	
	public void clearTextFromElement(WebElement element)  {
		if(isElementDisplayedOnPage(element) && element.isEnabled()) {
			element.clear();
		}
	}
	
	public void enterTextIntoElement(WebElement element, String text) {
		if(isElementDisplayedOnPage(element) && element.isEnabled()) {
			clearTextFromElement(element);
			element.sendKeys(text);
		}
	}
}
