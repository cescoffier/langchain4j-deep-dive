package org.acme;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.acme.Assistant.Entry;
import org.acme.MyHttpEndpoint.Question;
import org.junitpioneer.jupiter.RetryingTest;

import io.quarkus.test.junit.QuarkusTest;

import io.restassured.http.ContentType;

@QuarkusTest
class MyHttpEndpointTests {
	@RetryingTest(3)
	void nba() {
		var entry = given()
			.body(new Question("Larry Bird"))
			.contentType(ContentType.JSON)
			.post("/nba").then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.extract().body()
			.as(Entry.class);

		assertThat(entry)
			.isNotNull()
			.satisfies(e -> {
				assertThat(e.team()).isNotBlank();
				assertThat(e.years()).isNotBlank();
			});
	}
}