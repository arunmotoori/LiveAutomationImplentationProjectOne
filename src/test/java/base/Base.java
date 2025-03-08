package base;

import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;

import pages.AboutUsPage;
import pages.AccountLogoutPage;
import pages.AccountSuccessPage;
import pages.AddAddressPage;
import pages.AddressBookPage;
import pages.AffiliatePage;
import pages.BrandPage;
import pages.ChangePasswordPage;
import pages.CheckoutPage;
import pages.CheckoutSuccessPage;
import pages.ContactUsPage;
import pages.DeliveryInformationPage;
import pages.EditAddressPage;
import pages.FooterOptions;
import pages.ForgottenPasswordPage;
import pages.GiftCertificatePage;
import pages.HeaderOptions;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountInformationPage;
import pages.MyAccountPage;
import pages.NewsletterPage;
import pages.OrderHistoryPage;
import pages.OrderInformationPage;
import pages.PrivacyPolicyPage;
import pages.ProductComparisonPage;
import pages.ProductDisplayPage;
import pages.ProductReturnsPage;
import pages.RegisterPage;
import pages.ReturnInformationPage;
import pages.RightColumnOptions;
import pages.SearchPage;
import pages.ShoppingCartPage;
import pages.SiteMapPage;
import pages.SpecialOffersPage;
import pages.TermsAndConditionsPage;
import utils.CommonUtilities;

public class Base {
	
	WebDriver driver;
	public Properties prop;
	public String browserName;
	public HeaderOptions headerOptions;
	public RegisterPage registerPage;
	public AccountSuccessPage accountSuccessPage;
	public MyAccountPage myAccountPage;
	public NewsletterPage newsletterPage;
	public LoginPage loginPage;
	public RightColumnOptions rightColumnOptions;
	public MyAccountInformationPage myAccountInformationPage;
	public ContactUsPage contactUsPage;
	public ShoppingCartPage shoppingCartPage;
	public HomePage homePage;
	public SearchPage searchPage;
	public ForgottenPasswordPage forgottenPasswordPage;
	public FooterOptions footerOptions;
	public AboutUsPage aboutUsPage;
	public DeliveryInformationPage deliveryInformationPage;
	public PrivacyPolicyPage privacyPolicyPage;
	public TermsAndConditionsPage termsAndConditionsPage;
	public ProductReturnsPage productReturnsPage;
	public SiteMapPage siteMapPage;
	public BrandPage brandPage;
	public GiftCertificatePage giftCertificatePage;
	public SpecialOffersPage specialOffersPage;
	public Actions actions;
	public AccountLogoutPage accountLogoutPage;
	public ChangePasswordPage changePasswordPage;
	public ProductDisplayPage productDisplayPage;
	public ProductComparisonPage productComparisonPage;
	public AddressBookPage addressBookPage;
	public AddAddressPage addAddressPage;
	public EditAddressPage editAddressPage;
	public OrderHistoryPage orderHistoryPage;
	public OrderInformationPage orderInformationPage;
	public AffiliatePage affiliatePage;
	public ReturnInformationPage returnInformationPage;
	public CheckoutPage checkoutPage;
	public CheckoutSuccessPage checkoutSuccessPage;
	
	
	@AfterMethod
	public void teardown() {
		if(driver!=null) {
			driver.quit();
		}
	}
	
	public WebDriver openBrowserAndApplicationPageURL() {
		
		prop = CommonUtilities.loadPropertiesFile();
		browserName = prop.getProperty("browserName");
		
		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("internetexplorer")) {
			driver = new InternetExplorerDriver();
		} else if (browserName.equalsIgnoreCase("safari")) {
			driver = new SafariDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonUtilities.MIN_TIME));
		driver.get(prop.getProperty("appURL"));
		
		return driver;
		
	}
	
	public void refreshAndNavigateToPage(WebDriver driver,String pageURL) {
		refreshPage(driver);
		navigateToPage(pageURL);
	}
	
	public void navigateToPage(String pageURL) {
		driver.navigate().to(pageURL);
	}
	
	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	public String getPageURL(WebDriver driver) {
		return driver.getCurrentUrl();
	}
	
	public void navigateBackInBrowser(WebDriver driver) {
		driver.navigate().back();
	}
	
	public void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	public String getPageSourceCode(WebDriver driver) {
		return driver.getPageSource();
	}
	
	public Actions getActions(WebDriver driver) {
		Actions actions = new Actions(driver);
		return actions;
	}
	
	public Actions clickKeyboradKeyMultipleTimes(Actions actions,Keys keyName,int noOfTimes) {
		for (int i = 1; i <= noOfTimes; i++) {
			actions.sendKeys(keyName).perform();
		}
		return actions;
	}
	
	public Actions typeTextUsingActions(Actions actions,String text) {
		actions.sendKeys(text).perform();
		return actions;
	}
	
	public Properties swapPasswords(Properties prop) {
		String oldPassword = prop.getProperty("validPasswordTwo");
		String newPassword = prop.getProperty("validPasswordThree");
		prop.setProperty("validPasswordTwo", newPassword);
		prop.setProperty("validPasswordThree", oldPassword);
	    prop = CommonUtilities.storePropertiesFile(prop);
	    return prop;
	}
	
	public String getBaseURL() {
		return prop.getProperty("appURL");
	}

}
