package me.escoffier;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.time.Duration;

import jakarta.ws.rs.core.Response.Status;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.bootstrap.classloading.QuarkusClassLoader;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;

@QuarkusTest
class OCRResourceTests {
	private static final String FILENAME = "handwritten_text.jpg";

	@BeforeAll
	static void beforeAll() {
		enableLoggingOfRequestAndResponseIfValidationFails();

		// Need to increase the rest-assured timeouts
		var timeout = Duration.ofMinutes(5).toMillis();
		var reqConfig = RequestConfig.custom()
			.setConnectTimeout((int) timeout)
			.setSocketTimeout((int) timeout)
			.build();

		var httpClientFactory = HttpClientConfig.httpClientConfig()
			.httpClientFactory(() -> HttpClientBuilder.create()
				.setDefaultRequestConfig(reqConfig)
				.build()
			);

		RestAssured.config = config().httpClient(httpClientFactory);
	}

	@Test
	void ocrWorks() {
		var text = given()
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