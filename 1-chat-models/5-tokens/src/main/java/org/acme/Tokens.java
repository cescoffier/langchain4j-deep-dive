package org.acme;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.TokenUsage;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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

        NumberFormat formatter = new DecimalFormat("#0.000000");
        System.out.println("Estimation of the price: " + formatter.format(estimatedPrice(response.tokenUsage())) + " USD");

        return 0;
    }


    /**
     * Pricing for GPT-40 - 26/09/2024
     * - $5.00 / 1M input tokens
     * - $15.00 / 1M output tokens
     */
    private double estimatedPrice(TokenUsage tokenUsage) {
        return tokenUsage.inputTokenCount() * 5.00 / 1_000_000 +
                tokenUsage.outputTokenCount() * 15.00 / 1_000_000;
    }

}