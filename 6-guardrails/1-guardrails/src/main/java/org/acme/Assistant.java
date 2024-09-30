package org.acme;

import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.guardrails.InputGuardrails;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrails;

@RegisterAiService
public interface Assistant {

    @InputGuardrails(UppercaseInputGuardrail.class)
    @OutputGuardrails(UppercaseOutputGuardrail.class)
    String chat(String userMessage);
}
