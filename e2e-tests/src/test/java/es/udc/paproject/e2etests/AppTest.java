package es.udc.paproject.e2etests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AppTest {

	WebDriver driver;

	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
	}

	private void login(String userName, String password) {
		driver.get("http://localhost:5173");

		String title = driver.getTitle();
		assertEquals("PA Cinema", title);

		WebElement loginLink = driver.findElement(By.id("loginLink"));
		loginLink.click();

		WebElement userNameField = driver.findElement(By.id("userName"));
		WebElement passwordField = driver.findElement(By.id("password"));
		userNameField.sendKeys(userName);
		passwordField.sendKeys(password);

		WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
		submitButton.click();

		WebElement userDropdown = driver.findElement(By.id("user-dropdown"));
		String value = userDropdown.getText();
		assertTrue(value.contains(userName));
	}

	@Test
	public void testLogin() {
		login("testviewer", "pa2526");
	}

	@AfterEach
	public final void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
