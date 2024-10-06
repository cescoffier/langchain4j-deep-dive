package org.acme;


import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;


@QuarkusMain
public class Langchain4jAiServiceChatMemory implements QuarkusApplication {

    @Inject
    ChatLanguageModel model;

    @Override
    public int run(String... args) {
        var memory = MessageWindowChatMemory.builder()
                .id("user-id")
                .maxMessages(3) // Only 3 messages will be stored
                .build();

        var assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .chatMemory(memory)
                .build();

        System.out.println(assistant.ask(
                "Hello, my name is Clement and I live in Valence, France. What is your name?"));

        System.out.println(assistant.ask("What's my name?"));

        System.out.println(assistant.ask("Where do I live?"));
        return 0;
    }

    interface Assistant {
        @SystemMessage("You are a very useful assistant. Your name is Roger.")
        String ask(String question);
    }
}