package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;
import utils.CommonUtilities;

public class ProductDisplayPage extends RootPage {

	WebDriver driver;

	public ProductDisplayPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "button-cart")
	WebElement addtoCartButton;

	@FindBy(xpath = "//a[text()='shopping cart']")
	WebElement shoppingCartOption;

	public boolean didWeNavigateToProductDisplayPage() {
		return elementUtilities.isElementDisplayed(addtoCartButton);
	}
	
	public ShoppingCartPage clickOnAddToCartButtonAndSelectShoppingCartOption() {
		clickOnAddToCartButton();
		return selectShoppingCartOptionOnTheSuccessMessage();
	}

	public void clickOnAddToCartButton() {
		elementUtilities.clickOnElement(addtoCartButton);
	}
	
	public ShoppingCartPage selectShoppingCartOptionOnTheSuccessMessage() {
		elementUtilities.waitForElementAndClick(shoppingCartOption,CommonUtilities.AVERAGE_TIME);
		return new ShoppingCartPage(driver);
	}

}
