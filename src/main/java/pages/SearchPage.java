package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;

public class SearchPage extends RootPage {

	WebDriver driver;

	public SearchPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//ul[@class='breadcrumb']//a[text()='Search']")
	private WebElement searchPageBreadcrumb;

	@FindBy(linkText = "HP LP3065")
	private WebElement existingProductOne;
	
	@FindBy(linkText = "iMac")
	private WebElement existingProductThree;

	@FindBy(xpath = "//input[@id='button-search']/following-sibling::p")
	private WebElement noProductMessage;

	@FindBy(xpath = "//div[@class='product-thumb']")
	private List<WebElement> productThumbnail;
	
	@FindBy(id = "button-search")
	private WebElement searchButton;

	@FindBy(id = "input-search")
	private WebElement searchCriteriaField;
	
	@FindBy(id = "description")
	private WebElement searchInProductDescriptionField;

	public void selectSearchInProductDescriptionField() {
		elementUtilities.clickOnElement(searchInProductDescriptionField);
	}
	
	public void enterTextInProductDescriptionIntoSearchCriteriaField(String textInProductDescription) {
		elementUtilities.enterTextIntoElement(searchCriteriaField, textInProductDescription);
	}
	
	public void clickOnSearchButton() {
		elementUtilities.clickOnElement(searchButton);
	}
	
	public void enterProductIntoSearchCriteriaField(String productName) {
		elementUtilities.enterTextIntoElement(searchCriteriaField, productName);
	}

	public String getSearchCriteriaFieldPlaceHolderText() {
		return elementUtilities.getElementDomAttribute(searchCriteriaField,"placeholder");
	}

	public int getProductsCount() {
		return elementUtilities.getElementsCount(productThumbnail);
	}

	public String getNoProductMessage() {
		return elementUtilities.getElementText(noProductMessage);
	}

	public boolean isProductDisplayedInSearchResults() {
		return elementUtilities.isElementDisplayed(existingProductOne);
	}
	
	public boolean isProductHavingTextInItsDescriptionDisplayedInSearchResults() {
		return elementUtilities.isElementDisplayed(existingProductThree);
	}

	public boolean didWeNavigateToSearchResultsPage() {
		return elementUtilities.isElementDisplayed(searchPageBreadcrumb);
	}

}
