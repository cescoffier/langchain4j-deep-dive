package org.acme;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;

@QuarkusMain
public class ManualMemory implements QuarkusApplication {

    @Inject
    ChatLanguageModel model;

    @Override
    public int run(String... args) {
        List<ChatMessage> memory = new ArrayList<>(List.of(
                new SystemMessage("You are a useful AI assistant."),
                new UserMessage("Hello, my name is Clement."),
                new UserMessage("What is my name?")
        ));

        var response = model.chat(memory);

        System.out.println("Answer 1: " + response.aiMessage().text());
        memory.add(response.aiMessage());

        memory.add(new UserMessage("What's my name again?"));
        response = model.chat(memory);

        System.out.println("Answer 2: " + response.aiMessage().text());

        var m = new UserMessage("What's my name again?");

        // Notice we didn't add the response or the new message to the memory
        response = model.chat(m); // No memory
        System.out.println("Answer 3: " + response.aiMessage().text());

        return 0;
    }

}