package register;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_RF_013 {
	
	@Test
	public void verifyRegisterAccountPagePlaceholders() {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo/");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
		
		String expectedFirstNamePlaceHolder = "First Name";
		String expectedLastNamePlaceHolder = "Last Name";
		String expectedEmailPlaceHolder = "E-Mail";
		String expectedTelephonePlaceHolder = "Telephone";
		String expectedPasswordPlaceHolder = "Password";
		String expectedPasswordConfirmPlaceHolder = "Password Confirm";
		
		Assert.assertEquals(driver.findElement(By.id("input-firstname")).getAttribute("placeholder"), expectedFirstNamePlaceHolder);
		Assert.assertEquals(driver.findElement(By.id("input-lastname")).getAttribute("placeholder"), expectedLastNamePlaceHolder);
		Assert.assertEquals(driver.findElement(By.id("input-email")).getAttribute("placeholder"), expectedEmailPlaceHolder);
		Assert.assertEquals(driver.findElement(By.id("input-telephone")).getAttribute("placeholder"), expectedTelephonePlaceHolder);
		Assert.assertEquals(driver.findElement(By.id("input-password")).getAttribute("placeholder"), expectedPasswordPlaceHolder);
		Assert.assertEquals(driver.findElement(By.id("input-confirm")).getAttribute("placeholder"), expectedPasswordConfirmPlaceHolder);
		
		driver.quit();
	}

}
