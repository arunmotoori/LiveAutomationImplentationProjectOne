package experiment;

import java.time.Duration;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DemoThree {

	public static void main(String[] args) {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://omayo.blogspot.com/");
		
		WebElement textField = driver.findElement(By.id("textbox1"));
		
		textField.clear();
		textField.sendKeys("Arun Motoori");
	
		String domAttribute = textField.getDomAttribute("value");
		System.out.println(domAttribute);
		
		String domProperty = textField.getDomProperty("value");
		System.out.println(domProperty);
		
		driver.quit();
		
	}

}
