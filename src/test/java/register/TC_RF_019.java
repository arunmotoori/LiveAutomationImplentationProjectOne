package register;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import utils.CommonUtilities;

public class TC_RF_019 {
	
	WebDriver driver;
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
	
	@Test
	public void verifyRegisterAccountUsingLeadingAndTrailingSpaces() {
		
		SoftAssert softAssert = new SoftAssert();
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo/");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
		String firstName = "     Arun     ";
		driver.findElement(By.id("input-firstname")).sendKeys(firstName);
		String lastName = "     Motoori     ";
		driver.findElement(By.id("input-lastname")).sendKeys(lastName);
		String emailText = "     "+CommonUtilities.generateBrandNewEmail()+"     ";
		driver.findElement(By.id("input-email")).sendKeys(emailText);
		String telephone = "     1234567890     ";
		driver.findElement(By.id("input-telephone")).sendKeys(telephone);
		driver.findElement(By.id("input-password")).sendKeys("     12345     ");
		driver.findElement(By.id("input-confirm")).sendKeys("     12345     ");
		driver.findElement(By.xpath("//input[@name='newsletter'][@value='1']")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//input[@value='Continue']")).click();
		driver.findElement(By.xpath("//a[@class='btn btn-primary'][text()='Continue']")).click();
		driver.findElement(By.linkText("Edit your account information")).click();
		String actualFirstName = driver.findElement(By.id("input-firstname")).getDomAttribute("value");
		softAssert.assertEquals(actualFirstName,firstName.trim());
		String actualLastName = driver.findElement(By.id("input-lastname")).getDomAttribute("value");
		softAssert.assertEquals(actualLastName, lastName.trim());
		String actualEmail = driver.findElement(By.id("input-email")).getDomAttribute("value");
		softAssert.assertEquals(actualEmail, emailText.trim());
		String acutalTelephone = driver.findElement(By.id("input-telephone")).getDomAttribute("value");
		softAssert.assertEquals(acutalTelephone, telephone.trim());
		softAssert.assertAll();
	
	}

}
