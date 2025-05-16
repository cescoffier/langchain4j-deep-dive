package org.acme;

import dev.langchain4j.model.chat.ChatModel;
import io.quarkiverse.langchain4j.scorer.junit5.AiScorer;
import io.quarkiverse.langchain4j.scorer.junit5.SampleLocation;
import io.quarkiverse.langchain4j.scorer.junit5.ScorerConfiguration;
import io.quarkiverse.langchain4j.testing.scorer.EvaluationReport;
import io.quarkiverse.langchain4j.testing.scorer.Samples;
import io.quarkiverse.langchain4j.testing.scorer.Scorer;
import io.quarkiverse.langchain4j.testing.scorer.judge.AiJudgeStrategy;
import io.quarkiverse.langchain4j.testing.scorer.similarity.SemanticSimilarityStrategy;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@QuarkusTest
@AiScorer
@Disabled
public class EvaluationTest {

    @Inject
    SummarizationService service;

    @Inject
    ChatModel judge;

    @Test
    void evaluateUsingJudge(@ScorerConfiguration(concurrency = 5) Scorer scorer,
                            @SampleLocation("src/test/resources/samples.yaml") Samples<String> samples) throws IOException {
        AiJudgeStrategy strategy = new AiJudgeStrategy(judge);
        EvaluationReport<String> report = scorer.evaluate(samples, p -> {
            var i = p.get(0, String.class);
            return service.summarize(i);
        }, strategy);


        report.writeReport(new File("target/evaluation-judge-report.md"));
        assertThat(report.score()).isGreaterThan(20.0);
    }

    @Test
    void evaluateUsingEmbeddingModel(@ScorerConfiguration(concurrency = 5) Scorer scorer,
                                     @SampleLocation("src/test/resources/samples.yaml") Samples<String> samples) throws IOException {
        var strategy = new SemanticSimilarityStrategy(0.7);
        EvaluationReport<String> report = scorer.evaluate(samples, p -> {
            var i = p.get(0, String.class);
            return service.summarize(i);
        }, strategy);


        report.writeReport(new File("target/evaluation-embedding-report.md"));
        assertThat(report.score()).isGreaterThan(70.0);
    }
}
