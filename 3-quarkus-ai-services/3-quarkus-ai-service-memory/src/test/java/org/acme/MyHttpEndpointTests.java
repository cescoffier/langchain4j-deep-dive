package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class MyHttpEndpointTests {
	@Test
	void itWorks() {
		given()
			.body("My name is Clement")
			.queryParam("id", 1)
			.post("/memory").then()
			.statusCode(200)
			.body(not(blankOrNullString()));

		given()
			.body("What's my name?")
			.queryParam("id", 1)
			.post("/memory").then()
			.statusCode(200)
			.body(containsString("Clement"));

		given()
			.body("What's my name?")
			.queryParam("id", 2)
			.post("/memory").then()
			.statusCode(200)
			.body(not(containsString("Clement")));
	}
}