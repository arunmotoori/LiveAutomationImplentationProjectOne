package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.Base;
import pages.HeaderOptions;
import utils.CommonUtilities;

public class Search extends Base {

	WebDriver driver;

	@BeforeMethod
	public void setup() {

		driver = openBrowserAndApplicationPageURL();
		headerOptions = new HeaderOptions(driver);

	}
	
	@Test(priority = 1)
	public void verifySearchWithAnExistingProduct() {
		
		headerOptions.enterProductIntoSearchBoxField(prop.getProperty("existingProduct"));
		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.didWeNavigateToSearchResultsPage());
		Assert.assertTrue(searchPage.isProductDisplayedInSearchResults());
		
	}
	
	@Test(priority = 2)
	public void verifySearchWithANonExistingProduct() {
		
		headerOptions.enterProductIntoSearchBoxField(prop.getProperty("nonExistingProduct"));
		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.didWeNavigateToSearchResultsPage());
		Assert.assertEquals(searchPage.getNoProductMessage(),"There is no product that matches the search criteria.");
		
	}
	
	@Test(priority = 3)
	public void verifySearchWithoutEnterAnyProduct() {
		
		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.didWeNavigateToSearchResultsPage());
		Assert.assertEquals(searchPage.getNoProductMessage(),"There is no product that matches the search criteria.");
		
	}
	
	@Test(priority = 4)
	public void verifyProductSearchAfterLogin() {
		
		loginPage = headerOptions.navigateToLoginPage();
		myAccountPage = loginPage.loginInToApplication(prop.getProperty("existingEmail"),prop.getProperty("validPassword"));
		headerOptions = myAccountPage.getHeaderOptions();
		headerOptions.enterProductIntoSearchBoxField(prop.getProperty("existingProduct"));
		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.didWeNavigateToSearchResultsPage());
		Assert.assertTrue(searchPage.isProductDisplayedInSearchResults());
		
	}
	
	@Test(priority = 5)
	public void verifyProductSeachResultingMultipleProducts() {
		
		headerOptions.enterProductIntoSearchBoxField(prop.getProperty("existingProductTwo"));
		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.didWeNavigateToSearchResultsPage());
		Assert.assertTrue(searchPage.getProductsCount() > 0);
		
	}
	
	@Test(priority = 6)
	public void verifySearchFunctionalityFielsPlaceHolders() {
		
		Assert.assertEquals(headerOptions.getSearchBoxFieldPlaceHolderText(),"Search");
		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertEquals(searchPage.getSearchCriteriaFieldPlaceHolderText(),"Keywords");
		
	}
	
	@Test(priority = 7)
	public void verifySearchFunctionalityUsingSearchCriteriaField() {
		
		searchPage = headerOptions.clickOnSearchButton();
		searchPage.enterProductIntoSearchCriteriaField(prop.getProperty("existingProduct"));
		searchPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.isProductDisplayedInSearchResults());
		
	}
	
	@Test(priority = 8)
	public void verifySearchUsingTextInProductDescription() {
		
		searchPage = headerOptions.clickOnSearchButton();
		searchPage.enterTextInProductDescriptionIntoSearchCriteriaField(prop.getProperty("textInProductDescription"));
		searchPage.selectSearchInProductDescriptionField();
		searchPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.isProductHavingTextInItsDescriptionDisplayedInSearchResults());
		
	}
	
	@Test(priority = 9)
	public void verifySearchBySelectingTheCategory() {
		
		searchPage = headerOptions.clickOnSearchButton();
		searchPage.enterProductIntoSearchCriteriaField(prop.getProperty("existingProductThree"));
		searchPage.selectOptionFromCategoryDropdownField(CommonUtilities.convertToInteger(prop.getProperty("correctCategoryIndex")));
		searchPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.isProductFromCorrectCategoryDisplayedInSearchResults());
		searchPage.selectOptionFromCategoryDropdownField(CommonUtilities.convertToInteger(prop.getProperty("wrongCategoryIndex")));
		searchPage.clickOnSearchButton();
		Assert.assertEquals(searchPage.getNoProductMessage(),"There is no product that matches the search criteria.");
		
	}
	
	@Test(priority = 10)
	public void verifySearchBySelectingToSearchInSubCategories() {
		
		searchPage = headerOptions.clickOnSearchButton();
		searchPage.enterProductIntoSearchCriteriaField(prop.getProperty("existingProductThree"));
		searchPage.selectOptionFromCategoryDropdownField(prop.getProperty("superCategory"));
		searchPage.clickOnSearchButton();
		Assert.assertEquals(searchPage.getNoProductMessage(),"There is no product that matches the search criteria.");
		searchPage.selectToSearchInSubCategories();
		searchPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.isProductFromCorrectCategoryDisplayedInSearchResults());
		
	}
	
	
	
	

}
