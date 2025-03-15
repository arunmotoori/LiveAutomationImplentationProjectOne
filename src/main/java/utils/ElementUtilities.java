package utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtilities {

	WebDriver driver;
	Actions actions;
	Select select;

	public ElementUtilities(WebDriver driver) {
		this.driver = driver;
	}

	public List<String> getTextOfElements(List<WebElement> items) {
		List<String> itemNames = new ArrayList<>();
		for (WebElement item : items) {
			itemNames.add(getElementText(item));
		}
		return itemNames;
	}

	public void selectOptionFromDropdownFieldUsingIndex(WebElement element, int optionIndex) {
		if (isElementDisplayedOnPage(element) && element.isEnabled()) {
			select = new Select(element);
			select.selectByIndex(optionIndex);
		}
	}

	public void selectOptionFromDropdownFieldUsingVisibleText(WebElement element, String optionText) {
		if (isElementDisplayedOnPage(element) && element.isEnabled()) {
			select = new Select(element);
			select.selectByVisibleText(optionText);
		}
	}

	public void waitForElement(WebElement element, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public boolean waitAndCheckElementDisplayStatus(WebElement element, int seconds) {
		boolean b = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
			wait.until(ExpectedConditions.visibilityOf(element));
		    b = true;
		}catch(Exception e) {
			b = false;
		}
		return b;
	}


	public void waitForElementAndClick(WebElement element, int seconds) {
		waitForElement(element, seconds);
		clickOnElement(element);
	}

	public void clickEitherOfTheseElements(WebElement elementOne, WebElement elementTwo) {
		if(isElementDisplayedOnPageWithoutException(elementOne)) {
			elementOne.click();
		}else {
			elementTwo.click();
		}
	}

	public void clickOnElement(WebElement element) {
		if (isElementDisplayedOnPage(element) && element.isEnabled()) {
			element.click();
		}
	}

	public Actions getActions(WebDriver driver) {
		actions = new Actions(driver);
		return actions;
	}

	public void copyingTextUsingKeyboardKeys(WebDriver driver) {
		actions = getActions(driver);
		actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).keyDown(Keys.CONTROL).sendKeys("c")
				.keyUp(Keys.CONTROL).build().perform();
	}

	public void pasteTextIntoFieldUsingKeyboardKeys(WebElement element, WebDriver driver) {
		actions = getActions(driver);
		actions.click(element).keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).build().perform();
	}

	public String getElementText(WebElement element) {
		String elementText = "";
		if (isElementDisplayed(element)) {
			elementText = element.getText();
		}
		return elementText;
	}

	public boolean isElementDisplayed(WebElement element) {
		boolean b = false;
		try {
			b = element.isDisplayed();
		} catch (NoSuchElementException e) {
			b = false;
		}
		return b;
	}

	public boolean isElementDisplayedOnPage(WebElement element) {
		boolean b = false;
		b = element.isDisplayed();
		return b;
	}

	public boolean isElementDisplayedOnPageWithoutException(WebElement element) {
		boolean b = false;
		try {
			b = element.isDisplayed();
		} catch (NoSuchElementException e) {
			b = false;
		}
		return b;
	}

	public String getElementDomAttribute(WebElement element, String attributeName) {
		return element.getDomAttribute(attributeName);
	}

	public String getElementDomProperty(WebElement element, String attributeName) {
		return element.getDomProperty(attributeName);
	}

	public boolean isElementSelected(WebElement element) {
		boolean b = false;
		if (isElementDisplayedOnPage(element)) {
			b = element.isSelected();
		}
		return b;
	}

	public String getElementCSSValue(WebElement element, String cssPropertyName) {
		String value = "";
		value = element.getCssValue(cssPropertyName);
		return value;
	}

	public void clearTextFromElement(WebElement element) {
		if (isElementDisplayedOnPage(element) && element.isEnabled()) {
			element.clear();
		}
	}

	public void enterTextIntoElement(WebElement element, String text) {
		if (isElementDisplayedOnPage(element) && element.isEnabled()) {
			clearTextFromElement(element);
			element.sendKeys(text);
		}
	}

	public int getElementsCount(List<WebElement> elements) {

		int count = 0;

		try {
			count = elements.size();
		} catch (NoSuchElementException e) {
			count = 0;
		}

		return count;

	}
}
