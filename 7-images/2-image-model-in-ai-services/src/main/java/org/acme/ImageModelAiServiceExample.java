package org.acme;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.ImageUrl;
import io.quarkiverse.langchain4j.RegisterAiService;


@QuarkusMain
public class ImageModelAiServiceExample implements QuarkusApplication {

    @Inject
    ImageGenerator generator;

    @Inject
    ImageDescriber describer;

    @Override
    public int run(String... args) throws IOException {
        var prompt = "Generate a picture of a rabbit going to Devoxx. The rabbit should be wearing a Quarkus tee-shirt.";
        var response = generator.generate(prompt);
        var file = Paths.get("rabbit-at-devoxx.jpg");

        Files.copy(response.url().toURL().openStream(), file, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("File: " + file.toAbsolutePath());
        System.out.println("Image Description: " + describer.describe(response.url()));

        return 0;
    }

    @RegisterAiService
    @ApplicationScoped
    public interface ImageGenerator {
        Image generate(@UserMessage String userMessage);
    }

    @RegisterAiService
    @ApplicationScoped
    public interface ImageDescriber {
        @UserMessage("Describe the given image")
        String describe(@ImageUrl URI url);
    }
}
