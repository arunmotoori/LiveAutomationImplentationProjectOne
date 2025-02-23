package experiment;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DemoSix {
	
	public static void main(String[] args) {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://tutorialsninja.com/demo/");
		driver.findElement(By.name("search")).sendKeys("Mac");
		driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg']")).click();
		
		List<WebElement> items = driver.findElements(By.xpath("//div[@class='product-thumb']//h4/a"));
		List<String> itemNames = new ArrayList<>();
		for(WebElement item : items) {
			String itemName = item.getText();
			itemNames.add(itemName);
		}
		System.out.println(itemNames);
		driver.quit();
	}

}
