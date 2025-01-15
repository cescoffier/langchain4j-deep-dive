package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;


@QuarkusMain
public class Langchain4jAiServiceParameters implements QuarkusApplication {

    @Inject
    ChatLanguageModel model;

    @Override
    public int run(String... args) {
        Poet poet = AiServices.create(Poet.class, model);
        System.out.println("Poem: " + poet.answer("Devoxx"));

        return 0;
    }

    interface Poet {
        @SystemMessage("You are a Shakespeare, all your responses must be in iambic pentameter.")
        @UserMessage("Write a poem about {{topic}}. It should not be more than 5 lines long.")
        String answer(@V("topic") String topic);
    }
}