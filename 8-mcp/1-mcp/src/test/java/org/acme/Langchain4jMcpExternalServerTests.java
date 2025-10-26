package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junitpioneer.jupiter.RetryingTest;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

@QuarkusMainTest
@EnabledIf("isNpmAvailable")
class Langchain4jMcpExternalServerTests {
	private static final Path PYTHON_FILE = Langchain4jMcpExternalServer.PLAYGROUND_DIR.resolve("sum.py").toAbsolutePath();

	@BeforeAll
	static void setup() {
		PYTHON_FILE.toFile().delete();
	}

	@Test
	@Launch
	@RetryingTest(3)
	@DisabledIfEnvironmentVariable(named = "CI", matches = "true", disabledReason = "Disabled on CI because it requires a very large model")
	void itWorks(LaunchResult launchResult) {
		assertThat(PYTHON_FILE).isNotEmptyFile();
	}

  static boolean isNpmAvailable() {
    try {
      return new ProcessBuilder("npm", "--version")
          .start()
          .waitFor() == 0;
    }
    catch (Exception e) {
      return false;
    }
  }
}