package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.logging.Log;

import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailRequest;
import dev.langchain4j.guardrail.OutputGuardrailResult;


@ApplicationScoped
public class UppercaseOutputGuardrail implements OutputGuardrail {

    @Override
    public OutputGuardrailResult validate(OutputGuardrailRequest params) {
        var message = params.responseFromLLM().aiMessage().text();
        Log.infof("Response is: %s", message);

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
