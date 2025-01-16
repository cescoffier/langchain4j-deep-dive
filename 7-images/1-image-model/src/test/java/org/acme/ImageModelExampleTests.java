package org.acme;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import org.acme.ImageModelExampleTests.MockedImageModelTestProfile;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.junit.main.Launch;
import io.quarkus.test.junit.main.LaunchResult;
import io.quarkus.test.junit.main.QuarkusMainTest;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.output.Response;

@QuarkusMainTest
@TestProfile(MockedImageModelTestProfile.class)
class ImageModelExampleTests {
  @Test
	@Launch
	void itWorks(LaunchResult launchResult) {
		assertThat(launchResult.getOutput())
			.isNotNull()
			.contains("Response: https://www.somewhere.com/image.png");
	}

	public static class MockedImageModelTestProfile implements QuarkusTestProfile {
		@Override
		public Set<Class<?>> getEnabledAlternatives() {
			return Set.of(DummyImageModel.class);
		}
	}

	@Alternative
	@ApplicationScoped
	public static class DummyImageModel implements ImageModel {
		@Override
		public Response<Image> generate(String prompt) {
			return Response.from(
				Image.builder()
				     .url("https://www.somewhere.com/image.png")
				     .build()
			);
		}
	}
}