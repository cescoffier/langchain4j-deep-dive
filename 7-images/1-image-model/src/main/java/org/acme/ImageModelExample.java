package org.acme;


import dev.langchain4j.model.image.ImageModel;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

import java.io.IOException;


@QuarkusMain
public class ImageModelExample implements QuarkusApplication {

    @Inject
    ImageModel model;

    @Override
    public int run(String... args) throws IOException {
        var prompt = "Generate a picture showing an American (Eric), a Greek (Georgios) and a French (Clement) presenting Langchain4J at Devoxx, " +
                "with a crowd of attendees behind them. The picture should be in a cartoon style. The three presenters should wear Quarkus tee-shirts";
        var response = model.generate(prompt);
        System.out.println(response.content().url());

        return 0;
    }
}
