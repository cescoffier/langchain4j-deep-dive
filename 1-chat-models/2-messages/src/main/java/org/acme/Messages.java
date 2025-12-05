package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;

@QuarkusMain
public class Messages implements QuarkusApplication {

    @Inject
    ChatModel model;

    @Override
    public int run(String... args) {
//        var system = new SystemMessage("You are Georgios, all your answers should be using the Java language using greek letters");
//        system = new SystemMessage("""
//                You are at RivieraDev, all your responses should use the Java language
//                with method names and variables in French.
//                Be sarcastic and pendantic on your method and variable names when possible
//                and as long as it compiles.
//                """);
        var system = new SystemMessage("You are Eric, all your answers should be boring and long");
//        system = new SystemMessage("You are Emmanuel, all your answers should be an architectural decision record (ADR)");
        var user = new UserMessage("Say Hello World");
        var response = model.chat(system, user);

        System.out.println("Answer: " + response.aiMessage().text());
        return 0;
    }

    public static void main(String[] args) {
        io.quarkus.runtime.Quarkus.run(Messages.class, args);
    }
}