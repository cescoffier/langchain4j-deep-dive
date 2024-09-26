package org.acme;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;


@QuarkusMain
public class Langchain4jAiServiceSimple implements QuarkusApplication {

    @Inject
    ChatLanguageModel model;

    @Override
    public int run(String... args) {
        Assistant assistant = AiServices.create(Assistant.class, model);
        System.out.println(assistant.answer("Say Hello World"));

        return 0;
    }

    interface Assistant {
        String answer(String question);
    }
}