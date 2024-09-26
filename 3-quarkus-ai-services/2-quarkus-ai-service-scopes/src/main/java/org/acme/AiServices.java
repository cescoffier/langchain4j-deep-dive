package org.acme;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;


public class AiServices {

    @RegisterAiService
    @RequestScoped
    interface ShortMemoryAssistant {
        String answer(String question);
    }

    @RegisterAiService
    @ApplicationScoped
    interface LongMemoryAssistant {
        String answer(@MemoryId int id, @UserMessage String question);
    }

    @RegisterAiService
    @SessionScoped
    interface ConversationalMemoryAssistant {
        String answer(String question);
    }
}