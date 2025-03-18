package org.acme;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junitpioneer.jupiter.RetryingTest;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusMainTest
class Langchain4jAiServiceStructuredOutputTests {
    @Launch
    @RetryingTest(maxAttempts = 4)
    void itWorks(LaunchResult launchResult) {
        assertThat(launchResult.getOutput())
                .isNotNull()
                .contains("Answer 1: ")
                .contains("Answer 2: ");
    }
}