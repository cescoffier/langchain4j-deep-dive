package dev.langchain4j.quarkus.deepdive;

import jakarta.enterprise.context.SessionScoped;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.smallrye.mutiny.Multi;

@SessionScoped
@RegisterAiService
public interface DocumentationChatAgent {

    @SystemMessage("""
            You are Quarky, a friendly question and answer agent who knows everything there is to know about Quarkus, the Supersonic Subatomic Java Framework.
            
            You are friendly, polite, and concise.
            
            If the question is unrelated to Quarkus or Java, you should politely reply that perhaps they should use Google instead.
            
            Please provide references to where your responses were found.
            """)
    Multi<String> chat(String userMessage);
}
