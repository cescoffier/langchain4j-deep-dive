package me.escoffier;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.not;

import java.time.Duration;

import jakarta.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.bootstrap.classloading.QuarkusClassLoader;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;

import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;

@QuarkusTest
class OCRResourceTests {
	private static final String FILENAME = "handwritten_text.jpg";

	@BeforeAll
	static void beforeAll() {
		enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	void ocrWorks() {
		int timeout = (int) Duration.ofMinutes(5).toMillis();
		var config = config()
			.httpClient(
				HttpClientConfig.httpClientConfig()
				                .setParam("http.connection.timeout", timeout)
				                .setParam("http.socket.timeout", timeout)
			);

		var text = given()
			.config(config)
			.multiPart(
				"file",
				FILENAME,
				QuarkusClassLoader.getSystemResourceAsStream("images/%s".formatted(FILENAME)),
				"image/jpeg"
			)
			.formParam("description", "Handwritten notes")
			.when().post("/ocr").then()
			.statusCode(Status.OK.getStatusCode())
			.contentType(ContentType.TEXT)
			.body(not(blankOrNullString()))
			.extract().asString();

		Log.infof("Got OCR:\n%s", text);
	}
}