package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.List;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import org.acme.WebSearchExampleTests.MockedWebSearchTestProfile;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.WebSearchInformationResult;
import dev.langchain4j.web.search.WebSearchOrganicResult;
import dev.langchain4j.web.search.WebSearchRequest;
import dev.langchain4j.web.search.WebSearchResults;

@QuarkusMainTest
@TestProfile(MockedWebSearchTestProfile.class)
class WebSearchExampleTests {
  @Test
	@Launch
	void itWorks(LaunchResult launchResult) {
		assertThat(launchResult.getOutput())
			.isNotNull()
			.contains("Was there a Java version released in 2024?")
			.contains("No tools:")
			.contains("With tools:")
			.contains("Java 22")
			.contains("Java 23")
			.contains("March 19, 2024")
			.contains("September 17, 2024");
	}

	public static class MockedWebSearchTestProfile implements QuarkusTestProfile {
		@Override
		public Set<Class<?>> getEnabledAlternatives() {
			return Set.of(MockedWebSearchEngine.class);
		}
	}

	@Alternative
	@ApplicationScoped
	public static class MockedWebSearchEngine implements WebSearchEngine {
		@Override
		public WebSearchResults search(WebSearchRequest webSearchRequest) {
			return WebSearchResults.from(
				WebSearchInformationResult.from(1L),
				List.of(
					WebSearchOrganicResult.from(
						"Mock Search API",
						URI.create("https://java.net"),
						"Java 22 was released on March 19, 2024. Java 23 was released on September 17, 2024",
						null)
				)
			);
		}
	}
}