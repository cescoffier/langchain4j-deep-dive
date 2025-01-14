package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.logging.Log;

import dev.langchain4j.data.message.UserMessage;
import io.quarkiverse.langchain4j.guardrails.InputGuardrail;
import io.quarkiverse.langchain4j.guardrails.InputGuardrailResult;

@ApplicationScoped
public class UppercaseInputGuardrail implements InputGuardrail {

    @Override
    public InputGuardrailResult validate(UserMessage userMessage) {
        var message = userMessage.singleText();
        Log.infof("User message: %s", message);
        var isAllUppercase = message.chars()
                                    .filter(Character::isLetter)
                                    .allMatch(Character::isUpperCase);

        return isAllUppercase ?
               success() :
               failure("The input must be in uppercase.");
    }
}
