package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;


@QuarkusMain
public class Langchain4jAiServiceSimple implements QuarkusApplication {

    @Inject
    ChatModel model;

    @Override
    public int run(String... args) {
        Assistant assistant = AiServices.create(Assistant.class, model);
        System.out.println("Answer: " + assistant.answer("Say Hello World"));

        return 0;
    }

    interface Assistant {
        String answer(String question);
    }
}