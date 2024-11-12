package register;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_RF_010 {
	
	@Test
	public void verifyRegisteringAccountUsingInvalidEmail() throws IOException, InterruptedException {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
		
		driver.findElement(By.id("input-firstname")).sendKeys("Arun");
		driver.findElement(By.id("input-lastname")).sendKeys("Motoori");
		driver.findElement(By.id("input-email")).sendKeys("amotoori");
		driver.findElement(By.id("input-telephone")).sendKeys("1234567890");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.id("input-confirm")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		String expectedWarningMessageOne = "Please include an '@' in the email address. 'amotoori' is missing an '@'.";
		String actualWarningMessageOne = driver.findElement(By.id("input-email")).getAttribute("validationMessage");
		Assert.assertEquals(actualWarningMessageOne, expectedWarningMessageOne);
		
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys("amotoori@");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		String expectedWarningMessageTwo = "Please enter a part following '@'. 'amotoori@' is incomplete.";
		String actualWarningMessageTwo = driver.findElement(By.id("input-email")).getAttribute("validationMessage");
		Assert.assertEquals(actualWarningMessageTwo, expectedWarningMessageTwo);
		
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys("amotoori@gmail");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		String expectedWarningMessageThree = "E-Mail Address does not appear to be valid!";
		String actualWarningMessageThree = driver.findElement(By.xpath("//input[@id='input-email']/following-sibling::div")).getText();
		Assert.assertEquals(actualWarningMessageThree, expectedWarningMessageThree);
		
		driver.findElement(By.id("input-email")).clear();
		driver.findElement(By.id("input-email")).sendKeys("amotoori@gmail.");
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		
		String expectedWarningMessageFour = "'.' is used at a wrong position in 'gmail.'.";
		String actualWarningMessageFour = driver.findElement(By.id("input-email")).getAttribute("validationMessage");
		Assert.assertEquals(actualWarningMessageFour, expectedWarningMessageFour);
		
		driver.quit();
		
	}

}
