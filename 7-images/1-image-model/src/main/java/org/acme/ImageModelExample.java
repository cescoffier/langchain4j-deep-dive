package org.acme;


import dev.langchain4j.model.image.ImageModel;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.apache.commons.io.FileUtils;

import java.io.File;
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
        var file = new File("devoxx.jpg");
        FileUtils.copyURLToFile(response.content().url().toURL(), file);

        return 0;
    }
}