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
		elementUtilities.clickOnElement(newsletterOption);
		return new LoginPage(driver);
	}
	
	public LoginPage selectWishListOption() {
		elementUtilities.clickOnElement(wishListOption);
		return new LoginPage(driver);
	}
	
	public LoginPage selectOrderHistoryOption() {
		elementUtilities.clickOnElement(orderHistory);
		return new LoginPage(driver);
	}
	
	public LoginPage selectMyAccountOption() {
		elementUtilities.clickOnElement(myAccountOption);
		return new LoginPage(driver);
	}
	
	public SpecialOffersPage selectSpecialsOption() {
		elementUtilities.clickOnElement(specialsOption);
		return new SpecialOffersPage(driver);
	}
	
	public LoginPage selectAffiliateOption() {
		elementUtilities.clickOnElement(affiliateOption);
		return new LoginPage(driver);
	}
	
	public GiftCertificatePage selectGiftCertificatesOption() {
		elementUtilities.clickOnElement(giftCertificatesOption);
		return new GiftCertificatePage(driver);
	}
	
	public BrandPage selectBrandsOption() {
		elementUtilities.clickOnElement(brandsOption);
		return new BrandPage(driver);
	}
	
	public SiteMapPage selectSiteMapOption() {
		elementUtilities.clickOnElement(siteMapOption);
		return new SiteMapPage(driver);
	}
	
	public ProductReturnsPage selectReturnsOption() {
		elementUtilities.clickOnElement(returnsOption);
		return new ProductReturnsPage(driver);
	}
	
	public ContactUsPage selectContactUsOption() {
		elementUtilities.clickOnElement(contactUsOption);
		return new ContactUsPage(driver);
	}
	
	public TermsAndConditionsPage selectTermsAndConditionsOption() {
		elementUtilities.clickOnElement(termsAndConditionsOption);
		return new TermsAndConditionsPage(driver);
	}
	
	public PrivacyPolicyPage selectPrivacyPolicyOption() {
		elementUtilities.clickOnElement(privacyPolicyOption);
		return new PrivacyPolicyPage(driver);
	}
	
	public DeliveryInformationPage selectDeliveryInformationOption() {
		elementUtilities.clickOnElement(deliveryInformationOption);
		return new DeliveryInformationPage(driver);
	}
	
	public AboutUsPage selectAboutUsOption() {
		elementUtilities.clickOnElement(aboutUsOption);
		return new AboutUsPage(driver);
	}

}
