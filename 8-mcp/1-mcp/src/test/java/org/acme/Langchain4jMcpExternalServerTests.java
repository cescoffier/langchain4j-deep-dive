package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

@QuarkusMainTest
class Langchain4jMcpExternalServerTests {
	private static final Path PYTHON_FILE = Langchain4jMcpExternalServer.PLAYGROUND_DIR.resolve("sum.py").toAbsolutePath();

	@BeforeAll
	static void setup() {
		PYTHON_FILE.toFile().delete();
	}

	@Test
	@Launch
	@RetryingTest(3)
	void itWorks(LaunchResult launchResult) {
		assertThat(PYTHON_FILE).isNotEmptyFile();
	}
}