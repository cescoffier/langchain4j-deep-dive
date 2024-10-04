package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;


@QuarkusMain
public class Messages implements QuarkusApplication {

    @Inject
    ChatLanguageModel model;

    @Override
    public int run(String... args) {
        var system = new SystemMessage("You are Georgios, all your answers should be using the Java language using greek letters");
        //ChatMessage system = new SystemMessage("You are Clement, all your answer should be boring and long");
        var user = new UserMessage("Say Hello World");
        var response = model.generate(system, user);

        System.out.println("Answer: " + response.content().text());
        return 0;
    }
}