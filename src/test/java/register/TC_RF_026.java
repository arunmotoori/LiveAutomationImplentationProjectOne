package register;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.CommonUtilities;

public class TC_RF_026 {

	@Test
	public void verifyRegisterAccountUI() throws IOException {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo/");

		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();

		CommonUtilities.takeScreenshot(driver, System.getProperty("user.dir") + "\\Screenshots\\actualRAPageUI.png");

		Assert.assertFalse(CommonUtilities.compareTwoScreenshots(
				System.getProperty("user.dir") + "\\Screenshots\\actualRAPageUI.png",
				System.getProperty("user.dir") + "\\Screenshots\\expectedRAPageUI.png"));

		driver.quit();

	}

}
