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
		Assert.assertEquals(searchPage.getNoProductMessage(), "There is no product that matches the search criteria.");

	}

	@Test(priority = 3)
	public void verifySearchWithoutEnterAnyProduct() {

		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.didWeNavigateToSearchResultsPage());
		Assert.assertEquals(searchPage.getNoProductMessage(), "There is no product that matches the search criteria.");

	}

	@Test(priority = 4)
	public void verifyProductSearchAfterLogin() {

		loginPage = headerOptions.navigateToLoginPage();
		myAccountPage = loginPage.loginInToApplication(prop.getProperty("existingEmail"),
				prop.getProperty("validPassword"));
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

		Assert.assertEquals(headerOptions.getSearchBoxFieldPlaceHolderText(), "Search");
		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertEquals(searchPage.getSearchCriteriaFieldPlaceHolderText(), "Keywords");

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
		searchPage.selectOptionFromCategoryDropdownField(
				CommonUtilities.convertToInteger(prop.getProperty("correctCategoryIndex")));
		searchPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.isProductFromCorrectCategoryDisplayedInSearchResults());
		searchPage.selectOptionFromCategoryDropdownField(
				CommonUtilities.convertToInteger(prop.getProperty("wrongCategoryIndex")));
		searchPage.clickOnSearchButton();
		Assert.assertEquals(searchPage.getNoProductMessage(), "There is no product that matches the search criteria.");

	}

	@Test(priority = 10)
	public void verifySearchBySelectingToSearchInSubCategories() {

		searchPage = headerOptions.clickOnSearchButton();
		searchPage.enterProductIntoSearchCriteriaField(prop.getProperty("existingProductThree"));
		searchPage.selectOptionFromCategoryDropdownField(prop.getProperty("superCategory"));
		searchPage.clickOnSearchButton();
		Assert.assertEquals(searchPage.getNoProductMessage(), "There is no product that matches the search criteria.");
		searchPage.selectToSearchInSubCategories();
		searchPage.clickOnSearchButton();
		Assert.assertTrue(searchPage.isProductFromCorrectCategoryDisplayedInSearchResults());

	}

	@Test(priority = 11)
	public void verifyListAndGridViewsInSearchResultsPageHavingOneProduct() throws InterruptedException {

		headerOptions.enterProductIntoSearchBoxField(prop.getProperty("existingProductThree"));
		searchPage = headerOptions.clickOnSearchButton();
		searchPage.selectListOption();
		Assert.assertTrue(searchPage.getProductsCount() == 1);
		searchPage.clickOnAddToCartOption();
		String expectedMessage = "Success: You have added " + prop.getProperty("existingProductThree")
				+ " to your shopping cart!";
		Assert.assertTrue(searchPage.getPageLevelSuccessMessage().contains(expectedMessage));
		refreshPage(searchPage.getDriver());
		searchPage.clickOnAddToWishListOption();
		expectedMessage = "You must login or create an account to save " + prop.getProperty("existingProductThree")
				+ " to your wish list!";
		Assert.assertTrue(searchPage.getPageLevelSuccessMessage().contains(expectedMessage));
		refreshPage(searchPage.getDriver());
		searchPage.clickOnCompareThisProductOption();
		expectedMessage = "Success: You have added " + prop.getProperty("existingProductThree")
				+ " to your product comparison!";
		Assert.assertTrue(searchPage.getPageLevelSuccessMessage().contains(expectedMessage));
		productDisplayPage = searchPage.clickOnProductOneImage();
		Assert.assertTrue(productDisplayPage.didWeNavigateToProductDisplayPage());
		navigateBackInBrowser(productDisplayPage.getDriver());
		productDisplayPage = searchPage.clickOnProductOneName();
		Assert.assertTrue(productDisplayPage.didWeNavigateToProductDisplayPage());
		navigateBackInBrowser(productDisplayPage.getDriver());
		refreshPage(searchPage.getDriver());
		searchPage.selectGridOption();
		Assert.assertTrue(searchPage.getProductsCount() == 1);
		searchPage.clickOnAddToCartOption();
		expectedMessage = "Success: You have added " + prop.getProperty("existingProductThree")
				+ " to your shopping cart!";
		Assert.assertTrue(searchPage.getPageLevelSuccessMessage().contains(expectedMessage));
		refreshPage(searchPage.getDriver());
		searchPage.clickOnAddToWishListOption();
		expectedMessage = "You must login or create an account to save " + prop.getProperty("existingProductThree")
				+ " to your wish list!";
		Assert.assertTrue(searchPage.getPageLevelSuccessMessage().contains(expectedMessage));
		refreshPage(searchPage.getDriver());
		searchPage.clickOnCompareThisProductOption();
		expectedMessage = "Success: You have added " + prop.getProperty("existingProductThree")
				+ " to your product comparison!";
		Assert.assertTrue(searchPage.getPageLevelSuccessMessage().contains(expectedMessage));
		productDisplayPage = searchPage.clickOnProductOneImage();
		Assert.assertTrue(productDisplayPage.didWeNavigateToProductDisplayPage());
		navigateBackInBrowser(productDisplayPage.getDriver());
		productDisplayPage = searchPage.clickOnProductOneName();
		Assert.assertTrue(productDisplayPage.didWeNavigateToProductDisplayPage());
		navigateBackInBrowser(productDisplayPage.getDriver());

	}

	@Test(priority = 12)
	public void verifyListAndGridViewsWhenMultipleProductsAreDisplayed() {

		headerOptions.enterProductIntoSearchBoxField(prop.getProperty("existingProductTwo"));
		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.getProductsCount() > 1);
		searchPage.selectListOption();
		Assert.assertTrue(searchPage.getProductsCount() > 1);
		searchPage.selectGridOption();
		Assert.assertTrue(searchPage.getProductsCount() > 1);
		;

	}

	@Test(priority = 13)
	public void verifyNavigationToProductComparisonPageFromSearchResultsPage() {

		headerOptions.enterProductIntoSearchBoxField(prop.getProperty("existingProductThree"));
		searchPage = headerOptions.clickOnSearchButton();
		productComparisonPage = searchPage.selectProductCompareOption();
		Assert.assertTrue(productComparisonPage.didWeNavigateToProductComparisonPage());

	}

	@Test(priority = 14)
	public void verifyAllSortingOptionsInSearchResultsPage() {

		headerOptions.enterProductIntoSearchBoxField(prop.getProperty("existingProductTwo"));
		searchPage = headerOptions.clickOnSearchButton();
		searchPage.selectSortOptionInDropdownField("Default");
		Assert.assertTrue(searchPage.didProductsGotDisplayedInAscendingOrder());

	}

	@Test(priority = 15)
	public void verifyShowProductsByLimitingCount() {

		headerOptions.enterProductIntoSearchBoxField(prop.getProperty("existingProductTwo"));
		searchPage = headerOptions.clickOnSearchButton();
		String productLimitOne = "20";
		searchPage.selectOptionInShowDropdownField(productLimitOne);
		Assert.assertTrue(searchPage.getProductsCount() == Integer.parseInt(productLimitOne));
		String productLimitTwo = "25";
		searchPage.selectOptionInShowDropdownField(productLimitTwo);
		Assert.assertTrue(searchPage.getProductsCount() == Integer.parseInt(productLimitTwo));
		String productLimitThree = "50";
		searchPage.selectOptionInShowDropdownField(productLimitThree);
		Assert.assertTrue(searchPage.getProductsCount() == Integer.parseInt(productLimitThree));
		String productLimitFour = "75";
		searchPage.selectOptionInShowDropdownField(productLimitFour);
		Assert.assertTrue(searchPage.getProductsCount() == Integer.parseInt(productLimitFour));
		String productLimitFive = "100";
		searchPage.selectOptionInShowDropdownField(productLimitFive);
		Assert.assertTrue(searchPage.getProductsCount() == Integer.parseInt(productLimitFive));

	}

	@Test(priority = 16)
	public void verifyDisplayingOfSearchFieldAndSearchButtonOnAllPagesOfTheApplication() throws InterruptedException {

		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateToPage(getBaseURL()+prop.getProperty("contactUsPage"));
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateToPage(getBaseURL()+prop.getProperty("registerPageURL"));
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateToPage(getBaseURL()+prop.getProperty("loginPageURL"));
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateToPage(getBaseURL()+prop.getProperty("forgottenPasswordPage"));
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		registerPage = headerOptions.navigateToRegisterPage();
		String emailAddress = CommonUtilities.generateBrandNewEmail();
		accountSuccessPage = registerPage.registeringAnAccount(prop.getProperty("firstName"),prop.getProperty("lastName"),emailAddress,prop.getProperty("telephoneNumber"),prop.getProperty("validPassword"));
		rightColumnOptions = accountSuccessPage.getRightColumnOptions();
		myAccountPage = rightColumnOptions.clickOnMyAccountOptionAfterLogin();
		myAccountPage.clickOnEditYourAccountInformationOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateBackInBrowser(headerOptions.getDriver());
		myAccountPage.clickOnChangeYourPasswordOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateBackInBrowser(headerOptions.getDriver());
		addressBookPage = myAccountPage.clickOnModifyYourAddressBoxEntriesOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		addAddressPage = addressBookPage.clickNewAddressButton();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		addressBookPage = addAddressPage.enterMandatoryFieldsAndAddAddress(prop.getProperty("firstName"),
				prop.getProperty("lastName"), prop.getProperty("textInProductDescription"), prop.getProperty("city"),
				prop.getProperty("postCode"));
		editAddressPage = addressBookPage.clickOnEditButton();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		myAccountPage = editAddressPage.clickOnAccountBreadcrumb();
		myAccountPage.clickOnModifyYourWishListOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		searchPage = headerOptions.enterProductAndClickOnSearchButton(prop.getProperty("existingProduct"));
		productDisplayPage = searchPage.clickOnProductOneName();
		shoppingCartPage = productDisplayPage.clickOnAddToCartButtonAndSelectShoppingCartOption();
		checkoutPage = shoppingCartPage.clickOnCheckoutButton();
		checkoutSuccessPage = checkoutPage.placeOrder();
		refreshAndNavigateToPage(checkoutSuccessPage.getDriver(),getBaseURL()+prop.getProperty("myAccountPage"));
		orderHistoryPage = myAccountPage.clickOnViewYourOrderHistoryOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		orderInformationPage = orderHistoryPage.selectViewOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		productReturnsPage = orderInformationPage.selectReturnOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		productReturnsPage.selectFirstReasonForReturn();
		productReturnsPage.clickOnSubmitButton();
		rightColumnOptions = productReturnsPage.getRightColumnOptions();
		rightColumnOptions.clickOnMyAccountOption();
		myAccountPage.clickOnDownloadsOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateBackInBrowser(headerOptions.getDriver());
		myAccountPage.clickOnRewardPointsOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateBackInBrowser(headerOptions.getDriver());
		productReturnsPage = myAccountPage.clickOnViewYourReturnRequestsOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		//Continue from here
		returnInformationPage = productReturnsPage.clickOnViewOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		returnInformationPage.clickOnAccountBreadCrumb();
		myAccountPage.clickOnYourTransactionOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateBackInBrowser(headerOptions.getDriver());
		myAccountPage.clickOnRecurringPaymentsOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateBackInBrowser(headerOptions.getDriver());
		affiliatePage = myAccountPage.clickOnEditYourAffiliateInformationOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		myAccountPage = affiliatePage.updateAffiliateAccount(prop.getProperty("firstName"));
		myAccountPage.clickOnCustomAffiliateTrackingCodeOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateBackInBrowser(headerOptions.getDriver());
		myAccountPage.clickOnSubscribeOrUnscriberToNewsletterOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateBackInBrowser(headerOptions.getDriver());
		rightColumnOptions = myAccountPage.getRightColumnOptions();
		accountLogoutPage = rightColumnOptions.clickOnLogoutOption();
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateToPage(getBaseURL()+prop.getProperty("aboutUsPage"));
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateToPage(getBaseURL()+prop.getProperty("deliveryInformationPage"));
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateToPage(getBaseURL()+prop.getProperty("privacyPolicyPage"));
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateToPage(getBaseURL()+prop.getProperty("termsAndConditionsPage"));
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateToPage(getBaseURL()+prop.getProperty("brandsPage"));
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		//pending things - sitemap pages links and guest checkout pages
		//Pending items - Footer pages - gift certificate and purchase gift certificate page
		navigateToPage(getBaseURL()+prop.getProperty("affiliateLoginPage"));
		Assert.assertTrue(headerOptions.areSearchBoxFieldAndSearchButtonDisplayed());
		navigateToPage(getBaseURL()+prop.getProperty("specialOffersPage"));
		
	}

}
