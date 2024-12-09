package register;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_RF_023 {
	
	@Test
	public void verifyRegisterAccountPageNavigations() {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo/");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
		
		driver.findElement(By.xpath("//i[@class='fa fa-phone']")).click();
		Assert.assertEquals(driver.getTitle(),"Contact Us");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//i[@class='fa fa-heart']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//span[@class='hidden-xs hidden-sm hidden-md'][contains(text(),'Wish List')]")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//i[@class='fa fa-shopping-cart']")).click();
		Assert.assertEquals(driver.getTitle(),"Shopping Cart");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//span[text()='Shopping Cart']")).click();
		Assert.assertEquals(driver.getTitle(),"Shopping Cart");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//i[@class='fa fa-share']")).click();
		Assert.assertEquals(driver.getTitle(),"Shopping Cart");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//span[text()='Checkout']")).click();
		Assert.assertEquals(driver.getTitle(),"Shopping Cart");
		driver.navigate().back();
		
		driver.findElement(By.linkText("Qafox.com")).click();
		Assert.assertEquals(driver.getTitle(),"Your Store");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//button[@class='btn btn-default btn-lg']")).click();
		Assert.assertEquals(driver.getTitle(),"Search");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//i[@class='fa fa-home']")).click();
		Assert.assertEquals(driver.getTitle(),"Your Store");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Account']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Register']")).click();
		Assert.assertEquals(driver.getTitle(),"Register Account");
			
		driver.findElement(By.linkText("login page")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Login']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Register']")).click();
		Assert.assertEquals(driver.getTitle(),"Register Account");
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Forgotten Password']")).click();
		Assert.assertEquals(driver.getTitle(),"Forgot Your Password?");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='My Account']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Address Book']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Wish List']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Order History']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Downloads']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Recurring payments']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Reward Points']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Returns']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Transactions']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Newsletter']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='About Us']")).click();
		Assert.assertEquals(driver.getTitle(),"About Us");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Delivery Information']")).click();
		Assert.assertEquals(driver.getTitle(),"Delivery Information");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Privacy Policy']")).click();
		Assert.assertEquals(driver.getTitle(),"Privacy Policy");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Terms & Conditions']")).click();
		Assert.assertEquals(driver.getTitle(),"Terms & Conditions");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Contact Us']")).click();
		Assert.assertEquals(driver.getTitle(),"Contact Us");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Terms & Conditions']")).click();
		Assert.assertEquals(driver.getTitle(),"Terms & Conditions");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Returns']")).click();
		Assert.assertEquals(driver.getTitle(),"Product Returns");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Site Map']")).click();
		Assert.assertEquals(driver.getTitle(),"Site Map");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Brands']")).click();
		Assert.assertEquals(driver.getTitle(),"Find Your Favorite Brand");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Gift Certificates']")).click();
		Assert.assertEquals(driver.getTitle(),"Purchase a Gift Certificate");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Affiliate']")).click();
		Assert.assertEquals(driver.getTitle(),"Affiliate Program");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Specials']")).click();
		Assert.assertEquals(driver.getTitle(),"Special Offers");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='My Account']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Order History']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Wish List']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.findElement(By.xpath("//footer//a[text()='Newsletter']")).click();
		Assert.assertEquals(driver.getTitle(),"Account Login");
		driver.navigate().back();
		
		driver.quit();
		
	}

}
