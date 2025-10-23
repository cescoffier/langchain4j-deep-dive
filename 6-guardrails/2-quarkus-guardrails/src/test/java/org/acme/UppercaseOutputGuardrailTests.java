package org.acme;

import java.util.Map;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.quarkus.test.junit.QuarkusTest;

import io.quarkiverse.langchain4j.guardrails.NoopChatExecutor;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.GuardrailRequestParams;
import dev.langchain4j.guardrail.GuardrailResult.Result;
import dev.langchain4j.guardrail.OutputGuardrailRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.test.guardrail.GuardrailAssertions;

@QuarkusTest
class UppercaseOutputGuardrailTests {
	@Inject
	UppercaseOutputGuardrail uppercaseOutputGuardrail;

	@Test
	void success() {
    var request = OutputGuardrailRequest.builder()
        .chatExecutor(new NoopChatExecutor())
        .responseFromLLM(ChatResponse.builder().aiMessage(AiMessage.from("THIS IS ALL UPPERCASE")).build())
        .requestParams(GuardrailRequestParams.builder().userMessageTemplate("").variables(Map.of()).build())
        .build();

		GuardrailAssertions.assertThat(uppercaseOutputGuardrail.validate(request))
			.isSuccessful();
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"EVERYTHING IS UPPERCASE EXCEPT FOR oNE CHARACTER",
		"this is all lowercase"
	})
	void guardrailReprompt(String output) {
    var request = OutputGuardrailRequest.builder()
        .chatExecutor(new NoopChatExecutor())
        .responseFromLLM(ChatResponse.builder().aiMessage(AiMessage.from(output)).build())
        .requestParams(GuardrailRequestParams.builder().userMessageTemplate("").variables(Map.of()).build())
        .build();
		GuardrailAssertions.assertThat(uppercaseOutputGuardrail.validate(request))
			.hasResult(Result.FATAL)
			.hasSingleFailureWithMessageAndReprompt("The output must be in uppercase.", "Please provide the output in uppercase.");
	}
}