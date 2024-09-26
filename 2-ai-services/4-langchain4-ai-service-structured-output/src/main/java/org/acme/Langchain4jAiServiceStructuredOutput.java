package org.acme;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;


@QuarkusMain
public class Langchain4jAiServiceStructuredOutput implements QuarkusApplication {

    @Inject
    ChatLanguageModel model;

    @Override
    public int run(String... args) {
        TriageService triageService = AiServices.create(TriageService.class, model);
        System.out.println(triageService.triage("It was a great experience!"));
        System.out.println(triageService.triage("It was a terrible experience!"));

        return 0;
    }

    enum Sentiment {
        POSITIVE,
        NEGATIVE,
    }

    record Feedback(Sentiment sentiment, String summary) {}

    interface TriageService {
        @SystemMessage("You are an AI that need to triage user feedback.")
        @UserMessage("""
                Analyze the given feedback, and determine if it is positive, or negative.
                Then, provide a summary of the feedback.
                
                {{feedback}}
                """)
        Feedback triage(@V("feedback") String feedback);
    }
}