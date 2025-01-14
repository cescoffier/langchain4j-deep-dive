package me.escoffier;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

@QuarkusMainTest
class SummarizingCommandTests {
	private static final String URL = "https://quarkus.io/guides/command-mode-reference";

	@Test
	@Launch(URL)
	void itWorks(LaunchResult launchResult) {
		assertThat(launchResult.getOutput())
			.isNotNull()
			.contains("Summarizing article %s".formatted(URL))
			.contains("Extracting content...")
			.contains("Summarizing content...")
			.contains("Summary: ");
	}
}