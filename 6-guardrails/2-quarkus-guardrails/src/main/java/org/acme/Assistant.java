package org.acme;

import io.quarkiverse.langchain4j.RegisterAiService;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.guardrail.InputGuardrails;
import dev.langchain4j.service.guardrail.OutputGuardrails;

@RegisterAiService
public interface Assistant {

    @InputGuardrails(UppercaseInputGuardrail.class)
    @OutputGuardrails(UppercaseOutputGuardrail.class)
    String chat(@UserMessage String userMessage);
}
