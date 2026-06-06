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

	private static final int E2E_SESSION_ID = 15;

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

	private void buyTickets() {
		// Autenticar al usuario "testviewer" (Tarea 0).
		login("testviewer", "pa2526");

		// Acceder a la URL de detalle de la sesión E2E (id=15 en 2-MySQLCreateData.sql).
		driver.get("http://localhost:5173/catalog/session-details/" + E2E_SESSION_ID);

		// Localizar el nombre de la película y guardarlo en una variable.
		WebElement sessionTitle = driver.findElement(By.id("session-details-title"));
		String movieTitle = sessionTitle.getText();

		// Rellenar el formulario de compra para adquirir dos entradas.
		WebElement numTicketsField = driver.findElement(By.id("numTickets"));
		numTicketsField.clear();
		numTicketsField.sendKeys("2");

		WebElement bankCardField = driver.findElement(By.id("bankCard"));
		bankCardField.sendKeys("1234567890123456");

		// Hacer clic en el botón de comprar.
		WebElement buyButton = driver.findElement(By.id("buy-form-submit"));
		buyButton.click();

		// Localizar el identificador de la compra y guardarlo en una variable.
		WebElement purchaseIdElement = driver.findElement(By.id("purchase-completed-id"));
		String purchaseId = purchaseIdElement.getText();

		// Hacer clic en la opción que muestra el histórico de compras del usuario.
		WebElement userDropdown = driver.findElement(By.id("user-dropdown"));
		userDropdown.click();

		WebElement purchaseHistoryLink = driver.findElement(By.id("purchase-history-link"));
		purchaseHistoryLink.click();

		// Comprobar que la primera compra del histórico coincide con id y película esperados.
		WebElement firstPurchaseId = driver.findElement(By.id("purchase-history-first-id"));
		WebElement firstPurchaseMovie = driver.findElement(By.id("purchase-history-first-movie"));

		assertEquals(purchaseId, firstPurchaseId.getText());
		assertEquals(movieTitle, firstPurchaseMovie.getText());
	}
	private void deliverTickets() {
		login("testticketseller", "pa2526");

		driver.findElement(By.id("user-dropdown")).click();
		driver.findElement(By.id("deliver-tickets-link")).click();

		driver.findElement(By.id("purchaseId")).sendKeys("3");
		driver.findElement(By.id("bankCard")).sendKeys("9876543210987654");
		driver.findElement(By.id("deliver-tickets-submit")).click();

		WebElement successMessage = driver.findElement(By.id("deliver-tickets-completed-id"));
		assertTrue(successMessage.isDisplayed());

		driver.findElement(By.id("user-dropdown")).click();
		driver.findElement(By.id("deliver-tickets-link")).click();

		driver.findElement(By.id("purchaseId")).sendKeys("3");
		driver.findElement(By.id("bankCard")).sendKeys("9876543210987654");
		driver.findElement(By.id("deliver-tickets-submit")).click();

		WebElement errorMessage = driver.findElement(By.id("errors"));
		assertTrue(errorMessage.isDisplayed());
	}


	@Test
	public void testLogin() {
		login("testviewer", "pa2526");
	}

	@Test
	public void testSessionDetails() {
		viewSessionDetails();
	}

	@Test
	public void testBuyTickets() {
		buyTickets();
	}

	@Test
	public void testDeliverTickets() {
		deliverTickets();
	}

	/*@AfterEach
	public final void teardown() {*/

	@AfterEach
	public final void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
