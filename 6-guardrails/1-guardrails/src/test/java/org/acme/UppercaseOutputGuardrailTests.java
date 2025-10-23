package org.acme;

import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.quarkus.test.junit.QuarkusTest;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.guardrail.ChatExecutor;
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
        .responseFromLLM(ChatResponse.builder().aiMessage(AiMessage.from("THIS IS ALL UPPERCASE")).build())
        .requestParams(GuardrailRequestParams.builder().userMessageTemplate("").variables(Map.of()).build())
        .chatExecutor(new NoopChatExecutor())
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
        .responseFromLLM(ChatResponse.builder().aiMessage(AiMessage.from(output)).build())
        .requestParams(GuardrailRequestParams.builder().userMessageTemplate("").variables(Map.of()).build())
        .chatExecutor(new NoopChatExecutor())
        .build();
		GuardrailAssertions.assertThat(uppercaseOutputGuardrail.validate(request))
			.hasResult(Result.FATAL)
			.hasSingleFailureWithMessageAndReprompt("The output must be in uppercase.", "Please provide the output in uppercase.");
	}

  class NoopChatExecutor implements ChatExecutor {
    @Override
    public ChatResponse execute() {
        return execute(List.of());
    }

    @Override
    public ChatResponse execute(List<ChatMessage> chatMessages) {
        return null;
    }
}
}