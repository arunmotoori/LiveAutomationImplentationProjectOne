package register;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.CommonUtilities;

public class TC_RF_015 {
	
	@Test
	public void verifyDataBaseTestingForRegisterAccount() {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("http://localhost/opencart/");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Register")).click();
		
		String enteredFirstNameData = "Arun";
		driver.findElement(By.id("input-firstname")).sendKeys(enteredFirstNameData);
		
		String enteredLastNameData = "Motoori";
		driver.findElement(By.id("input-lastname")).sendKeys(enteredLastNameData);
		
		String enteredEmailData = CommonUtilities.generateBrandNewEmail();
		driver.findElement(By.id("input-email")).sendKeys(enteredEmailData);
		
		String enteredPasswordData = "12345";
		driver.findElement(By.id("input-password")).sendKeys(enteredPasswordData);
		
		driver.findElement(By.id("input-newsletter")).click();
		driver.findElement(By.name("agree")).click();
		driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
		
		// Database credentials
        String jdbcURL = "jdbc:mysql://localhost:3306/opencart_db";
        String dbUser = "root";
        String dbPassword = "";
        
        // SQL query
        String sqlQuery = "SELECT * FROM oc_customer";
        
     // JDBC objects
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        int newsletter = 0;

        try {
            // Step 1: Register JDBC driver (optional in newer versions)
        	
            // Step 2: Open a connection
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            System.out.println("Connected to the database!");

            // Step 3: Create a statement
            statement = connection.createStatement();

            // Step 4: Execute the query
            resultSet = statement.executeQuery(sqlQuery);

            // Step 5: Process the ResultSet
            while (resultSet.next()) {
                firstName = resultSet.getString("firstname"); // Replace with your column name
                lastName = resultSet.getString("lastname"); // Replace with your column name
                email = resultSet.getString("email");
                newsletter = resultSet.getInt("newsletter");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 6: Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        Assert.assertEquals(firstName, enteredFirstNameData);
        Assert.assertEquals(lastName, enteredLastNameData);
        Assert.assertEquals(email, enteredEmailData);
        Assert.assertEquals(newsletter, 1);
        
        driver.quit();
		
	}

}
