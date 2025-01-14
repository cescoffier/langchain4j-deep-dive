package org.acme.examples;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.logging.Log;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.content.Content;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrail;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailParams;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;

@ApplicationScoped
public class HallucinationGuard implements OutputGuardrail {
    @Inject
    EmbeddingModel embedding;

    @ConfigProperty(name = "hallucination.threshold", defaultValue = "0.7")
    double threshold;

    @Override
    public OutputGuardrailResult validate(OutputGuardrailParams params) {
        Response<Embedding> embeddingOfTheResponse = embedding.embed(params.responseFromLLM().text());

        if ((params.augmentationResult() == null) || params.augmentationResult().contents().isEmpty()) {
            Log.info("No content to validate against");
            return success();
        }

        float[] vectorOfTheResponse = embeddingOfTheResponse.content().vector();

        for (Content content : params.augmentationResult().contents()) {
            Response<Embedding> embeddingOfTheContent = embedding.embed(content.textSegment());
            float[] vectorOfTheContent = embeddingOfTheContent.content().vector();
            double distance = cosineDistance(vectorOfTheResponse, vectorOfTheContent);

            if (distance < threshold) {
                Log.info("Passed hallucination guardrail: " + distance);
                return success();
            }
        }

        return reprompt("Hallucination detected", "Make sure you use the given documents to produce the response");
    }

    public static double cosineDistance(float[] vector1, float[] vector2) {
        var dotProduct = 0.0;
        var normA = 0.0;
        var normB = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            normA += Math.pow(vector1[i], 2);
            normB += Math.pow(vector2[i], 2);
        }

        var cosineSimilarity = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        return 1.0 - cosineSimilarity;
    }
}