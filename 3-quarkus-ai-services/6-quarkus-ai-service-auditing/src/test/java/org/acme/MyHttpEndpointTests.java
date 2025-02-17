package org.acme;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.logging.LogManager;
import java.util.logging.LogRecord;

import org.acme.Assistant.Entry;
import org.acme.MyHttpEndpoint.Question;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.InMemoryLogHandler;
import io.quarkus.test.junit.QuarkusTest;

import io.restassured.http.ContentType;

@QuarkusTest
class MyHttpEndpointTests {
	private static final InMemoryLogHandler LOG_HANDLER = new InMemoryLogHandler(logRecord -> true);

	@BeforeAll
	static void beforeAll() {
		LogManager.getLogManager().getLogger("org.acme").addHandler(LOG_HANDLER);
	}

	@AfterAll
	static void afterAll() {
		LogManager.getLogManager().getLogger("org.acme").removeHandler(LOG_HANDLER);
	}

	@Test
	void auditListenerInvoked() {
		var entry = given()
			.body(new Question("Larry Bird"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.post("/nba").then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.extract().body()
			.as(Entry.class);

		assertThat(entry)
			.isNotNull()
			.satisfies(
				e -> assertThat(e.team()).isNotBlank(),
				e -> assertThat(e.years()).isNotBlank()
			);

		var logMessages = LOG_HANDLER.getRecords()
			.stream()
			.filter(logRecord -> logRecord.getLoggerName().equals(AuditListener.class.getName()))
			.map(LogRecord::getMessage);

		assertThat(logMessages)
			.hasSizeGreaterThanOrEqualTo(3)
			.containsSequence(
				"Initial messages:\nsource: %s\nsystemMessage: %s\nuserMessage: %s",
				"Response from LLM received:\nsource: %s\nresponse: %s",
				"LLM interaction complete:\nsource: %s\nresult: %s"
			);
	}
}