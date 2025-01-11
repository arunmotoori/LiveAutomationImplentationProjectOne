package experiment;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class DemoFour {

	public static void main(String[] args) {
		
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://omayo.blogspot.com/");
		
		boolean b = driver.findElement(By.id("dte")).isSelected();
		System.out.println(b);
		
		driver.quit();
	}

}
