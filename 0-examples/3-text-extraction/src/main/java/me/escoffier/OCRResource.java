package me.escoffier;

import dev.langchain4j.data.image.Image;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Path("/ocr")
public class OCRResource {

    @Inject OCR ocr;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String create(@RestForm File file, @RestForm String description) throws IOException {
        Log.infof("Converting image...");
        var image = Image.builder().base64Data(encodeFileToBase64(file))
                .mimeType("image/jpeg").build();
        Log.info("Processing image... ");
        return ocr.process(description, image);
    }

    public static String encodeFileToBase64(File file) throws IOException {
        var content = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(content);
    }
}
