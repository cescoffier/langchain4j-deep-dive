package me.escoffier;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import jakarta.ws.rs.core.Response.Status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.bootstrap.classloading.QuarkusClassLoader;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;

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