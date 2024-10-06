package org.acme;

import io.quarkiverse.langchain4j.guardrails.OutputGuardrail;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UppercaseOutputGuardrail implements OutputGuardrail {

    @Override
    public OutputGuardrailResult validate(OutputGuardrailParams params) {
        System.out.println("response is: " + params.responseFromLLM().text());
        var message = params.responseFromLLM().text();
        var isAllUppercase = message.chars().filter(Character::isLetter).allMatch(Character::isUpperCase);
        if (isAllUppercase) {
            System.out.println("success");
            return success();
        } else {
            return reprompt("The output must be in uppercase.", "Please provide the output in uppercase.");
        }
    }

}
