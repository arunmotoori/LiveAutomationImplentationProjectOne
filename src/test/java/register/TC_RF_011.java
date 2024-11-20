package register;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import utils.CommonUtilities;

public class TC_RF_011 {
	
	WebDriver driver;
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
	
	@Test
	public void verifyRegisteringAccountUsingInvalidTelephoneNumber() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo/");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
		
		driver.findElement(By.id("input-firstname")).sendKeys("Arun");
		driver.findElement(By.id("input-lastname")).sendKeys("Motoori");
		driver.findElement(By.id("input-email")).sendKeys(CommonUtilities.generateBrandNewEmail());
		driver.findElement(By.id("input-telephone")).sendKeys("abcde");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.id("input-confirm")).sendKeys("12345");
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		String expectedWarningMessage = "Telephone number entered by you is invalid!";
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='input-telephone']/following-sibling::div")).getText(), expectedWarningMessage);
		
		Assert.assertFalse(driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Success']")).isDisplayed());
		
	}

}
