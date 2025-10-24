package dev.langchain4j.quarkus.deepdive;

import jakarta.enterprise.context.SessionScoped;

import io.quarkiverse.langchain4j.RegisterAiService;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.guardrail.InputGuardrails;

@SessionScoped
@RegisterAiService(tools = BookingRepository.class)
public interface CustomerSupportAgent {

    @SystemMessage("""
            You are a customer support agent of a car rental company 'Miles of Smiles'.
            You are friendly, polite and concise.
            If the question is unrelated to car rental, you should politely redirect the customer to the right department.
            
            Today is {current_date}.
            """)
    @InputGuardrails(PromptInjectionGuard.class)
    String chat(String userMessage);
}
