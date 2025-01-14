package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.TokenUsage;

@QuarkusMain
public class Tokens implements QuarkusApplication {

    @Inject
    ChatLanguageModel model;

    @Override
    public int run(String... args) {
        var memory = MessageWindowChatMemory.builder()
                .id("user-id")
                .maxMessages(3) // Only 3 messages will be stored
                .build();

        memory.add(new SystemMessage("You are a useful AI assistant."));
        memory.add(new UserMessage("Hello, my name is Clement and I live in Valence, France"));
        memory.add(new UserMessage("What is my name?"));

        var response = model.generate(memory.messages());
        System.out.println("Answer 1: " + response.content().text());

        System.out.println("Input token: " + response.tokenUsage().inputTokenCount());
        System.out.println("Output token: " + response.tokenUsage().outputTokenCount());
        System.out.println("Total token: " + response.tokenUsage().totalTokenCount());
        System.out.println("Estimation of the price: %.7f USD".formatted(estimatedPrice(response.tokenUsage())));

        return 0;
    }

    /**
     * Pricing for GPT-4o - As of January 14, 2025
     * - $2.50 / 1M input tokens
     * - $10.00 / 1M output tokens
     */
    private static double estimatedPrice(TokenUsage tokenUsage) {
        return (tokenUsage.inputTokenCount() * (2.50 / 1_000_000)) +
          (tokenUsage.outputTokenCount() * (10.00 / 1_000_000));
    }
}