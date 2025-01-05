package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class FooterOptions extends RootPage {
	
	WebDriver driver;
	
	public FooterOptions(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//footer//a[text()='About Us']")
	private WebElement aboutUsOption;
	
	@FindBy(xpath="//footer//a[text()='Delivery Information']")
	private WebElement deliveryInformationOption;
	
	@FindBy(xpath="//footer//a[text()='Privacy Policy']")
	private WebElement privacyPolicyOption;
	
	@FindBy(xpath="//footer//a[text()='Terms & Conditions']")
	private WebElement termsAndConditionsOption;
	
	@FindBy(xpath="//footer//a[text()='Contact Us']")
	private WebElement contactUsOption;
	
	@FindBy(xpath="//footer//a[text()='Returns']")
	private WebElement returnsOption;
	
	@FindBy(xpath="//footer//a[text()='Site Map']")
	private WebElement siteMapOption;
	
	@FindBy(xpath="//footer//a[text()='Brands']")
	private WebElement brandsOption;
	
	@FindBy(xpath="//footer//a[text()='Gift Certificates']")
	private WebElement giftCertificatesOption;
	
	@FindBy(xpath="//footer//a[text()='Affiliate']")
	private WebElement affiliateOption;
	
	@FindBy(xpath="//footer//a[text()='Specials']")
	private WebElement specialsOption;
	
	@FindBy(xpath="//footer//a[text()='My Account']")
	private WebElement myAccountOption;
	
	@FindBy(xpath="//footer//a[text()='Order History']")
	private WebElement orderHistory;
	
	@FindBy(xpath="//footer//a[text()='Wish List']")
	private WebElement wishListOption;
	
	@FindBy(xpath="//footer//a[text()='Newsletter']")
	private WebElement newsletterOption;
	
	public LoginPage selectNewsletterOption() {
		newsletterOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage selectWishListOption() {
		wishListOption.click();
		return new LoginPage(driver);
	}
	
	public LoginPage selectOrderHistoryOption() {
		orderHistory.click();
		return new LoginPage(driver);
	}
	
	public LoginPage selectMyAccountOption() {
		myAccountOption.click();
		return new LoginPage(driver);
	}
	
	public SpecialOffersPage selectSpecialsOption() {
		specialsOption.click();
		return new SpecialOffersPage(driver);
	}
	
	public LoginPage selectAffiliateOption() {
		affiliateOption.click();
		return new LoginPage(driver);
	}
	
	public GiftCertificatePage selectGiftCertificatesOption() {
		giftCertificatesOption.click();
		return new GiftCertificatePage(driver);
	}
	
	public BrandPage selectBrandsOption() {
		brandsOption.click();
		return new BrandPage(driver);
	}
	
	public SiteMapPage selectSiteMapOption() {
		siteMapOption.click();
		return new SiteMapPage(driver);
	}
	
	public ProductReturnsPage selectReturnsOption() {
		returnsOption.click();
		return new ProductReturnsPage(driver);
	}
	
	public ContactUsPage selectContactUsOption() {
		contactUsOption.click();
		return new ContactUsPage(driver);
	}
	
	public TermsAndConditionsPage selectTermsAndConditionsOption() {
		termsAndConditionsOption.click();
		return new TermsAndConditionsPage(driver);
	}
	
	public PrivacyPolicyPage selectPrivacyPolicyOption() {
		privacyPolicyOption.click();
		return new PrivacyPolicyPage(driver);
	}
	
	public DeliveryInformationPage selectDeliveryInformationOption() {
		deliveryInformationOption.click();
		return new DeliveryInformationPage(driver);
	}
	
	public AboutUsPage selectAboutUsOption() {
		aboutUsOption.click();
		return new AboutUsPage(driver);
	}

}
