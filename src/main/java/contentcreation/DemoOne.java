package contentcreation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DemoOne {

	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://the-internet.herokuapp.com/javascript_alerts");

		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

//		Alert alert = driver.switchTo().alert();
//		alert.accept();

//		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
//		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//		alert.accept();

		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.alert=function(){}");
		} catch (Exception e) {

		}

	}

}
