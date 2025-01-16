package org.acme;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.acme.Assistant.Entry;
import org.acme.MyHttpEndpoint.Question;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import io.restassured.http.ContentType;

@QuarkusTest
class MyHttpEndpointTests {
	@Test
	void nba() {
		var answers = given()
			.body(new Question(2, "Michael Jordan"))
			.contentType(ContentType.JSON)
			.post("/nba").then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.extract().body()
			.jsonPath().getList(".", String.class);

		assertThat(answers)
			.isNotNull()
			.hasSize(2);
	}

	@Test
	void nbaLast() {
		var entry = given()
			.body(new Question(1, "Larry Bird"))
			.contentType(ContentType.JSON)
			.post("/nba-last").then()
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