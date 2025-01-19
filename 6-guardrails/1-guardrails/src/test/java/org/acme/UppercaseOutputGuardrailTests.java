package org.acme;

import java.util.Map;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.quarkus.test.junit.QuarkusTest;

import dev.langchain4j.data.message.AiMessage;
import io.quarkiverse.langchain4j.guardrails.GuardrailAssertions;
import io.quarkiverse.langchain4j.guardrails.GuardrailResult.Result;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailParams;

@QuarkusTest
class UppercaseOutputGuardrailTests {
	@Inject
	UppercaseOutputGuardrail uppercaseOutputGuardrail;

	@Test
	void success() {
		var params = new OutputGuardrailParams(AiMessage.from("THIS IS ALL UPPERCASE"), null, null, null, Map.of());
		GuardrailAssertions.assertThat(uppercaseOutputGuardrail.validate(params))
			.isSuccessful();
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"EVERYTHING IS UPPERCASE EXCEPT FOR oNE CHARACTER",
		"this is all lowercase"
	})
	void guardrailReprompt(String output) {
		var params = new OutputGuardrailParams(AiMessage.from(output), null, null, null, Map.of());
		GuardrailAssertions.assertThat(uppercaseOutputGuardrail.validate(params))
			.hasResult(Result.FATAL)
			.hasSingleFailureWithMessageAndReprompt("The output must be in uppercase.", "Please provide the output in uppercase.");
	}
}