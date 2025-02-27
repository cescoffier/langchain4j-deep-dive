package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

@QuarkusMainTest
class Langchain4jMcpExternalServerTests {
	private static final Path PYTHON_FILE = Paths.get("playground", "sum.py");

	@BeforeAll
	static void setup() {
		PYTHON_FILE.toAbsolutePath().toFile().delete();
	}

	@Test
	@Launch
	void itWorks(LaunchResult launchResult) {
		assertThat(PYTHON_FILE.toAbsolutePath()).isNotEmptyFile();
	}
}