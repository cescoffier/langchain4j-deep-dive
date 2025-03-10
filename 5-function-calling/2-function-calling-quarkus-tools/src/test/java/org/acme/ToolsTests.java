package org.acme;

import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusMainTest
class ToolsTests {
    @Test
    @Launch
    @RetryingTest(3)
    void itWorks(LaunchResult launchResult) {
        assertThat(launchResult.getOutput())
                .isNotNull()
                .contains("What is the square root of the sum of the numbers of letters in the words \"hello\" and \"world\"?")
                .contains("stringLength")
                .contains("sqrt");
    }
}