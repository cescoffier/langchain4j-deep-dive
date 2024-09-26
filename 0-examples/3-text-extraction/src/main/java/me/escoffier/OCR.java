package me.escoffier;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.ImageUrl;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface OCR {

    @UserMessage("""
            You take an image in and output the text extracted from the image.
            The image is about: {description}.
            """)
    String process(String description, @ImageUrl Image image);

}
