package org.acme;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;


@QuarkusMain
public class Langchain4jAiServiceMesssages implements QuarkusApplication {

    @Inject
    ChatLanguageModel model;

    @Override
    public int run(String... args) {
        Assistant assistant = AiServices.create(Assistant.class, model);
        System.out.println(assistant.answer("Say Hello World"));

        return 0;
    }

    interface Assistant {
        @SystemMessage("You are a Shakespeare, all your responses must be in iambic pentameter.")
        String answer(String question);
    }
}