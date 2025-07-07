package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.model.image.ImageModel;

@QuarkusMain
public class ImageModelExample implements QuarkusApplication {

    @Inject
    ImageModel model;

    @Override
    public int run(String... args) {
        var prompt = "Generate a picture showing an American (Eric), a Greek (Georgios) and a French (Clement) presenting Langchain4J at Devoxx, " +
                "with a crowd of attendees behind them. The picture should be in a cartoon style. The three presenters should wear Quarkus tee-shirts";
        var response = model.generate(prompt);
        System.out.println("Response: " + response.content().url());

        return 0;
    }

    public static void main(String[] args) {
        io.quarkus.runtime.Quarkus.run(ImageModelExample.class, args);
    }
}
