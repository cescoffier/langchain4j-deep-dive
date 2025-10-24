package dev.langchain4j.quarkus.deepdive;

import jakarta.enterprise.context.ApplicationScoped;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailResult;

@ApplicationScoped
public class PromptInjectionGuard implements InputGuardrail {

    private final PromptInjectionDetectionService service;

    public PromptInjectionGuard(PromptInjectionDetectionService service) {
        this.service = service;
    }

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        double result = service.isInjection(userMessage.singleText());
        if (result > 0.7) {
            return failure("Prompt injection detected");
        }
        return success();
    }
}
