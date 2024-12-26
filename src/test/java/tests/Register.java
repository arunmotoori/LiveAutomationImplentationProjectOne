package tests;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.AccountSuccessPage;
import pages.HeaderOptions;
import pages.MyAccountPage;
import pages.NewsletterPage;
import pages.RegisterPage;
import utils.CommonUtilities;

public class Register {

	WebDriver driver;
	String browserName;
	Properties prop;

	HeaderOptions headerOptions;
	RegisterPage registerPage;
	AccountSuccessPage accountSuccessPage;
	MyAccountPage myAccountPage;
	NewsletterPage newsletterPage;

	@AfterMethod
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@BeforeMethod
	public void setup() {

		prop = CommonUtilities.loadPropertiesFile();
		browserName = prop.getProperty("browserName");

		if (browserName.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (browserName.equals("edge")) {
			driver = new EdgeDriver();
		} else if (browserName.equals("internetexplorer")) {
			driver = new InternetExplorerDriver();
		} else if (browserName.equals("safari")) {
			driver = new SafariDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get(prop.getProperty("appURL"));

		headerOptions = new HeaderOptions(driver);
		headerOptions.clickOnMyAccountDropMenu();
		registerPage = headerOptions.selectRegisterOption();

	}

	@Test(priority = 1)
	public void verifyRegisteringAccountUsingMandatoryFields() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastNameField(prop.getProperty("lastName"));
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
		registerPage.enterLastNameField(prop.getProperty("lastName"));
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
		registerPage.enterLastNameField(prop.getProperty("lastName"));
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
		Assert.assertEquals(registerPage.getPrivacyPolicyFieldWarning(), expectedPrivacyPolicyWarning);

	}

	@Test(priority = 5)
	public void verifyRegisteringAccountBySubscribingToNewsletter() {

		registerPage.enterFirstName(prop.getProperty("firstName"));
		registerPage.enterLastNameField(prop.getProperty("lastName"));
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

		driver.findElement(By.id("input-firstname")).sendKeys(prop.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(prop.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(CommonUtilities.generateBrandNewEmail());
		driver.findElement(By.id("input-telephone")).sendKeys(prop.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.id("input-confirm")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='0']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		driver.findElement(By.xpath("//a[@class='btn btn-primary'][text()='Continue']")).click();

		driver.findElement(By.linkText("Subscribe / unsubscribe to newsletter")).click();

		Assert.assertTrue(
				driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Newsletter']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//input[@name='newsletter'][@value='0']")).isSelected());

	}

	@Test(priority = 7)
	public void verifyDifferentWaysOfNavigatingToRegisterAccountPage() {

		Assert.assertTrue(
				driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Register']")).isDisplayed());

		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.xpath("//a[@class='btn btn-primary'][text()='Continue']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Register']")).isDisplayed());

		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.xpath("//aside[@id='column-right']//a[text()='Register']")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Register']")).isDisplayed());

	}

	@Test(priority = 8)
	public void verifyRegisteringAccountByProvidingMismatchedPasswords() {

		driver.findElement(By.id("input-firstname")).sendKeys(prop.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(prop.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(CommonUtilities.generateBrandNewEmail());
		driver.findElement(By.id("input-telephone")).sendKeys(prop.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.id("input-confirm")).sendKeys(prop.getProperty("mismatchingPassword"));
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String expectedWarning = "Password confirmation does not match password!";
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-confirm']/following-sibling::div")).getText(),
				expectedWarning);

	}

	@Test(priority = 9)
	public void verifyRegisterAccountWithExistingEmailAddress() {

		driver.findElement(By.id("input-firstname")).sendKeys(prop.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(prop.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(prop.getProperty("existingEmail"));
		driver.findElement(By.id("input-telephone")).sendKeys(prop.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.id("input-confirm")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String expectedWarning = "Warning: E-Mail Address is already registered!";
		Assert.assertEquals(
				driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).getText(),
				expectedWarning);

	}

	@Test(priority = 10)
	public void verifyRegisteringAccountUsingInvalidEmail() throws IOException, InterruptedException {

		driver.findElement(By.id("input-firstname")).sendKeys(prop.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(prop.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(prop.getProperty("invalidEmailOne"));
		driver.findElement(By.id("input-telephone")).sendKeys(prop.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.id("input-confirm")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		if (browserName.equals("chrome") || browserName.equals("edge")) {
			String expectedWarningMessageOne = "Please include an '@' in the email address. 'amotoori' is missing an '@'.";
			String actualWarningMessageOne = driver.findElement(By.id("input-email"))
					.getDomProperty("validationMessage");
			Assert.assertEquals(actualWarningMessageOne, expectedWarningMessageOne);
		} else if (browserName.equals("firefox")) {
			String expectedWarningMessageOne = "Please enter an email address.";
			String actualWarningMessageOne = driver.findElement(By.id("input-email"))
					.getDomProperty("validationMessage");
			Assert.assertEquals(actualWarningMessageOne, expectedWarningMessageOne);
		}

		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys(prop.getProperty("invalidEmailTwo"));
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		if (browserName.equals("chrome") || browserName.equals("edge")) {
			String expectedWarningMessageTwo = "Please enter a part following '@'. 'amotoori@' is incomplete.";
			String actualWarningMessageTwo = driver.findElement(By.id("input-email"))
					.getDomProperty("validationMessage");
			Assert.assertEquals(actualWarningMessageTwo, expectedWarningMessageTwo);
		} else if (browserName.equals("firefox")) {
			String expectedWarningMessageOne = "Please enter an email address.";
			String actualWarningMessageOne = driver.findElement(By.id("input-email"))
					.getDomProperty("validationMessage");
			Assert.assertEquals(actualWarningMessageOne, expectedWarningMessageOne);
		}

		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys(prop.getProperty("invalidEmailThree"));
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String expectedWarningMessageThree = "E-Mail Address does not appear to be valid!";
		String actualWarningMessageThree = driver
				.findElement(By.xpath("//input[@id='input-email']/following-sibling::div")).getText();
		Assert.assertEquals(actualWarningMessageThree, expectedWarningMessageThree);

		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys(prop.getProperty("invalidEmailFour"));
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		if (browserName.equals("chrome") || browserName.equals("edge")) {
			String expectedWarningMessageFour = "'.' is used at a wrong position in 'gmail.'.";
			String actualWarningMessageFour = driver.findElement(By.id("input-email"))
					.getDomProperty("validationMessage");
			Assert.assertEquals(actualWarningMessageFour, expectedWarningMessageFour);
		} else if (browserName.equals("firefox")) {
			String expectedWarningMessageOne = "Please enter an email address.";
			String actualWarningMessageOne = driver.findElement(By.id("input-email"))
					.getDomProperty("validationMessage");
			Assert.assertEquals(actualWarningMessageOne, expectedWarningMessageOne);
		}

	}

	@Test(priority = 11)
	public void verifyRegisteringAccountUsingInvalidTelephoneNumber() {

		driver.findElement(By.id("input-firstname")).sendKeys(prop.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(prop.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(CommonUtilities.generateBrandNewEmail());
		driver.findElement(By.id("input-telephone")).sendKeys(prop.getProperty("invalidTelephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.id("input-confirm")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String expectedWarningMessage = "Telephone number entered by you is invalid!";
		boolean b = false;
		try {
			String actualWarningMessage = driver
					.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).getText();
			if (actualWarningMessage.equals(expectedWarningMessage)) {
				b = true;
			}
		} catch (NoSuchElementException e) {
			b = false;
		}

		Assert.assertTrue(b);

		Assert.assertFalse(
				driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Success']")).isDisplayed());

	}

	@Test(priority = 12)
	public void verifyRegisteringAccountUsingKeyboardKeys() {

		Actions actions = new Actions(driver);

		for (int i = 1; i <= 23; i++) {
			actions.sendKeys(Keys.TAB).perform();
		}

		actions.sendKeys(prop.getProperty("firstName")).sendKeys(Keys.TAB).sendKeys(prop.getProperty("lastName"))
				.sendKeys(Keys.TAB).sendKeys(CommonUtilities.generateBrandNewEmail()).sendKeys(Keys.TAB)
				.sendKeys(prop.getProperty("telephoneNumber")).sendKeys(Keys.TAB)
				.sendKeys(prop.getProperty("validPassword")).sendKeys(Keys.TAB)
				.sendKeys(prop.getProperty("validPassword")).sendKeys(Keys.TAB).sendKeys(Keys.ARROW_LEFT)
				.sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.SPACE).sendKeys(Keys.TAB).sendKeys(Keys.ENTER)
				.build().perform();

		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Logout']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Success']")).isDisplayed());

	}

	@Test(priority = 13)
	public void verifyRegisterAccountPagePlaceholders() {

		String expectedFirstNamePlaceHolder = "First Name";
		String expectedLastNamePlaceHolder = "Last Name";
		String expectedEmailPlaceHolder = "E-Mail";
		String expectedTelephonePlaceHolder = "Telephone";
		String expectedPasswordPlaceHolder = "Password";
		String expectedPasswordConfirmPlaceHolder = "Password Confirm";

		Assert.assertEquals(driver.findElement(By.id("input-firstname")).getDomAttribute("placeholder"),
				expectedFirstNamePlaceHolder);
		Assert.assertEquals(driver.findElement(By.id("input-lastname")).getDomAttribute("placeholder"),
				expectedLastNamePlaceHolder);
		Assert.assertEquals(driver.findElement(By.id("input-email")).getDomAttribute("placeholder"),
				expectedEmailPlaceHolder);
		Assert.assertEquals(driver.findElement(By.id("input-telephone")).getDomAttribute("placeholder"),
				expectedTelephonePlaceHolder);
		Assert.assertEquals(driver.findElement(By.id("input-password")).getDomAttribute("placeholder"),
				expectedPasswordPlaceHolder);
		Assert.assertEquals(driver.findElement(By.id("input-confirm")).getDomAttribute("placeholder"),
				expectedPasswordConfirmPlaceHolder);

	}

	@Test(priority = 14)
	public void verifyMandatoryFieldsInRegisterAccountPage() {

		String expectedContent = "\"* \"";
		String expectedColor = "rgb(255, 0, 0)";

		WebElement firstNameLabel = driver.findElement(By.cssSelector("label[for='input-firstname']"));
		WebElement lastNameLabel = driver.findElement(By.cssSelector("label[for='input-lastname']"));
		WebElement emailLabel = driver.findElement(By.cssSelector("label[for='input-email']"));
		WebElement telephoneLabel = driver.findElement(By.cssSelector("label[for='input-telephone']"));
		WebElement passwordLabel = driver.findElement(By.cssSelector("label[for='input-password']"));
		WebElement passwordConfirmLabel = driver.findElement(By.cssSelector("label[for='input-confirm']"));
		WebElement privacyPolicyLabel = driver.findElement(By.cssSelector("div[class='pull-right']"));

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String fistNameLabelContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');", firstNameLabel);
		Assert.assertEquals(fistNameLabelContent, expectedContent);
		String fistNameLabelColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')", firstNameLabel);
		Assert.assertEquals(fistNameLabelColor, expectedColor);

		String lastNameLabelContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');", lastNameLabel);
		Assert.assertEquals(lastNameLabelContent, expectedContent);
		String lastNameLabelColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')", lastNameLabel);
		Assert.assertEquals(lastNameLabelColor, expectedColor);

		String emailLabelContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');", emailLabel);
		Assert.assertEquals(emailLabelContent, expectedContent);
		String emailLabelColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')", emailLabel);
		Assert.assertEquals(emailLabelColor, expectedColor);

		String telephoneLabelContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');", telephoneLabel);
		Assert.assertEquals(telephoneLabelContent, expectedContent);
		String telephoneLabelColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')", telephoneLabel);
		Assert.assertEquals(telephoneLabelColor, expectedColor);

		String passwordLabelContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');", passwordLabel);
		Assert.assertEquals(passwordLabelContent, expectedContent);
		String passwordLabelColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')", passwordLabel);
		Assert.assertEquals(passwordLabelColor, expectedColor);

		String passwordConfirmLabelContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');",
				passwordConfirmLabel);
		Assert.assertEquals(passwordConfirmLabelContent, expectedContent);
		String passwordConfirmLabelColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')",
				passwordConfirmLabel);
		Assert.assertEquals(passwordConfirmLabelColor, expectedColor);

		String privacyPolicyLabelContent = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('content');",
				privacyPolicyLabel);
		Assert.assertEquals(privacyPolicyLabelContent, expectedContent);
		String privacyPolicyLabelColor = (String) jse.executeScript(
				"return window.getComputedStyle(arguments[0],'::before').getPropertyValue('color')",
				privacyPolicyLabel);
		Assert.assertEquals(privacyPolicyLabelColor, expectedColor);

	}

	@Test(priority = 15)
	public void verifyDataBaseTestingForRegisterAccount() {

		String enteredFirstNameData = prop.getProperty("firstName");
		driver.findElement(By.id("input-firstname")).sendKeys(enteredFirstNameData);

		String enteredLastNameData = prop.getProperty("lastName");
		driver.findElement(By.id("input-lastname")).sendKeys(enteredLastNameData);

		String enteredEmailData = CommonUtilities.generateBrandNewEmail();
		driver.findElement(By.id("input-email")).sendKeys(enteredEmailData);

		String enteredPasswordData = prop.getProperty("validPassword");
		driver.findElement(By.id("input-password")).sendKeys(enteredPasswordData);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");

		driver.findElement(By.id("input-newsletter")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();

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

		driver.findElement(By.id("input-firstname")).sendKeys("     ");
		driver.findElement(By.id("input-lastname")).sendKeys("     ");
		driver.findElement(By.id("input-email")).sendKeys("     ");
		driver.findElement(By.id("input-telephone")).sendKeys("     ");
		driver.findElement(By.id("input-password")).sendKeys("     ");
		driver.findElement(By.id("input-confirm")).sendKeys("     ");
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String expectedFirstNameWarning = "First Name must be between 1 and 32 characters!";
		String expectedLastNameWarning = "Last Name must be between 1 and 32 characters!";
		String expectedEmailWarning = "E-Mail Address does not appear to be valid!";
		String expectedTelephoneWarning = "Telephone does not appear to be valid!";

		if (browserName.equals("chrome") || browserName.equals("edge")) {
			Assert.assertEquals(
					driver.findElement(By.xpath("//input[@id='input-firstname']/following-sibling::div")).getText(),
					expectedFirstNameWarning);
			Assert.assertEquals(
					driver.findElement(By.xpath("//input[@id='input-lastname']/following-sibling::div")).getText(),
					expectedLastNameWarning);
			Assert.assertEquals(
					driver.findElement(By.xpath("//input[@id='input-email']/following-sibling::div")).getText(),
					expectedEmailWarning);
			Assert.assertEquals(
					driver.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).getText(),
					expectedTelephoneWarning);
		} else if (browserName.equals("firefox")) {
			String expectedWarningMessageOne = "Please enter an email address.";
			String actualWarningMessageOne = driver.findElement(By.id("input-email"))
					.getDomProperty("validationMessage");
			Assert.assertEquals(actualWarningMessageOne, expectedWarningMessageOne);
		}

	}

	@Test(priority = 17, dataProvider = "passwordSupplier")
	public void verifyRegisteringAccountUsingPasswordsWhichAreNotFollowingPasswordComplexityStandards(
			String passwordText) {

		driver.findElement(By.id("input-firstname")).sendKeys(prop.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(prop.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(CommonUtilities.generateBrandNewEmail());
		driver.findElement(By.id("input-telephone")).sendKeys(prop.getProperty("telephoneNumber"));

		driver.findElement(By.id("input-password")).sendKeys(passwordText);
		driver.findElement(By.id("input-confirm")).sendKeys(passwordText);

		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String expectedWarning = "Enter password which follows Password Complexity Standard!";
		boolean b = false;
		try {
			String actualWarning = driver.findElement(By.xpath("//input[@id='input-password']/following-sibling::div"))
					.getText();
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

		WebElement firstNameField = driver.findElement(By.id("input-firstname"));
		String actualFirstNameFieldHeight = firstNameField.getCssValue("height");
		Assert.assertEquals(actualFirstNameFieldHeight, expectedHeight);
		String actualFirstNameWidth = firstNameField.getCssValue("width");
		Assert.assertEquals(actualFirstNameWidth, expectedWidth);

		String exptectedFirstNameWarning = "First Name must be between 1 and 32 characters!";
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-firstname']/following-sibling::div")).getText(),
				exptectedFirstNameWarning);
		firstNameField = driver.findElement(By.id("input-firstname"));
		firstNameField.sendKeys("a");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		boolean firstNameWarningStatus = false;
		try {
			firstNameWarningStatus = driver
					.findElement(By.xpath("//input[@id='input-firstname']/following-sibling::div")).isDisplayed();
		} catch (NoSuchElementException e) {
			firstNameWarningStatus = false;
		}
		Assert.assertFalse(firstNameWarningStatus);
		firstNameField = driver.findElement(By.id("input-firstname"));
		firstNameField.clear();
		firstNameField.sendKeys("abcdeabcdeabcdeabcdeabcdeabcdeab");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		firstNameWarningStatus = false;
		try {
			firstNameWarningStatus = driver
					.findElement(By.xpath("//input[@id='input-firstname']/following-sibling::div")).isDisplayed();
		} catch (NoSuchElementException e) {
			firstNameWarningStatus = false;
		}
		Assert.assertFalse(firstNameWarningStatus);
		firstNameField = driver.findElement(By.id("input-firstname"));
		firstNameField.clear();
		firstNameField.sendKeys("abcdeabcdeabcdeabcdeabcdeabcdeabc");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-firstname']/following-sibling::div")).getText(),
				exptectedFirstNameWarning);

		// Last Name Field check

		WebElement lastNameField = driver.findElement(By.id("input-lastname"));
		String actualLastNameFieldHeight = lastNameField.getCssValue("height");
		Assert.assertEquals(actualLastNameFieldHeight, expectedHeight);
		String actualLastNameWidth = lastNameField.getCssValue("width");
		Assert.assertEquals(actualLastNameWidth, expectedWidth);

		String exptectedLastNameWarning = "Last Name must be between 1 and 32 characters!";
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-lastname']/following-sibling::div")).getText(),
				exptectedLastNameWarning);
		lastNameField = driver.findElement(By.id("input-lastname"));
		lastNameField.sendKeys("a");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		boolean lastNameWarningStatus = false;
		try {
			lastNameWarningStatus = driver.findElement(By.xpath("//input[@id='input-lastname']/following-sibling::div"))
					.isDisplayed();
		} catch (NoSuchElementException e) {
			lastNameWarningStatus = false;
		}
		Assert.assertFalse(lastNameWarningStatus);
		lastNameField = driver.findElement(By.id("input-lastname"));
		lastNameField.clear();
		lastNameField.sendKeys("abcdeabcdeabcdeabcdeabcdeabcdeab");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		lastNameWarningStatus = false;
		try {
			lastNameWarningStatus = driver.findElement(By.xpath("//input[@id='input-lastname']/following-sibling::div"))
					.isDisplayed();
		} catch (NoSuchElementException e) {
			lastNameWarningStatus = false;
		}
		Assert.assertFalse(lastNameWarningStatus);
		lastNameField = driver.findElement(By.id("input-lastname"));
		lastNameField.clear();
		lastNameField.sendKeys("abcdeabcdeabcdeabcdeabcdeabcdeabc");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-lastname']/following-sibling::div")).getText(),
				exptectedLastNameWarning);

		// Email Field check

		WebElement emailField = driver.findElement(By.id("input-email"));
		String actualEmailFieldHeight = emailField.getCssValue("height");
		Assert.assertEquals(actualEmailFieldHeight, expectedHeight);
		String actualEmailFieldWidth = emailField.getCssValue("width");
		Assert.assertEquals(actualEmailFieldWidth, expectedWidth);
		emailField.sendKeys("adfdsfasdfadfdsssssafasdfasdfasdfadsfasdf@email.com");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		boolean emailWarningStatus = false;
		try {
			emailWarningStatus = driver.findElement(By.xpath("//input[@id='input-email']/following-sibling::div"))
					.isDisplayed();
		} catch (NoSuchElementException e) {
			emailWarningStatus = false;
		}
		Assert.assertFalse(emailWarningStatus);

		// Telephone Field check

		WebElement telephoneField = driver.findElement(By.id("input-telephone"));
		String actualTelephoneFieldHeight = telephoneField.getCssValue("height");
		Assert.assertEquals(actualTelephoneFieldHeight, expectedHeight);
		String actualTelephoneFieldWidth = telephoneField.getCssValue("width");
		Assert.assertEquals(actualTelephoneFieldWidth, expectedWidth);

		String expectedTelephoneWarning = "Telephone must be between 3 and 32 characters!";
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).getText(),
				expectedTelephoneWarning);
		telephoneField = driver.findElement(By.id("input-telephone"));
		telephoneField.sendKeys("1");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).getText(),
				expectedTelephoneWarning);
		telephoneField = driver.findElement(By.id("input-telephone"));
		telephoneField.clear();
		telephoneField.sendKeys("12");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).getText(),
				expectedTelephoneWarning);
		telephoneField = driver.findElement(By.id("input-telephone"));
		telephoneField.clear();
		telephoneField.sendKeys("123");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		boolean telephoneWarningStatus = false;
		try {
			telephoneWarningStatus = driver
					.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).isDisplayed();
		} catch (NoSuchElementException e) {
			telephoneWarningStatus = false;
		}
		Assert.assertFalse(telephoneWarningStatus);
		telephoneField = driver.findElement(By.id("input-telephone"));
		telephoneField.clear();
		telephoneField.sendKeys("12345678901234567890123456789012");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		telephoneWarningStatus = false;
		try {
			telephoneWarningStatus = driver
					.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).isDisplayed();
		} catch (NoSuchElementException e) {
			telephoneWarningStatus = false;
		}
		Assert.assertFalse(telephoneWarningStatus);
		telephoneField = driver.findElement(By.id("input-telephone"));
		telephoneField.clear();
		telephoneField.sendKeys("123456789012345678901234567890123");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).getText(),
				expectedTelephoneWarning);

		// Password Field check
		WebElement passwordField = driver.findElement(By.id("input-password"));
		String actualPasswordFieldHeight = passwordField.getCssValue("height");
		Assert.assertEquals(actualPasswordFieldHeight, expectedHeight);
		String actualPasswordFieldWidth = passwordField.getCssValue("width");
		Assert.assertEquals(actualPasswordFieldWidth, expectedWidth);
		String expectedPasswordWarning = "Password must be between 4 and 20 characters!";
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-password']/following-sibling::div")).getText(),
				expectedPasswordWarning);

		passwordField = driver.findElement(By.id("input-password"));
		passwordField.clear();
		passwordField.sendKeys("1");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-password']/following-sibling::div")).getText(),
				expectedPasswordWarning);
		passwordField = driver.findElement(By.id("input-password"));
		passwordField.clear();
		passwordField.sendKeys("12");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-password']/following-sibling::div")).getText(),
				expectedPasswordWarning);
		passwordField = driver.findElement(By.id("input-password"));
		passwordField.clear();
		passwordField.sendKeys("123");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-password']/following-sibling::div")).getText(),
				expectedPasswordWarning);
		passwordField = driver.findElement(By.id("input-password"));
		passwordField.clear();
		passwordField.sendKeys("1234");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		boolean passwordWarningStatus = false;
		try {
			passwordWarningStatus = driver.findElement(By.xpath("//input[@id='input-password']/following-sibling::div"))
					.isDisplayed();
		} catch (NoSuchElementException e) {
			passwordWarningStatus = false;
		}
		Assert.assertFalse(passwordWarningStatus);
		passwordField = driver.findElement(By.id("input-password"));
		passwordField.clear();
		passwordField.sendKeys("12345678901234567890");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		passwordWarningStatus = false;
		try {
			passwordWarningStatus = driver.findElement(By.xpath("//input[@id='input-password']/following-sibling::div"))
					.isDisplayed();
		} catch (NoSuchElementException e) {
			passwordWarningStatus = false;
		}
		Assert.assertFalse(passwordWarningStatus);

		passwordField = driver.findElement(By.id("input-password"));
		passwordField.clear();
		passwordField.sendKeys("123456789012345678901");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		passwordWarningStatus = false;
		try {
			passwordWarningStatus = driver.findElement(By.xpath("//input[@id='input-password']/following-sibling::div"))
					.isDisplayed();
		} catch (NoSuchElementException e) {
			passwordWarningStatus = false;
		}
		Assert.assertTrue(passwordWarningStatus);

		// Password Confirm Field check
		WebElement passwordConfirmField = driver.findElement(By.id("input-confirm"));
		String actualPasswordConfirmFieldHeight = passwordConfirmField.getCssValue("height");
		Assert.assertEquals(actualPasswordConfirmFieldHeight, expectedHeight);
		String actualPasswordConfirmFieldWidth = passwordConfirmField.getCssValue("width");
		Assert.assertEquals(actualPasswordConfirmFieldWidth, expectedWidth);

		// Continue Button
		WebElement continueButton = driver.findElement(By.xpath("//input[@value='Continue']"));
		String actualButtonTextColor = continueButton.getCssValue("color");
		Assert.assertEquals(actualButtonTextColor, "rgba(255, 255, 255, 1)");
		String actualButtonBackgroundColor = continueButton.getCssValue("background-color");
		Assert.assertEquals(actualButtonBackgroundColor, "rgba(34, 154, 200, 1)");
		String actualButtonFontSize = continueButton.getCssValue("font-size");
		Assert.assertEquals(actualButtonFontSize, "12px");

		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();

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
		String firstName = "     " + prop.getProperty("firstName") + "     ";
		driver.findElement(By.id("input-firstname")).sendKeys(firstName);
		String lastName = "     " + prop.getProperty("lastName") + "     ";
		driver.findElement(By.id("input-lastname")).sendKeys(lastName);
		String emailText = "     " + CommonUtilities.generateBrandNewEmail() + "     ";
		driver.findElement(By.id("input-email")).sendKeys(emailText);
		String telephone = "     " + prop.getProperty("telephoneNumber") + "     ";
		driver.findElement(By.id("input-telephone")).sendKeys(telephone);
		driver.findElement(By.id("input-password")).sendKeys("     " + prop.getProperty("validPassword") + "     ");
		driver.findElement(By.id("input-confirm")).sendKeys("     " + prop.getProperty("validPassword") + "     ");
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		if (browserName.equals("chrome") || browserName.equals("edge")) {
			driver.findElement(By.xpath("//a[@class='btn btn-primary'][text()='Continue']")).click();
			driver.findElement(By.linkText("Edit your account information")).click();
			String actualFirstName = driver.findElement(By.id("input-firstname")).getDomAttribute("value");
			softAssert.assertEquals(actualFirstName, firstName.trim());
			String actualLastName = driver.findElement(By.id("input-lastname")).getDomAttribute("value");
			softAssert.assertEquals(actualLastName, lastName.trim());
			String actualEmail = driver.findElement(By.id("input-email")).getDomAttribute("value");
			softAssert.assertEquals(actualEmail, emailText.trim());
			String acutalTelephone = driver.findElement(By.id("input-telephone")).getDomAttribute("value");
			softAssert.assertEquals(acutalTelephone, telephone.trim());
			softAssert.assertAll();
		} else if (browserName.equals("firefox")) {
			String expectedWarningMessageOne = "Please enter an email address.";
			String actualWarningMessageOne = driver.findElement(By.id("input-email"))
					.getDomProperty("validationMessage");
			Assert.assertEquals(actualWarningMessageOne, expectedWarningMessageOne);
		}

	}

	@Test(priority = 20)
	public void verifyRegisterAccountPrivacyPolicyField() {

		Assert.assertFalse(driver.findElement(By.name("agree")).isSelected());

	}

	@Test(priority = 21)
	public void verifyRegisteringAccountWithoutSelectingPrivacyPolicyCheckboxField() {

		driver.findElement(By.id("input-firstname")).sendKeys(prop.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(prop.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(CommonUtilities.generateBrandNewEmail());
		driver.findElement(By.id("input-telephone")).sendKeys(prop.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.id("input-confirm")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String expectedPrivacyPolicyWarning = "Warning: You must agree to the Privacy Policy!";

		Assert.assertEquals(
				driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).getText(),
				expectedPrivacyPolicyWarning);

	}

	@Test(priority = 22)
	public void verifyRegisteringAccountPasswordFieldsForSecurity() {

		Assert.assertEquals(driver.findElement(By.id("input-password")).getDomAttribute("type"), "password");
		Assert.assertEquals(driver.findElement(By.id("input-confirm")).getDomAttribute("type"), "password");

	}

	@Test(priority = 23)
	public void verifyRegisterAccountPageNavigations() {

		driver.findElement(By.xpath("//i[@class='fa fa-phone']")).click();
		Assert.assertEquals(driver.getTitle(), "Contact Us");
		driver.navigate().back();

		driver.findElement(By.xpath("//i[@class='fa fa-heart']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//span[@class='hidden-xs hidden-sm hidden-md'][contains(text(),'Wish List')]"))
				.click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//i[@class='fa fa-shopping-cart']")).click();
		Assert.assertEquals(driver.getTitle(), "Shopping Cart");
		driver.navigate().back();

		driver.findElement(By.xpath("//span[text()='Shopping Cart']")).click();
		Assert.assertEquals(driver.getTitle(), "Shopping Cart");
		driver.navigate().back();

		driver.findElement(By.xpath("//i[@class='fa fa-share']")).click();
		Assert.assertEquals(driver.getTitle(), "Shopping Cart");
		driver.navigate().back();

		driver.findElement(By.xpath("//span[text()='Checkout']")).click();
		Assert.assertEquals(driver.getTitle(), "Shopping Cart");
		driver.navigate().back();

		driver.findElement(By.linkText("Qafox.com")).click();
		Assert.assertEquals(driver.getTitle(), "Your Store");
		driver.navigate().back();

		driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg']")).click();
		Assert.assertEquals(driver.getTitle(), "Search");
		driver.navigate().back();

		driver.findElement(By.xpath("//i[@class='fa fa-home']")).click();
		Assert.assertEquals(driver.getTitle(), "Your Store");
		driver.navigate().back();

		driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Account']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Register']")).click();
		Assert.assertEquals(driver.getTitle(), "Register Account");

		driver.findElement(By.linkText("login page")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Login']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Register']")).click();
		Assert.assertEquals(driver.getTitle(), "Register Account");

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Forgotten Password']")).click();
		Assert.assertEquals(driver.getTitle(), "Forgot Your Password?");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='My Account']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Address Book']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Wish List']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Order History']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Downloads']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Recurring payments']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Reward Points']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Returns']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Transactions']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Newsletter']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='About Us']")).click();
		Assert.assertEquals(driver.getTitle(), "About Us");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Delivery Information']")).click();
		Assert.assertEquals(driver.getTitle(), "Delivery Information");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Privacy Policy']")).click();
		Assert.assertEquals(driver.getTitle(), "Privacy Policy");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Terms & Conditions']")).click();
		Assert.assertEquals(driver.getTitle(), "Terms & Conditions");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Contact Us']")).click();
		Assert.assertEquals(driver.getTitle(), "Contact Us");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Terms & Conditions']")).click();
		Assert.assertEquals(driver.getTitle(), "Terms & Conditions");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Returns']")).click();
		Assert.assertEquals(driver.getTitle(), "Product Returns");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Site Map']")).click();
		Assert.assertEquals(driver.getTitle(), "Site Map");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Brands']")).click();
		Assert.assertEquals(driver.getTitle(), "Find Your Favorite Brand");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Gift Certificates']")).click();
		Assert.assertEquals(driver.getTitle(), "Purchase a Gift Certificate");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Affiliate']")).click();
		Assert.assertEquals(driver.getTitle(), "Affiliate Program");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Specials']")).click();
		Assert.assertEquals(driver.getTitle(), "Special Offers");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='My Account']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Order History']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Wish List']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

		driver.findElement(By.xpath("//footer//a[text()='Newsletter']")).click();
		Assert.assertEquals(driver.getTitle(), "Account Login");
		driver.navigate().back();

	}

	@Test(priority = 24)
	public void verifyRegisteringAccountWithoutEnteringConfirmationPassword() {

		driver.findElement(By.id("input-firstname")).sendKeys(prop.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(prop.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(CommonUtilities.generateBrandNewEmail());
		driver.findElement(By.id("input-telephone")).sendKeys(prop.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		String expectedWarning = "Password confirmation does not match password!";
		Assert.assertEquals(
				driver.findElement(By.xpath("//input[@id='input-confirm']/following-sibling::div")).getText(),
				expectedWarning);

	}

	@Test(priority = 25)
	public void verifyRegisterAccountPageBreadcrumbURLTitleHeading() {

		String expectedTitle = "Register Account";
		Assert.assertEquals(driver.getTitle(), expectedTitle);

		String expectedURL = "https://tutorialsninja.com/demo/index.php?route=account/register";
		Assert.assertEquals(driver.getCurrentUrl(), expectedURL);

		Assert.assertTrue(
				driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Register']")).isDisplayed());

		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='content']/h1")).getText(), "Register Account");

	}

	@Test(priority = 26)
	public void verifyRegisterAccountUI() throws IOException {

		if (browserName.equals("chrome") || browserName.equals("edge")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualRAPageUI.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualRAPageUI.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedRAPageUI.png"));
		} else if (browserName.equals("firefox")) {
			CommonUtilities.takeScreenshot(driver,
					System.getProperty("user.dir") + "\\Screenshots\\actualFirefoxRAPageUI.png");
			Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
					System.getProperty("user.dir") + "\\Screenshots\\actualFirefoxRAPageUI.png",
					System.getProperty("user.dir") + "\\Screenshots\\expectedFirefoxRAPageUI.png"));
		}

	}

	@Test(priority = 27)
	public void verifyRegisterAccountInAllEnvironments() {

		driver.findElement(By.id("input-firstname")).sendKeys(prop.getProperty("firstName"));
		driver.findElement(By.id("input-lastname")).sendKeys(prop.getProperty("lastName"));
		driver.findElement(By.id("input-email")).sendKeys(CommonUtilities.generateBrandNewEmail());
		driver.findElement(By.id("input-telephone")).sendKeys(prop.getProperty("telephoneNumber"));
		driver.findElement(By.id("input-password")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.id("input-confirm")).sendKeys(prop.getProperty("validPassword"));
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Logout']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Success']")).isDisplayed());

	}

}
