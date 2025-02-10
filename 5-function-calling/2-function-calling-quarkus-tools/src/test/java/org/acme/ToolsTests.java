package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

@QuarkusMainTest
class ToolsTests {
  @Test
	@Launch
	void itWorks(LaunchResult launchResult) {
		assertThat(launchResult.getOutput())
			.isNotNull()
			.contains("What is the square root of the sum of the numbers of letters in the words \"hello\" and \"world\"?")
			.contains("Called stringLength('")
			.contains("Called sqrt(");
	}
}