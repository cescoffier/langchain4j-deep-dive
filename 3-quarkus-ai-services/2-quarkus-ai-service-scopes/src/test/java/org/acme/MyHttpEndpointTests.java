package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MyHttpEndpointTests {
	@Test
	void shortMemory() {
		given()
			.body("My name is Clement")
			.post("/short").then()
			.statusCode(200)
			.body(not(blankOrNullString()));

		given()
			.body("What's my name?")
			.post("/short").then()
			.statusCode(200)
			.body(not(containsString("Clement")));
	}

	@Test
	void longMemory() {
		given()
			.body("My name is Clement")
			.post("/long").then()
			.statusCode(200)
			.body(not(blankOrNullString()));

		given()
			.body("What's my name?")
			.post("/long").then()
			.statusCode(200)
			.body(containsString("Clement"));
	}
}