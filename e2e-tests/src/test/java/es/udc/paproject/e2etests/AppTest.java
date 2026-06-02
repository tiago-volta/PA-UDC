package es.udc.paproject.e2etests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

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

	private void viewSessionDetails() {
		login("testviewer", "pa2526");

		WebElement dateDropdown = driver.findElement(By.id("billboardDate"));
		Select select = new Select(dateDropdown);
		select.selectByIndex(1);

		List<WebElement> movieLinks = driver.findElements(By.cssSelector("[id^='movie-link-']"));
		WebElement firstMovieLink = movieLinks.get(0);
		String movieTitle = firstMovieLink.getText();

		WebElement firstCard = driver.findElement(By.cssSelector(".card"));
		WebElement firstSessionLink = firstCard.findElement(By.cssSelector("[id^='session-link-']"));
		String sessionTime = firstSessionLink.getText();
		firstSessionLink.click();

		driver.findElement(By.id("session-details-title"));
		driver.findElement(By.id("session-details-date"));
		driver.findElement(By.id("session-details-runtime"));
		driver.findElement(By.id("session-details-price"));
		driver.findElement(By.id("session-details-room"));
		driver.findElement(By.id("session-details-free-seats"));

		WebElement sessionTitle = driver.findElement(By.id("session-details-title"));
		WebElement sessionTimeOnDetails = driver.findElement(By.id("session-details-time"));

		assertEquals(movieTitle, sessionTitle.getText());
		assertEquals(sessionTime, sessionTimeOnDetails.getText());

		driver.findElement(By.id("buy-form-title"));
	}

	@Test
	public void testLogin() {
		login("testviewer", "pa2526");
	}

	@Test
	public void testSessionDetails() {
		viewSessionDetails();
	}

	@AfterEach
	public final void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
