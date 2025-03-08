package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;
import utils.CommonUtilities;

public class CheckoutPage extends RootPage {
	
	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(id = "button-payment-address")
	private WebElement billingDetailsContinueButton;
	
	@FindBy(id = "button-shipping-address")
	private WebElement deliveryDetailsContinueButton;
	
	@FindBy(id = "button-shipping-method")
	private WebElement deliveryMethodContinueButton;
	
	@FindBy(name = "agree")
	private WebElement termsAndConditionsOption;
	
	@FindBy(id = "button-payment-method")
	private WebElement paymentMethodContinueButton;
	
	@FindBy(id = "button-confirm")
	private WebElement confirmOrderButton;
	
	public CheckoutSuccessPage placeOrder() {
		clickOnBillingDetailsContinueButton();
		clickOnDeliveryDetailsContinueButton();
		clickOnDeliveryMethodContinueButton();
		selectTermsAndConditions();
		clickOnPaymentMethodContinueButton();
		return clickOnConfirmOrderButton();
	}
	
	public CheckoutSuccessPage clickOnConfirmOrderButton() {
		elementUtilities.waitForElementAndClick(confirmOrderButton,CommonUtilities.AVERAGE_TIME);
		return new CheckoutSuccessPage(driver);
	}
	
	public void clickOnPaymentMethodContinueButton() {
		elementUtilities.clickOnElement(paymentMethodContinueButton);
	}
	
	public void selectTermsAndConditions() {
		elementUtilities.waitForElementAndClick(termsAndConditionsOption,CommonUtilities.AVERAGE_TIME);
	}
	
	public void clickOnDeliveryMethodContinueButton() {
		elementUtilities.waitForElementAndClick(deliveryMethodContinueButton,CommonUtilities.AVERAGE_TIME);
	}
	
	public void clickOnBillingDetailsContinueButton() {
		elementUtilities.waitForElementAndClick(billingDetailsContinueButton,CommonUtilities.AVERAGE_TIME);
	}
	
	public void clickOnDeliveryDetailsContinueButton() {
		elementUtilities.waitForElementAndClick(deliveryDetailsContinueButton,CommonUtilities.AVERAGE_TIME);
	}

}
