package dev.langchain4j.quarkus.deepdive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.nio.file.Files;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

import jakarta.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.quarkiverse.playwright.InjectPlaywright;
import io.quarkiverse.playwright.WithPlaywright;

@QuarkusTest
@WithPlaywright(recordVideoDir = UITest.RECORD_DIR, slowMo = 500)
class UITest {
	static final String RECORD_DIR = "target/playwright";

	@InjectPlaywright
	BrowserContext context;

	@ConfigProperty(name = "quarkus.http.test-port")
	int quarkusPort;

		@Test
	void chatWorks() {
		var page = loadPage("%s_chatWorks".formatted(getClass().getSimpleName()));

		PlaywrightAssertions.assertThat(page).hasTitle("Miles of Smiles");

		var openChatButton = page.locator(".bot-button");
		PlaywrightAssertions.assertThat(openChatButton).isVisible();
		openChatButton.click();

		PlaywrightAssertions.assertThat(page.getByText("Welcome to Miles of Smiles! How can I help you today?")).isVisible();
		assertThat(getChatResponseLocator(page).count()).isOne();

		var typeYourMessageHereField = page.getByPlaceholder("Type your message here...");
		PlaywrightAssertions.assertThat(typeYourMessageHereField).isVisible();
		typeYourMessageHereField.fill("I am customer id 1, Speedy McWheels. Please show me my bookings.");

		var sendQueryButton = page.locator("form").getByRole(AriaRole.BUTTON);
		PlaywrightAssertions.assertThat(sendQueryButton).isVisible();
		sendQueryButton.click();

		// Wait for the answer text to have at least one piece of text in the answer
		await()
			.atMost(Duration.ofMinutes(10))
			.until(() -> getChatResponseText(page).isPresent());

		assertThat(getChatResponseText(page)).isNotNull().isPresent();
		assertThat(getChatResponseLocator(page).count()).isGreaterThan(2);
	}

	private static Optional<String> getChatResponseText(Page page) {
		return getChatResponseLocator(page).all().stream()
			.map(Locator::textContent)
			.filter(Objects::nonNull)
			.map(String::strip)
			.filter(answer -> !"Welcome to Miles of Smiles! How can I help you today?".equals(answer))
			.filter(answer -> !"I am customer id 1, Speedy McWheels. Please show me my bookings.".equals(answer))
			.findFirst()
			.filter(s -> !s.isEmpty());
	}

	private static Locator getChatResponseLocator(Page page) {
		return page.locator("chat-bubble");
	}

	private String getUrl() {
		return "http://localhost:%d/".formatted(this.quarkusPort);
	}

	private Page loadPage(String testName) {
		var page = this.context.newPage();
		page.onClose(p -> saveVideoWithReadableName(p, testName));

		var response = page.navigate(getUrl());

		assertThat(response)
			.isNotNull()
			.extracting(Response::status)
			.isEqualTo(Status.OK.getStatusCode());

		page.waitForLoadState(LoadState.NETWORKIDLE);

		return page;
	}

	private void saveVideoWithReadableName(Page page, String testName) {
		var video = page.video();
		var path = video.path();

		if (Files.isRegularFile(path)) {
			path = path.getParent();
		}

		var saveAsPath = path.resolve("%s.webm".formatted(testName));

		// Save the video under a different filename
		video.saveAs(saveAsPath);

		// Delete the randomly generated one
		video.delete();
	}
}
