package org.acme.examples;

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
        var result = service.isInjection(userMessage.singleText());

        return (result > 0.7) ?
               failure("Prompt injection detected") :
               success();
    }
}
