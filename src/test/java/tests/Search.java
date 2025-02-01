package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.Base;
import pages.HeaderOptions;

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

}
