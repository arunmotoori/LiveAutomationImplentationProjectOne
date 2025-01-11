package experiment;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class DemoFive {

	public static void main(String[] args) {
		
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://omayo.blogspot.com/");
		
		String str = driver.findElement(By.id("ta1")).getDomAttribute("readonly");
		System.out.println(str);
		
		driver.quit();
	}

}
