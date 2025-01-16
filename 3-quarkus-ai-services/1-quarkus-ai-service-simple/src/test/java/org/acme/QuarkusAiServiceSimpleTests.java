package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

@QuarkusMainTest
class QuarkusAiServiceSimpleTests {
	@Test
	@Launch
	void itWorks(LaunchResult result) {
		assertThat(result.getOutput())
			.isNotNull()
			.contains("Answer 1: ")
			.contains("Answer 2: ");
	}
}