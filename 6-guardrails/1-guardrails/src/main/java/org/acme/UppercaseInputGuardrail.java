package org.acme;

import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UppercaseInputGuardrail implements InputGuardrail {

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        var message = userMessage.singleText();
        var isAllUppercase = message.chars().filter(Character::isLetter).allMatch(Character::isUpperCase);
        if (isAllUppercase) {
            return success();
        } else {
            return failure("The input must be in uppercase.");
        }
    }
}
