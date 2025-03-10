package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junitpioneer.jupiter.RetryingTest;

@QuarkusMainTest
class Langchain4jMcpExternalServerTests {
	private static final Path PYTHON_FILE = Paths.get("playground", "sum.py").toAbsolutePath();

	@BeforeAll
	static void setup() {
		// delete the file in case it already exists from previous runs
		PYTHON_FILE.toFile().delete();
	}

	@Test
	@Launch
	@RetryingTest(3)
	@DisabledIfEnvironmentVariable(named = "CI", matches = "true", disabledReason = "Disabled on CI because it requires a very large model")
	void itWorks(LaunchResult launchResult) {
		assertThat(PYTHON_FILE).isNotEmptyFile();
	}
}