package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import org.acme.ImageModelAiServiceExample.ImageDescriber;
import org.acme.ImageModelAiServiceExampleTests.MockedImageDescriberTestProfile;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.output.Response;
import io.quarkiverse.langchain4j.ImageUrl;

@QuarkusMainTest
@TestProfile(MockedImageDescriberTestProfile.class)
class ImageModelAiServiceExampleTests {
	@AfterAll
	static void afterAll() throws IOException {
		Files.deleteIfExists(Paths.get("rabbit-at-devoxx.jpg"));
	}

	@Test
	@Launch
	void itWorks(LaunchResult launchResult) {
		assertThat(launchResult.getOutput())
			.isNotNull()
			.contains("File: ")
			.contains("Image Description: Image Description");
	}

	public static class MockedImageDescriberTestProfile implements QuarkusTestProfile {
		@Override
		public Set<Class<?>> getEnabledAlternatives() {
			return Set.of(DummyImageDescriber.class, DummyImageModel.class);
		}
	}

	@Alternative
	@ApplicationScoped
	public static class DummyImageDescriber implements ImageDescriber {
		@Override
		public String describe(@ImageUrl URI url) {
			return "Image Description";
		}
	}

	@Alternative
	@ApplicationScoped
	public static class DummyImageModel implements ImageModel {
			@Override
			public Response<Image> generate(String prompt) {
				return Response.from(Image.builder()
				                          .url("https://raw.githubusercontent.com/langchain4j/langchain4j/6023e32425002ca7bc70f87ef61b81513d1a5f45/docs/static/img/docusaurus-social-card.jpg")
				                          .mimeType("image/jpeg")
				                          .build());
			}
		}
}