package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junitpioneer.jupiter.RetryingTest;

@QuarkusMainTest
class Langchain4jAiServiceToolsTests {
	@Test
	@Launch
	@RetryingTest(3)
	void itWorks(LaunchResult launchResult) {
		assertThat(launchResult.getOutput())
			.isNotNull()
			.contains("Answer 1: ")
			.contains("Answer 2: ")
			.contains("What is the square root of the sum of the numbers of letters in the words \"hello\" and \"world\"?")
			.contains("Answer 3: ")
			.contains("stringLength")
			.contains("add")
			.contains("sqrt");
	}
}