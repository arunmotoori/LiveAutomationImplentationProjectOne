package pages;

import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pages.root.RootPage;
import utils.CommonUtilities;

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

	@FindBy(className = "product-thumb")
	private List<WebElement> productThumbnail;
	
	@FindBy(id = "button-search")
	private WebElement searchButton;

	@FindBy(id = "input-search")
	private WebElement searchCriteriaField;
	
	@FindBy(id = "description")
	private WebElement searchInProductDescriptionField;
	
	@FindBy(name = "category_id")
	private WebElement categoriesDropdownField;
	
	@FindBy(name = "sub_category")
	private WebElement searchInSubCategoriesOption;
	
	@FindBy(id = "list-view")
	private WebElement listOption;
	
	@FindBy(id = "grid-view")
	private WebElement gridOption;
	
	@FindBy(xpath = "//div[@class='product-thumb']//span[text()='Add to Cart']")
	private WebElement addToCartOption;
	
	@FindBy(xpath = "//button[@*='Add to Wish List']")
	private WebElement addToWishListOption;
	
	@FindBy(xpath = "//button[@*='Compare this Product']")
	private WebElement compareThisProductOption;
	
	@FindBy(xpath = "(//div[@class='product-thumb']//img)[1]")
	private WebElement productOneImage;
	
	@FindBy(xpath = "(//div[@class='product-thumb']//h4//a)[1]")
	private WebElement productOneName;
	
	@FindBy(id = "compare-total")
	private WebElement productCompareOption;
	
	@FindBy(id = "input-sort")
	private WebElement sortDropdownField;
	
	@FindBy(xpath = "//div[@class='product-thumb']//h4/a")
	private List<WebElement> sortedProducts;
	
	public boolean didProductsGotDisplayedInAscendingOrder() {
		return CommonUtilities.areItemsInListAreInAscendingOrder(elementUtilities.getTextOfElements(sortedProducts));
	}
	
	public void selectSortOptionInDropdownField(String optionText) {
		elementUtilities.selectOptionFromDropdownFieldUsingVisibleText(sortDropdownField, optionText);
	}
	
	public ProductComparisonPage selectProductCompareOption() {
		elementUtilities.clickOnElement(productCompareOption);
		return new ProductComparisonPage(driver);
	}
	
	public void selectGridOption() {
		elementUtilities.clickOnElement(gridOption);
	}
	
	public ProductDisplayPage clickOnProductOneName() {
		elementUtilities.clickOnElement(productOneName);
		return new ProductDisplayPage(driver);
	}
	
	public ProductDisplayPage clickOnProductOneImage() {
		elementUtilities.clickOnElement(productOneImage);
		return new ProductDisplayPage(driver);
	}
	
	public void clickOnCompareThisProductOption() {
		elementUtilities.clickOnElement(compareThisProductOption);
	}
	
	public void clickOnAddToWishListOption() {
		elementUtilities.clickOnElement(addToWishListOption);
	}
	
	public void clickOnAddToCartOption() {
		elementUtilities.clickOnElement(addToCartOption);
	}
	
	public void selectListOption() {
		elementUtilities.clickOnElement(listOption);
	}
	
	public void selectToSearchInSubCategories() {
		elementUtilities.clickOnElement(searchInSubCategoriesOption);
	}
	
	public void selectOptionFromCategoryDropdownField(int optionIndex) {
		elementUtilities.selectOptionFromDropdownFieldUsingIndex(categoriesDropdownField,optionIndex);
	}
	
	public void selectOptionFromCategoryDropdownField(String optionText) {
		elementUtilities.selectOptionFromDropdownFieldUsingVisibleText(categoriesDropdownField,optionText);
	}

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
	
	public boolean isProductFromCorrectCategoryDisplayedInSearchResults() {
		return elementUtilities.isElementDisplayed(existingProductThree);
	}
	
	public boolean isProductHavingTextInItsDescriptionDisplayedInSearchResults() {
		return elementUtilities.isElementDisplayed(existingProductThree);
	}

	public boolean didWeNavigateToSearchResultsPage() {
		return elementUtilities.isElementDisplayed(searchPageBreadcrumb);
	}

}
