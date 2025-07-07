package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;

@QuarkusMain
public class ChatMemory implements QuarkusApplication {

    @Inject
    ChatModel model;

    @Override
    public int run(String... args) {
        var memory = MessageWindowChatMemory.builder()
                .id("user-id")
                .maxMessages(3) // Only 3 messages will be stored
                .build();

        memory.add(new SystemMessage("You are a useful AI assistant."));
        memory.add(new UserMessage("Hello, my name is Clement and I live in Valence, France"));
        memory.add(new UserMessage("What is my name?"));

        var response = model.chat(memory.messages());
        System.out.println("Answer 1: " + response.aiMessage().text());

        memory.add(response.aiMessage());

        memory.add(new UserMessage("Where do I live?"));
        response = model.chat(memory.messages());
        System.out.println("Answer 2: " + response.aiMessage().text());

        return 0;
    }

    public static void main(String[] args) {
        io.quarkus.runtime.Quarkus.run(ChatMemory.class, args);
    }

}