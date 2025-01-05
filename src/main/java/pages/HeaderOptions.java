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
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//span[text()='My Account']")
	private WebElement myAccountDropMenu;
	
	@FindBy(linkText="Register")
	private WebElement registerOption;
	
	@FindBy(linkText="Login")
	private WebElement loginOption;
	
	@FindBy(xpath="//i[@class='fa fa-phone']")
	private WebElement phoneIcon;
	
	@FindBy(xpath="//i[@class='fa fa-heart']")
	private WebElement heartIconOption;
	
	@FindBy(xpath="//span[@class='hidden-xs hidden-sm hidden-md'][contains(text(),'Wish List')]")
	private WebElement wishListHeaderOption;
	
	@FindBy(xpath="//i[@class='fa fa-shopping-cart']")
	private WebElement shoppingCartHeaderIcon;
	
	@FindBy(xpath="//span[text()='Shopping Cart']")
	private WebElement shoppingCartHeaderOption;
	
	@FindBy(xpath="//i[@class='fa fa-share']")
	private WebElement checkoutHeaderIcon;
	
	@FindBy(xpath="//span[text()='Checkout']")
	private WebElement checkoutOption;
	
	@FindBy(linkText="Qafox.com")
	private WebElement logo;
	
	@FindBy(xpath="//button[@class='btn btn-default btn-lg']")
	private WebElement searchButton;
	
	public SearchPage clickOnSearchButton() {
		searchButton.click();
		return new SearchPage(driver);
	}
	
	public HomePage selectLogo() {
		logo.click();
		return new HomePage(driver);
	}
	
	public ShoppingCartPage selectCheckoutOption() {
		checkoutOption.click();
		return new ShoppingCartPage(driver);
	}
	
	public ShoppingCartPage selectCheckoutIcon() {
		checkoutHeaderIcon.click();
		return new ShoppingCartPage(driver);
	}
	
	public ShoppingCartPage selectShoppingCartOption() {
		shoppingCartHeaderOption.click();
		return new ShoppingCartPage(driver);
	}
	
	public ShoppingCartPage selectShoppingCartIcon() {
		shoppingCartHeaderIcon.click();
		return new ShoppingCartPage(driver);
	}
	
	public LoginPage selectWishListOption() {
		wishListHeaderOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage selectHeartIconOption() {
		heartIconOption.click();
		return new LoginPage(driver);
	}
	
	public ContactUsPage selectPhoneIconOption() {
		phoneIcon.click();
		return new ContactUsPage(driver);
	}
	
	public LoginPage selectLoginOption() {
		loginOption.click();
		return new LoginPage(driver);
	}
	
	public void clickOnMyAccountDropMenu() {
		myAccountDropMenu.click();
	}
	
	public RegisterPage selectRegisterOption() {
		registerOption.click();
		return new RegisterPage(driver);
	}

}
