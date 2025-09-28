package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

@QuarkusMain
public class Langchain4jAiServiceMesssages implements QuarkusApplication {

    @Inject
    ChatModel model;

    @Override
    public int run(String... args) {
        Assistant assistant = AiServices.create(Assistant.class, model);
        System.out.println("Answer: " + assistant.answer("Say Hello World"));

        return 0;
    }

    interface Assistant {
        @SystemMessage("You are a Shakespeare, all your responses must be in iambic pentameter.")
        String answer(String question);
    }

    public static void main(String[] args) {
        io.quarkus.runtime.Quarkus.run(Langchain4jAiServiceMesssages.class, args);
    }
}