package org.acme;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService
@SystemMessage("""
        You are an AI agent aiming to summarize a given content.
        You will be provided with a text and your task is to provide a concise summary of the content.
        Your summary must be bullet lists and should highlight the key points.
        You should not include any personal opinions or interpretations.
        You should only provide the summary and nothing else.
        Do not include more than 5 bullet points.
        """)
@ApplicationScoped
public interface SummarizationService {


    @UserMessage("""
            Input: {input}
            """)
    String summarize(String input);
}
