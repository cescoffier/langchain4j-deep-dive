package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.logging.Log;

import io.quarkiverse.langchain4j.guardrails.OutputGuardrail;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailParams;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;

@ApplicationScoped
public class UppercaseOutputGuardrail implements OutputGuardrail {

    @Override
    public OutputGuardrailResult validate(OutputGuardrailParams params) {
        Log.infof("Response is: %s", params.responseFromLLM().text());

        var message = params.responseFromLLM().text();
        var isAllUppercase = message.chars()
                                    .filter(Character::isLetter)
                                    .allMatch(Character::isUpperCase);

        if (isAllUppercase) {
            Log.info("success");
            return success();
        } else {
            return reprompt("The output must be in uppercase.", "Please provide the output in uppercase.");
        }
    }

}
