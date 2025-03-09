package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class HeaderOptions extends RootPage {

	WebDriver driver;

	public HeaderOptions(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[text()='My Account']")
	private WebElement myAccountDropMenu;

	@FindBy(linkText = "Register")
	private WebElement registerOption;

	@FindBy(linkText = "Login")
	private WebElement loginOption;

	@FindBy(xpath = "//i[@class='fa fa-phone']")
	private WebElement phoneIcon;

	@FindBy(xpath = "//i[@class='fa fa-heart']")
	private WebElement heartIconOption;

	@FindBy(xpath = "//span[@class='hidden-xs hidden-sm hidden-md'][contains(text(),'Wish List')]")
	private WebElement wishListHeaderOption;

	@FindBy(xpath = "//i[@class='fa fa-shopping-cart']")
	private WebElement shoppingCartHeaderIcon;

	@FindBy(xpath = "//span[text()='Shopping Cart']")
	private WebElement shoppingCartHeaderOption;

	@FindBy(xpath = "//i[@class='fa fa-share']")
	private WebElement checkoutHeaderIcon;

	@FindBy(xpath = "//span[text()='Checkout']")
	private WebElement checkoutOption;

	@FindBy(linkText = "Qafox.com")
	private WebElement logo;

	@FindBy(xpath = "//button[@class='btn btn-default btn-lg']")
	private WebElement searchButton;

	@FindBy(name = "search")
	private WebElement searchBoxField;

	@FindBy(linkText = "Logout")
	private WebElement logoutOption;
	
	@FindBy(linkText = "My Account")
	private WebElement myAccountOption;
	
	public MyAccountPage selectMyAccountOption() {
		elementUtilities.clickOnElement(myAccountOption);
		return new MyAccountPage(driver);
	}
	
	public boolean isSearchBoxFieldDisplayed() {
		return elementUtilities.isElementDisplayed(searchBoxField);
	}
	
	public boolean isSearchButtonDisplayed() {
		return elementUtilities.isElementDisplayed(searchButton);
	}
	
	public boolean areSearchBoxFieldAndSearchButtonDisplayed() {
		return isSearchBoxFieldDisplayed() && isSearchButtonDisplayed();
	}
	
	public String getSearchBoxFieldPlaceHolderText() {
		return elementUtilities.getElementDomAttribute(searchBoxField,"placeholder");
	}
	
	public SearchPage enterProductAndClickOnSearchButton(String productName) {
		enterProductIntoSearchBoxField(productName);
		return clickOnSearchButton();
	}
	
	public void enterProductIntoSearchBoxField(String productName) {
		elementUtilities.enterTextIntoElement(searchBoxField, productName);
	}

	public boolean isLogoutOptionUnderMyAccountDropMenuDisplayed() {
		return elementUtilities.isElementDisplayed(logoutOption);
	}

	public boolean isLoginOptionUnderMyAccountDropMenuDisplayed() {
		return elementUtilities.isElementDisplayed(loginOption);
	}

	public AccountLogoutPage selectLogoutOption() {
		elementUtilities.clickOnElement(logoutOption);
		return new AccountLogoutPage(driver);
	}

	public SearchPage clickOnSearchButton() {
		elementUtilities.clickOnElement(searchButton);
		return new SearchPage(driver);
	}

	public HomePage selectLogo() {
		elementUtilities.clickOnElement(logo);
		return new HomePage(driver);
	}

	public ShoppingCartPage selectCheckoutOption() {
		elementUtilities.clickOnElement(checkoutOption);
		return new ShoppingCartPage(driver);
	}

	public ShoppingCartPage selectCheckoutIcon() {
		elementUtilities.clickOnElement(checkoutHeaderIcon);
		return new ShoppingCartPage(driver);
	}

	public ShoppingCartPage selectShoppingCartOption() {
		elementUtilities.clickOnElement(shoppingCartHeaderOption);
		return new ShoppingCartPage(driver);
	}

	public ShoppingCartPage selectShoppingCartIcon() {
		elementUtilities.clickOnElement(shoppingCartHeaderIcon);
		return new ShoppingCartPage(driver);
	}

	public LoginPage selectWishListOption() {
		elementUtilities.clickOnElement(wishListHeaderOption);
		return new LoginPage(driver);
	}

	public LoginPage selectHeartIconOption() {
		elementUtilities.clickOnElement(heartIconOption);
		return new LoginPage(driver);
	}

	public ContactUsPage selectPhoneIconOption() {
		elementUtilities.clickOnElement(phoneIcon);
		return new ContactUsPage(driver);
	}

	public LoginPage selectLoginOption() {
		elementUtilities.clickOnElement(loginOption);
		return new LoginPage(driver);
	}

	public void clickOnMyAccountDropMenu() {
		elementUtilities.clickOnElement(myAccountDropMenu);
	}

	public LoginPage navigateToLoginPage() {
		clickOnMyAccountDropMenu();
		return selectLoginOption();
	}
	
	public RegisterPage navigateToRegisterPage() {
		clickOnMyAccountDropMenu();
		return selectRegisterOption();
	}

	public RegisterPage selectRegisterOption() {
		elementUtilities.clickOnElement(registerOption);
		return new RegisterPage(driver);
	}

}
