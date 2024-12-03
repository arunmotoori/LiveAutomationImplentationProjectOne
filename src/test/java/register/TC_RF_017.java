package register;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.CommonUtilities;

public class TC_RF_017 {
	
	WebDriver driver;
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
	
	
	@Test(dataProvider="passwordSupplier")
	public void verifyRegisteringAccountUsingPasswordsWhichAreNotFollowingPasswordComplexityStandards(String passwordText) {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo/");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
		
		driver.findElement(By.id("input-firstname")).sendKeys("Arun");
		driver.findElement(By.id("input-lastname")).sendKeys("Motoori");
		driver.findElement(By.id("input-email")).sendKeys(CommonUtilities.generateBrandNewEmail());
		driver.findElement(By.id("input-telephone")).sendKeys("1234567890");
		
		driver.findElement(By.id("input-password")).sendKeys(passwordText);
		driver.findElement(By.id("input-confirm")).sendKeys(passwordText);
		
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		String expectedWarning = "Enter password which follows Password Complexity Standard!";
		
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='input-password']/following-sibling::div")).getText(), expectedWarning);
		
	}
	
	@DataProvider(name="passwordSupplier")
	public Object[][] supplyPasswords() {
		
		Object[][] data = {{"12345"},{"abcdefghi"},{"abcd1234"},{"abcd123$"},{"ABCD456#"}};
		return data;
		
	}

}
