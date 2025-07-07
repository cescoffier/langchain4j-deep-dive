package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

@QuarkusMain
public class Langchain4jAiServiceStructuredOutput implements QuarkusApplication {

    @Inject
    ChatModel model;

    @Override
    public int run(String... args) {
        TriageService triageService = AiServices.create(TriageService.class, model);

        System.out.println("Answer 1: " + triageService.triage("It was a great experience!"));
        System.out.println("Answer 2: " + triageService.triage("It was a terrible experience!"));

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

    public static void main(String[] args) {
        io.quarkus.runtime.Quarkus.run(Langchain4jAiServiceStructuredOutput.class, args);
    }

}