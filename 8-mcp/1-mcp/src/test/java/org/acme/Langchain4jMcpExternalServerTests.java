package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

import java.io.File;

@QuarkusMainTest
class Langchain4jMcpExternalServerTests {

	@BeforeAll
	static void setup() {
		// delete the file in case it already exists from previous runs
		new File("playground/sum.py").delete();
	}

	@Test
	@Launch
	void itWorks(LaunchResult launchResult) {
		File expectedPythonScript = new File("playground/sum.py");
		assertThat(expectedPythonScript).exists();
		assertThat(expectedPythonScript).isNotEmpty();
	}
}