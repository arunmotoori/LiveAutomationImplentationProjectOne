package tests;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.Base;
import pages.HeaderOptions;
import utils.CommonUtilities;

public class Register extends Base {

	WebDriver driver;

	@BeforeMethod
	public void setup() {

		driver = openBrowserAndApplicationPageURL();
		headerOptions = new HeaderOptions(driver);
		headerOptions.clickOnMyAccountDropMenu();
		registerPage = headerOptions.selectRegisterOption();

	}

	@Test(priority = 1)
	public void verifyRegisteringAccountUsingMandatoryFields() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerPage.selectPrivacyPolicyField();
		accountSuccessPage = registerPage.clickOnContinueButton();
		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());
		String properDetailsOne = "Your Account Has Been Created!";
		String properDetailsTwo = "Congratulations! Your new account has been successfully created!";
		String properDetailsThree = "You can now take advantage of member privileges to enhance your online shopping experience with us.";
		String properDetailsFour = "If you have ANY questions about the operation of this online shop, please e-mail the store owner.";
		String properDetailsFive = "A confirmation has been sent to the provided e-mail address. If you have not received it within the hour, please contact us.";
		Assert.assertTrue(accountSuccessPage.getContent().contains(properDetailsOne));
		Assert.assertTrue(accountSuccessPage.getContent().contains(properDetailsTwo));
		Assert.assertTrue(accountSuccessPage.getContent().contains(properDetailsThree));
		Assert.assertTrue(accountSuccessPage.getContent().contains(properDetailsFour));
		Assert.assertTrue(accountSuccessPage.getContent().contains(properDetailsFive));
		myAccountPage = accountSuccessPage.clickOnContinueButton();
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());

	}

	@Test(priority = 2)
	public void verifyThankYourConfirmationEmailOnSuccessfulRegistration() throws InterruptedException {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		String emailText = CommonUtilities.generateBrandNewEmail();
		registerPage.enterEmail(emailText);
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerPage.selectPrivacyPolicyField();
		registerPage.clickOnContinueButton();

		String email = emailText;
		String appPasscode = "dbmm vncw rtja ewoo";

		Thread.sleep(2000);

		// Gmail IMAP configuration
		String host = "imap.gmail.com";
		String port = "993";
		String username = email; // Your Gmail address
		String appPassword = appPasscode; // Your app password
		String expectedSubject = "Welcome To TutorialNinja";
		String expectedFromEmail = "tutorialsninja<account-update@tn.in>";
		String expectedBodyContent = "Your account has been successfully created.";

		try {
			// Mail server connection properties
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "imaps");
			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", port);
			properties.put("mail.imap.ssl.enable", "true");

			// Connect to the mail server
			Session emailSession = Session.getDefaultInstance(properties);
			Store store = emailSession.getStore("imaps");
			store.connect(host, username, appPassword); // replace email password with App password

			// Open the inbox folder
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			// Search for unread emails
			Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

			boolean found = false;
			for (int i = messages.length - 1; i >= 0; i--) {

				Message message = messages[i];

				if (message.getSubject().contains(expectedSubject)) {
					found = true;
					Assert.assertEquals(message.getSubject(), expectedSubject);
					Assert.assertEquals(message.getFrom()[0].toString(), expectedFromEmail);
					Assert.assertTrue(CommonUtilities.getTextFromMessage(message).contains(expectedBodyContent));
					break;
				}
			}

			if (!found) {
				System.out.println("No confirmation email found.");
			}

			// Close the store and folder objects
			inbox.close(false);
			store.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 3)
	public void verifyRegistringAccountUsingAllFields() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicyField();
		accountSuccessPage = registerPage.clickOnContinueButton();

		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());

		String properDetailsOne = "Your Account Has Been Created!";
		String properDetailsTwo = "Congratulations! Your new account has been successfully created!";
		String properDetailsThree = "You can now take advantage of member privileges to enhance your online shopping experience with us.";
		String properDetailsFour = "If you have ANY questions about the operation of this online shop, please e-mail the store owner.";
		String properDetailsFive = "A confirmation has been sent to the provided e-mail address. If you have not received it within the hour, please contact us.";

		Assert.assertTrue(accountSuccessPage.getContent().contains(properDetailsOne));
		Assert.assertTrue(accountSuccessPage.getContent().contains(properDetailsTwo));
		Assert.assertTrue(accountSuccessPage.getContent().contains(properDetailsThree));
		Assert.assertTrue(accountSuccessPage.getContent().contains(properDetailsFour));
		Assert.assertTrue(accountSuccessPage.getContent().contains(properDetailsFive));

		myAccountPage = accountSuccessPage.clickOnContinueButton();
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());

	}

	@Test(priority = 4)
	public void verifyWarningMessageOfMandatoryFieldsInRegisterAccountPage() {

		registerPage.clickOnContinueButton();

		String expectedFirstNameWarning = "First Name must be between 1 and 32 characters!";
		String expectedLastNameWarning = "Last Name must be between 1 and 32 characters!";
		String expectedEmailWarning = "E-Mail Address does not appear to be valid!";
		String expectedTelephoneWarning = "Telephone must be between 3 and 32 characters!";
		String expectedPasswordWarning = "Password must be between 4 and 20 characters!";
		String expectedPrivacyPolicyWarning = "Warning: You must agree to the Privacy Policy!";

		Assert.assertEquals(registerPage.getFirstNameWarning(), expectedFirstNameWarning);
		Assert.assertEquals(registerPage.getLastNameWarning(), expectedLastNameWarning);
		Assert.assertEquals(registerPage.getEmailWarning(), expectedEmailWarning);
		Assert.assertEquals(registerPage.getTelephoneWarning(), expectedTelephoneWarning);
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);
		Assert.assertEquals(registerPage.getPageLevelWarning(), expectedPrivacyPolicyWarning);

	}

	@Test(priority = 5)
	public void verifyRegisteringAccountBySubscribingToNewsletter() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicyField();
		accountSuccessPage = registerPage.clickOnContinueButton();
		myAccountPage = accountSuccessPage.clickOnContinueButton();
		newsletterPage = myAccountPage.clickOnSubscribeOrUnscriberToNewsletterOption();
		Assert.assertTrue(newsletterPage.didWeNavigateToNewsletterPage());
		Assert.assertTrue(newsletterPage.isYesNewsletterOptionIsInSelectedState());

	}

	@Test(priority = 6)
	public void verifyRegisteringAccountByNotSubscribingToNewsletter() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerPage.selectNoNewletterOption();
		registerPage.selectPrivacyPolicyField();
		accountSuccessPage = registerPage.clickOnContinueButton();
		myAccountPage = accountSuccessPage.clickOnContinueButton();
		newsletterPage = myAccountPage.clickOnSubscribeOrUnscriberToNewsletterOption();
		Assert.assertTrue(newsletterPage.didWeNavigateToNewsletterPage());
		Assert.assertTrue(newsletterPage.isNoNewsletterOptionIsInSelectedState());

	}

	@Test(priority = 7)
	public void verifyDifferentWaysOfNavigatingToRegisterAccountPage() {

		Assert.assertTrue(registerPage.didWeNavigateToRegisterPage());
		headerOptions = registerPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		loginPage = headerOptions.selectLoginOption();
		registerPage = loginPage.clickOnContinueButton();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterPage());
		headerOptions = registerPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		loginPage = headerOptions.selectLoginOption();
		rightColumnOptions = loginPage.getRightColumnOptions();
		registerPage = rightColumnOptions.clickOnRegisterOption();
		Assert.assertTrue(registerPage.didWeNavigateToRegisterPage());

	}

	@Test(priority = 8)
	public void verifyRegisteringAccountByProvidingMismatchedPasswords() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("mismatchingPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicyField();
		registerPage.clickOnContinueButton();
		String expectedWarning = "Password confirmation does not match password!";
		Assert.assertEquals(registerPage.getPasswordConfirmationWarning(), expectedWarning);

	}

	@Test(priority = 9)
	public void verifyRegisterAccountWithExistingEmailAddress() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(prop.getProperty("existingEmail"));
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicyField();
		registerPage.clickOnContinueButton();
		String expectedWarning = "Warning: E-Mail Address is already registered!";
		Assert.assertEquals(registerPage.getPageLevelWarning(), expectedWarning);

	}

	@Test(priority = 10)
	public void verifyRegisteringAccountUsingInvalidEmail() throws IOException, InterruptedException {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(prop.getProperty("invalidEmailOne"));
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicyField();
		registerPage.clickOnContinueButton();

		if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("edge")) {
			String expectedWarningMessageOne = "Please include an '@' in the email address. 'amotoori' is missing an '@'.";
			Assert.assertEquals(registerPage.getEmailValidationMessage(), expectedWarningMessageOne);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			String expectedWarningMessageOne = "Please enter an email address.";
			Assert.assertEquals(registerPage.getEmailValidationMessage(), expectedWarningMessageOne);
		}

		registerPage.clearEmailField();
		registerPage.enterEmail(prop.getProperty("invalidEmailTwo"));
		registerPage.clickOnContinueButton();

		if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("edge")) {
			String expectedWarningMessageTwo = "Please enter a part following '@'. 'amotoori@' is incomplete.";
			Assert.assertEquals(registerPage.getEmailValidationMessage(), expectedWarningMessageTwo);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			String expectedWarningMessageOne = "Please enter an email address.";
			Assert.assertEquals(registerPage.getEmailValidationMessage(), expectedWarningMessageOne);
		}

		registerPage.clearEmailField();
		registerPage.enterEmail(prop.getProperty("invalidEmailThree"));
		registerPage.clickOnContinueButton();

		String expectedWarningMessageThree = "E-Mail Address does not appear to be valid!";
		Assert.assertEquals(registerPage.getEmailWarning(), expectedWarningMessageThree);

		registerPage.clearEmailField();
		registerPage.enterEmail(prop.getProperty("invalidEmailFour"));
		registerPage.clickOnContinueButton();

		if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("edge")) {
			String expectedWarningMessageFour = "'.' is used at a wrong position in 'gmail.'.";
			Assert.assertEquals(registerPage.getEmailValidationMessage(), expectedWarningMessageFour);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			String expectedWarningMessageOne = "Please enter an email address.";
			Assert.assertEquals(registerPage.getEmailValidationMessage(), expectedWarningMessageOne);
		}

	}

	@Test(priority = 11)
	public void verifyRegisteringAccountUsingInvalidTelephoneNumber() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		registerPage.enterTelephone(prop.getProperty("invalidTelephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerPage.selectPrivacyPolicyField();
		accountSuccessPage = registerPage.clickOnContinueButton();
		String expectedWarningMessage = "Telephone number entered by you is invalid!";
		boolean b = false;
		try {
			if (registerPage.getTelephoneWarning().equals(expectedWarningMessage)) {
				b = true;
			}
		} catch (NoSuchElementException e) {
			b = false;
		}
		Assert.assertTrue(b);
		Assert.assertFalse(accountSuccessPage.didWeNavigateToAccountSuccessPage());

	}

	@Test(priority = 12)
	public void verifyRegisteringAccountUsingKeyboardKeys() {

		actions = clickKeyboradKeyMultipleTimes(getActions(driver), Keys.TAB, 23);
		actions = typeTextUsingActions(actions, prop.getProperty("firstName"));
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.TAB, 1);
		actions = typeTextUsingActions(actions, prop.getProperty("lastName"));
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.TAB, 1);
		actions = typeTextUsingActions(actions, CommonUtilities.generateBrandNewEmail());
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.TAB, 1);
		actions = typeTextUsingActions(actions, prop.getProperty("telephoneNumber"));
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.TAB, 1);
		actions = typeTextUsingActions(actions, prop.getProperty("validPassword"));
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.TAB, 1);
		actions = typeTextUsingActions(actions, prop.getProperty("validPassword"));
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.TAB, 1);
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.ARROW_LEFT, 1);
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.TAB, 2);
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.SPACE, 1);
		actions = clickKeyboradKeyMultipleTimes(actions, Keys.TAB, 1);
		clickKeyboradKeyMultipleTimes(actions, Keys.ENTER, 1);

		rightColumnOptions = registerPage.getRightColumnOptions();
		Assert.assertTrue(rightColumnOptions.didWeGetLoggedIn());
		accountSuccessPage = rightColumnOptions.getAccountSuccessPage();
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());

	}

	@Test(priority = 13)
	public void verifyRegisterAccountPagePlaceholders() {

		Assert.assertEquals(registerPage.getFirstNameFieldPlaceholderText(), "First Name");
		Assert.assertEquals(registerPage.getLastNameFieldPlaceholderText(), "Last Name");
		Assert.assertEquals(registerPage.getEmailFieldPlaceholderText(), "E-Mail");
		Assert.assertEquals(registerPage.getTelephoneFieldPlaceholderText(), "Telephone");
		Assert.assertEquals(registerPage.getPasswordFieldPlaceholderText(), "Password");
		Assert.assertEquals(registerPage.getPasswordConfirmFieldPlaceholderText(), "Password Confirm");

	}

	@Test(priority = 14)
	public void verifyMandatoryFieldsInRegisterAccountPage() {

		String expectedContent = "\"* \"";
		String expectedColor = "rgb(255, 0, 0)";
		String fistNameLabelContent = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');",
				registerPage.getFirstNameFieldLabelElement());
		Assert.assertEquals(fistNameLabelContent, expectedContent);
		String fistNameLabelColor = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')",
				registerPage.getFirstNameFieldLabelElement());
		Assert.assertEquals(fistNameLabelColor, expectedColor);
		String lastNameLabelContent = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');",
				registerPage.getLastNameFieldLabelElement());
		Assert.assertEquals(lastNameLabelContent, expectedContent);
		String lastNameLabelColor = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')",
				registerPage.getLastNameFieldLabelElement());
		Assert.assertEquals(lastNameLabelColor, expectedColor);
		String emailLabelContent = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');",
				registerPage.getEmailFieldLabelElement());
		Assert.assertEquals(emailLabelContent, expectedContent);
		String emailLabelColor = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')",
				registerPage.getEmailFieldLabelElement());
		Assert.assertEquals(emailLabelColor, expectedColor);
		String telephoneLabelContent = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');",
				registerPage.getTelephoneFieldLabelElement());
		Assert.assertEquals(telephoneLabelContent, expectedContent);
		String telephoneLabelColor = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')",
				registerPage.getTelephoneFieldLabelElement());
		Assert.assertEquals(telephoneLabelColor, expectedColor);
		String passwordLabelContent = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');",
				registerPage.getPasswordFieldLabelElement());
		Assert.assertEquals(passwordLabelContent, expectedContent);
		String passwordLabelColor = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')",
				registerPage.getPasswordFieldLabelElement());
		Assert.assertEquals(passwordLabelColor, expectedColor);
		String passwordConfirmLabelContent = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');",
				registerPage.getPasswordConfirmFieldLabelElement());
		Assert.assertEquals(passwordConfirmLabelContent, expectedContent);
		String passwordConfirmLabelColor = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')",
				registerPage.getPasswordConfirmFieldLabelElement());
		Assert.assertEquals(passwordConfirmLabelColor, expectedColor);
		String privacyPolicyLabelContent = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');",
				registerPage.getPrivacyPolicyFieldLabelElement());
		Assert.assertEquals(privacyPolicyLabelContent, expectedContent);
		String privacyPolicyLabelColor = (String) ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')",
				registerPage.getPrivacyPolicyFieldLabelElement());
		Assert.assertEquals(privacyPolicyLabelColor, expectedColor);

	}

	@Test(priority = 15, enabled = false)
	public void verifyDataBaseTestingForRegisterAccount() {

		String enteredFirstNameData = prop.getProperty("firstName");
		registerPage.enterFirstName(enteredFirstNameData);
		String enteredLastNameData = prop.getProperty("lastName");
		registerPage.enterLastName(enteredLastNameData);
		String enteredEmailData = CommonUtilities.generateBrandNewEmail();
		registerPage.enterEmail(enteredEmailData);
		String enteredPasswordData = prop.getProperty("validPassword");
		registerPage.enterPassword(enteredPasswordData);
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicyField();
		registerPage.clickOnContinueButton();

		// Database credentials
		String jdbcURL = "jdbc:mysql://localhost:3306/opencart_db";
		String dbUser = "root";
		String dbPassword = "";

		// SQL query
		String sqlQuery = "SELECT * FROM oc_customer";

		// JDBC objects
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String firstName = null;
		String lastName = null;
		String email = null;
		int newsletter = 0;

		try {
			// Step 1: Register JDBC driver (optional in newer versions)

			// Step 2: Open a connection
			connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
			System.out.println("Connected to the database!");

			// Step 3: Create a statement
			statement = connection.createStatement();

			// Step 4: Execute the query
			resultSet = statement.executeQuery(sqlQuery);

			// Step 5: Process the ResultSet
			while (resultSet.next()) {
				firstName = resultSet.getString("firstname"); // Replace with your column name
				lastName = resultSet.getString("lastname"); // Replace with your column name
				email = resultSet.getString("email");
				newsletter = resultSet.getInt("newsletter");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Step 6: Close resources
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		Assert.assertEquals(firstName, enteredFirstNameData);
		Assert.assertEquals(lastName, enteredLastNameData);
		Assert.assertEquals(email, enteredEmailData);
		Assert.assertEquals(newsletter, 1);

	}

	@Test(priority = 16)
	public void verifyRegisteringAccountByEnteringOnlySpaces() {

		registerPage.enterFirstName("     ");
		registerPage.enterLastName("     ");
		registerPage.enterEmail("     ");
		registerPage.enterTelephone("     ");
		registerPage.enterPassword("     ");
		registerPage.enterConfirmationPassword("     ");
		registerPage.selectPrivacyPolicyField();
		registerPage.clickOnContinueButton();

		if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("edge")) {
			Assert.assertEquals(registerPage.getFirstNameWarning(), "First Name must be between 1 and 32 characters!");
			Assert.assertEquals(registerPage.getLastNameWarning(), "Last Name must be between 1 and 32 characters!");
			Assert.assertEquals(registerPage.getEmailWarning(), "E-Mail Address does not appear to be valid!");
			Assert.assertEquals(registerPage.getTelephoneWarning(), "Telephone does not appear to be valid!");
		} else if (browserName.equalsIgnoreCase("firefox")) {
			Assert.assertEquals(registerPage.getEmailValidationMessage(), "Please enter an email address.");
		}

	}

	@Test(priority = 17, dataProvider = "passwordSupplier")
	public void verifyRegisteringAccountUsingPasswordsWhichAreNotFollowingPasswordComplexityStandards(
			String passwordText) {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(passwordText);
		registerPage.enterConfirmationPassword(passwordText);
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicyField();
		registerPage.clickOnContinueButton();

		String expectedWarning = "Enter password which follows Password Complexity Standard!";
		boolean b = false;
		try {
			String actualWarning = registerPage.getPasswordWarning();
			if (actualWarning.equals(expectedWarning)) {
				b = true;
			}
		} catch (NoSuchElementException e) {
			b = false;
		}

		Assert.assertTrue(b);

	}

	@DataProvider(name = "passwordSupplier")
	public Object[][] supplyPasswords() {

		Object[][] data = { { "12345" }, { "abcdefghi" }, { "abcd1234" }, { "abcd123$" }, { "ABCD456#" } };
		return data;

	}

	@Test(priority = 18)
	public void verifyHeightWidthNumberOfCharacters() throws IOException {

		String expectedHeight = "34px";
		String expectedWidth = "701.25px";
		// First Name Field check
		Assert.assertEquals(registerPage.getFirstNameCSSValue("height"), expectedHeight);
		Assert.assertEquals(registerPage.getFirstNameCSSValue("width"), expectedWidth);
		String exptectedFirstNameWarning = "First Name must be between 1 and 32 characters!";
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getFirstNameWarning(), exptectedFirstNameWarning);
		registerPage.enterFirstName("a");
		registerPage.clickOnContinueButton();
		boolean firstNameWarningStatus = false;
		try {
			firstNameWarningStatus = registerPage.isFirstNameWarningMessageDisplayed();
		} catch (NoSuchElementException e) {
			firstNameWarningStatus = false;
		}
		Assert.assertFalse(firstNameWarningStatus);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("abcdeabcdeabcdeabcdeabcdeabcdeab");
		registerPage.clickOnContinueButton();
		firstNameWarningStatus = false;
		try {
			firstNameWarningStatus = registerPage.isFirstNameWarningMessageDisplayed();
		} catch (NoSuchElementException e) {
			firstNameWarningStatus = false;
		}
		Assert.assertFalse(firstNameWarningStatus);
		registerPage.clearFirstNameField();
		registerPage.enterFirstName("abcdeabcdeabcdeabcdeabcdeabcdeabc");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getFirstNameWarning(), exptectedFirstNameWarning);
		// Last Name Field check
		Assert.assertEquals(registerPage.getLastNameCSSValue("height"), expectedHeight);
		Assert.assertEquals(registerPage.getLastNameCSSValue("width"), expectedWidth);
		String exptectedLastNameWarning = "Last Name must be between 1 and 32 characters!";
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getLastNameWarning(), exptectedLastNameWarning);
		registerPage.enterLastName("a");
		registerPage.clickOnContinueButton();
		boolean lastNameWarningStatus = false;
		try {
			lastNameWarningStatus = registerPage.isLastNameWarningMessageDisplayed();
		} catch (NoSuchElementException e) {
			lastNameWarningStatus = false;
		}
		Assert.assertFalse(lastNameWarningStatus);
		registerPage.clearLastNameField();
		registerPage.enterLastName("abcdeabcdeabcdeabcdeabcdeabcdeab");
		registerPage.clickOnContinueButton();
		lastNameWarningStatus = false;
		try {
			lastNameWarningStatus = registerPage.isLastNameWarningMessageDisplayed();
		} catch (NoSuchElementException e) {
			lastNameWarningStatus = false;
		}
		Assert.assertFalse(lastNameWarningStatus);
		registerPage.clearLastNameField();
		registerPage.enterLastName("abcdeabcdeabcdeabcdeabcdeabcdeabc");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getLastNameWarning(), exptectedLastNameWarning);
		// Email Field check
		Assert.assertEquals(registerPage.getEmailCSSValue("height"), expectedHeight);
		Assert.assertEquals(registerPage.getEmailCSSValue("width"), expectedWidth);
		registerPage.enterEmail("adfdsfasdfadfdsssssafasdfasdfasdfadsfasdf@email.com");
		registerPage.clickOnContinueButton();
		boolean emailWarningStatus = false;
		try {
			emailWarningStatus = registerPage.isEmailWarningMessageDisplayed();
		} catch (NoSuchElementException e) {
			emailWarningStatus = false;
		}
		Assert.assertFalse(emailWarningStatus);
		// Telephone Field check
		Assert.assertEquals(registerPage.getTelephoneCSSValue("height"), expectedHeight);
		Assert.assertEquals(registerPage.getTelephoneCSSValue("width"), expectedWidth);
		String expectedTelephoneWarning = "Telephone must be between 3 and 32 characters!";
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTelephoneWarning(), expectedTelephoneWarning);
		registerPage.enterTelephone("1");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTelephoneWarning(), expectedTelephoneWarning);
		registerPage.clearTelephoneField();
		registerPage.enterTelephone("12");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTelephoneWarning(), expectedTelephoneWarning);
		registerPage.clearTelephoneField();
		registerPage.enterTelephone("123");
		registerPage.clickOnContinueButton();
		boolean telephoneWarningStatus = false;
		try {
			telephoneWarningStatus = registerPage.isTelephoneWarningMessageDisplayed();
		} catch (NoSuchElementException e) {
			telephoneWarningStatus = false;
		}
		Assert.assertFalse(telephoneWarningStatus);
		registerPage.clearTelephoneField();
		registerPage.enterTelephone("12345678901234567890123456789012");
		registerPage.clickOnContinueButton();
		telephoneWarningStatus = false;
		try {
			telephoneWarningStatus = registerPage.isTelephoneWarningMessageDisplayed();
		} catch (NoSuchElementException e) {
			telephoneWarningStatus = false;
		}
		Assert.assertFalse(telephoneWarningStatus);
		registerPage.clearTelephoneField();
		registerPage.enterTelephone("123456789012345678901234567890123");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getTelephoneWarning(), expectedTelephoneWarning);
		// Password Field check
		Assert.assertEquals(registerPage.getPasswordCSSValue("height"), expectedHeight);
		Assert.assertEquals(registerPage.getPasswordCSSValue("width"), expectedWidth);
		String expectedPasswordWarning = "Password must be between 4 and 20 characters!";
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);
		registerPage.clearPasswordField();
		registerPage.enterPassword("1");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);
		registerPage.clearPasswordField();
		registerPage.enterPassword("12");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);
		registerPage.clearPasswordField();
		registerPage.enterPassword("123");
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordWarning(), expectedPasswordWarning);
		registerPage.clearPasswordField();
		registerPage.enterPassword("1234");
		registerPage.clickOnContinueButton();
		boolean passwordWarningStatus = false;
		try {
			passwordWarningStatus = registerPage.isPasswordWarningMessageDisplayed();
		} catch (NoSuchElementException e) {
			passwordWarningStatus = false;
		}
		Assert.assertFalse(passwordWarningStatus);
		registerPage.clearPasswordField();
		registerPage.enterPassword("12345678901234567890");
		registerPage.clickOnContinueButton();
		passwordWarningStatus = false;
		try {
			passwordWarningStatus = registerPage.isPasswordWarningMessageDisplayed();
		} catch (NoSuchElementException e) {
			passwordWarningStatus = false;
		}
		Assert.assertFalse(passwordWarningStatus);
		registerPage.clearPasswordField();
		registerPage.enterPassword("123456789012345678901");
		registerPage.clickOnContinueButton();
		passwordWarningStatus = false;
		try {
			passwordWarningStatus = registerPage.isPasswordWarningMessageDisplayed();
		} catch (NoSuchElementException e) {
			passwordWarningStatus = false;
		}
		Assert.assertTrue(passwordWarningStatus);
		// Password Confirm Field check
		Assert.assertEquals(registerPage.getPasswordConfirmCSSValue("height"), expectedHeight);
		Assert.assertEquals(registerPage.getPasswordConfirmCSSValue("width"), expectedWidth);
		// Continue Button
		Assert.assertEquals(registerPage.getContinueButtonCSSValue("color"), "rgba(255, 255, 255, 1)");
		Assert.assertEquals(registerPage.getContinueButtonCSSValue("background-color"), "rgba(34, 154, 200, 1)");
		Assert.assertEquals(registerPage.getContinueButtonCSSValue("font-size"), "12px");
		headerOptions = registerPage.getHeaderOptions();
		headerOptions.clickOnMyAccountDropMenu();
		headerOptions.selectRegisterOption();
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcScreenshot = ts.getScreenshotAs(OutputType.FILE);
		try {
			FileHandler.copy(srcScreenshot,
					new File(System.getProperty("user.dir") + "\\Screenshots\\AcutalRAPageAligment.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
				System.getProperty("user.dir") + "\\Screenshots\\AcutalRAPageAligment.png",
				System.getProperty("user.dir") + "\\Screenshots\\ExpectedRAPageAligment.png"));

	}

	@Test(priority = 19)
	public void verifyRegisterAccountUsingLeadingAndTrailingSpaces() {

		SoftAssert softAssert = new SoftAssert();
		registerPage.enterFirstName("     " + prop.getProperty("firstName") + "     ");
		registerPage.enterLastName("     " + prop.getProperty("lastName") + "     ");
		String emailWithTimeStamp = CommonUtilities.generateBrandNewEmail();
		registerPage.enterEmail("     " + emailWithTimeStamp + "     ");
		registerPage.enterTelephone("     " + prop.getProperty("telephoneNumber") + "     ");
		registerPage.enterPassword("     " + prop.getProperty("validPassword") + "     ");
		registerPage.enterConfirmationPassword("     " + prop.getProperty("validPassword") + "     ");
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicyField();
		accountSuccessPage = registerPage.clickOnContinueButton();

		if (browserName.equalsIgnoreCase("chrome") || browserName.equalsIgnoreCase("edge")) {
			myAccountPage = accountSuccessPage.clickOnContinueButton();
			myAccountInformationPage = myAccountPage.clickOnEditYourAccountInformationOption();
			softAssert.assertEquals(myAccountInformationPage.getFirstNameDomAttribute("value"),
					prop.getProperty("firstName"));
			softAssert.assertEquals(myAccountInformationPage.getLastNameDomAttribute("value"),
					prop.getProperty("lastName"));
			softAssert.assertEquals(myAccountInformationPage.getEmailDomAttribute("value"), emailWithTimeStamp);
			softAssert.assertEquals(myAccountInformationPage.getTelephoneDomAttribute("value"),
					prop.getProperty("telephoneNumber"));
			softAssert.assertAll();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			String expectedWarningMessageOne = "Please enter an email address.";
			Assert.assertEquals(myAccountInformationPage.getEmailDomProperty("validationMessage"),
					expectedWarningMessageOne);
		}

	}

	@Test(priority = 20)
	public void verifyRegisterAccountPrivacyPolicyField() {

		Assert.assertFalse(registerPage.isPrivacyPolicyFieldSelected());

	}

	@Test(priority = 21)
	public void verifyRegisteringAccountWithoutSelectingPrivacyPolicyCheckboxField() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPageLevelWarning(), "Warning: You must agree to the Privacy Policy!");

	}

	@Test(priority = 22)
	public void verifyRegisteringAccountPasswordFieldsForSecurity() {

		Assert.assertEquals(registerPage.getPasswordFieldDomAttribute("type"), "password");
		Assert.assertEquals(registerPage.getPasswordConfirmFieldDomAttribute("type"), "password");

	}

	@Test(priority = 23)
	public void verifyRegisterAccountPageNavigations() {

		headerOptions = registerPage.getHeaderOptions();
		contactUsPage = headerOptions.selectPhoneIconOption();
		Assert.assertTrue(getPageTitle(contactUsPage.getDriver()).equals("Contact Us"));
		navigateBackInBrowser(contactUsPage.getDriver());

		loginPage = headerOptions.selectHeartIconOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = headerOptions.selectWishListOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		shoppingCartPage = headerOptions.selectShoppingCartIcon();
		Assert.assertEquals(getPageTitle(shoppingCartPage.getDriver()), "Shopping Cart");
		navigateBackInBrowser(shoppingCartPage.getDriver());

		shoppingCartPage = headerOptions.selectShoppingCartOption();
		Assert.assertEquals(getPageTitle(shoppingCartPage.getDriver()), "Shopping Cart");
		navigateBackInBrowser(shoppingCartPage.getDriver());

		shoppingCartPage = headerOptions.selectCheckoutIcon();
		Assert.assertEquals(getPageTitle(shoppingCartPage.getDriver()), "Shopping Cart");
		navigateBackInBrowser(shoppingCartPage.getDriver());

		shoppingCartPage = headerOptions.selectCheckoutOption();
		Assert.assertEquals(getPageTitle(shoppingCartPage.getDriver()), "Shopping Cart");
		navigateBackInBrowser(shoppingCartPage.getDriver());

		homePage = headerOptions.selectLogo();
		Assert.assertEquals(getPageTitle(homePage.getDriver()), "Your Store");
		navigateBackInBrowser(homePage.getDriver());

		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertEquals(getPageTitle(searchPage.getDriver()), "Search");
		navigateBackInBrowser(searchPage.getDriver());

		homePage = headerOptions.selectHomeBreadcrumbOption();
		Assert.assertEquals(getPageTitle(homePage.getDriver()), "Your Store");
		navigateBackInBrowser(homePage.getDriver());

		loginPage = headerOptions.selectAccountBreadcrumbOptionWithoutLogin();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		registerPage = registerPage.selectRegisterBreadcrumbOption();
		Assert.assertEquals(getPageTitle(registerPage.getDriver()), "Register Account");

		loginPage = registerPage.selectLoginPageOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		rightColumnOptions = loginPage.getRightColumnOptions();
		loginPage = rightColumnOptions.clickOnLoginOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		registerPage = rightColumnOptions.clickOnRegisterOption();
		Assert.assertEquals(getPageTitle(registerPage.getDriver()), "Register Account");

		forgottenPasswordPage = rightColumnOptions.clickOnForgottenPasswordOption();
		Assert.assertEquals(getPageTitle(forgottenPasswordPage.getDriver()), "Forgot Your Password?");
		navigateBackInBrowser(forgottenPasswordPage.getDriver());

		loginPage = rightColumnOptions.clickOnMyAccountOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = rightColumnOptions.clickOnAddressBookOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = rightColumnOptions.clickOnWishListOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = rightColumnOptions.clickOnOrderHistoryOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = rightColumnOptions.clickOnDownloadsOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = rightColumnOptions.clickOnRecurringPaymentsOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = rightColumnOptions.clickOnRewarPointsOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = rightColumnOptions.clickOnReturnsOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = rightColumnOptions.clickOnTransactionsOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = rightColumnOptions.clickOnNewsletterOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		footerOptions = loginPage.getFoooterOptions();
		aboutUsPage = footerOptions.selectAboutUsOption();
		Assert.assertEquals(getPageTitle(aboutUsPage.getDriver()), "About Us");
		navigateBackInBrowser(aboutUsPage.getDriver());

		deliveryInformationPage = footerOptions.selectDeliveryInformationOption();
		Assert.assertEquals(getPageTitle(deliveryInformationPage.getDriver()), "Delivery Information");
		navigateBackInBrowser(deliveryInformationPage.getDriver());

		privacyPolicyPage = footerOptions.selectPrivacyPolicyOption();
		Assert.assertEquals(getPageTitle(privacyPolicyPage.getDriver()), "Privacy Policy");
		navigateBackInBrowser(privacyPolicyPage.getDriver());

		termsAndConditionsPage = footerOptions.selectTermsAndConditionsOption();
		Assert.assertEquals(getPageTitle(termsAndConditionsPage.getDriver()), "Terms & Conditions");
		navigateBackInBrowser(termsAndConditionsPage.getDriver());

		contactUsPage = footerOptions.selectContactUsOption();
		Assert.assertEquals(getPageTitle(contactUsPage.getDriver()), "Contact Us");
		navigateBackInBrowser(contactUsPage.getDriver());

		productReturnsPage = footerOptions.selectReturnsOption();
		Assert.assertEquals(getPageTitle(productReturnsPage.getDriver()), "Product Returns");
		navigateBackInBrowser(productReturnsPage.getDriver());

		siteMapPage = footerOptions.selectSiteMapOption();
		Assert.assertEquals(getPageTitle(siteMapPage.getDriver()), "Site Map");
		navigateBackInBrowser(siteMapPage.getDriver());

		brandPage = footerOptions.selectBrandsOption();
		Assert.assertEquals(getPageTitle(brandPage.getDriver()), "Find Your Favorite Brand");
		navigateBackInBrowser(brandPage.getDriver());

		giftCertificatePage = footerOptions.selectGiftCertificatesOption();
		Assert.assertEquals(getPageTitle(giftCertificatePage.getDriver()), "Purchase a Gift Certificate");
		navigateBackInBrowser(giftCertificatePage.getDriver());

		loginPage = footerOptions.selectAffiliateOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Affiliate Program");
		navigateBackInBrowser(loginPage.getDriver());

		specialOffersPage = footerOptions.selectSpecialsOption();
		Assert.assertEquals(getPageTitle(specialOffersPage.getDriver()), "Special Offers");
		navigateBackInBrowser(specialOffersPage.getDriver());

		loginPage = footerOptions.selectMyAccountOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = footerOptions.selectOrderHistoryOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = footerOptions.selectWishListOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

		loginPage = footerOptions.selectNewsletterOption();
		Assert.assertEquals(getPageTitle(loginPage.getDriver()), "Account Login");
		navigateBackInBrowser(loginPage.getDriver());

	}

	@Test(priority = 24)
	public void verifyRegisteringAccountWithoutEnteringConfirmationPassword() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicyField();
		registerPage.clickOnContinueButton();
		Assert.assertEquals(registerPage.getPasswordConfirmationWarning(),
				"Password confirmation does not match password!");

	}

	@Test(priority = 25)
	public void verifyRegisterAccountPageBreadcrumbURLTitleHeading() {

		Assert.assertEquals(getPageTitle(registerPage.getDriver()), "Register Account");
		Assert.assertEquals(getPageURL(registerPage.getDriver()), getBaseURL()+prop.getProperty("registerPageURL"));
		Assert.assertTrue(registerPage.didWeNavigateToRegisterPage());
		Assert.assertEquals(registerPage.getPageHeading(), "Register Account");

	}

	@Test(priority = 26)
	public void verifyRegisterAccountUI() throws IOException {

		if (browserName.equalsIgnoreCase("chrome")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualRAPageUI.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualRAPageUI.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedRAPageUI.png"));
		} else if (browserName.equalsIgnoreCase("firefox")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualFirefoxRAPageUI.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualFirefoxRAPageUI.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedFirefoxRAPageUI.png"));
		} else if (browserName.equalsIgnoreCase("edge")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualEdgeRAPageUI.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualEdgeRAPageUI.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedEdgeRAPageUI.png"));
		}

	}

	@Test(priority = 27)
	public void verifyRegisterAccountInAllEnvironments() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastName(prop.getProperty("lastName"));
		registerPage.enterEmail(CommonUtilities.generateBrandNewEmail());
		registerPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsletterOption();
		registerPage.selectPrivacyPolicyField();
		accountSuccessPage = registerPage.clickOnContinueButton();
		Assert.assertTrue(accountSuccessPage.isUserLoggedIn());
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());

	}

}
