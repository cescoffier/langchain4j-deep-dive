package org.acme;


import dev.langchain4j.data.image.Image;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


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
        var file = new File("rabbit-at-devoxx.jpg");
        Files.copy(response.url().toURL().openStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("file://" + file.getAbsolutePath());

        System.out.println(describer.describe(response));

        return 0;
    }

    @RegisterAiService
    @ApplicationScoped
    public interface ImageGenerator {

        Image generate(String userMessage);



    }

    @RegisterAiService
    @ApplicationScoped
    public interface ImageDescriber {
        @UserMessage("""
                Describe the given message.
                """)
        String describe(Image image);
    }
}
