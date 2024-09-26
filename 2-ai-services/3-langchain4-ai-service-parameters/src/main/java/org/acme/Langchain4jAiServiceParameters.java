package org.acme;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;


@QuarkusMain
public class Langchain4jAiServiceParameters implements QuarkusApplication {

    @Inject
    ChatLanguageModel model;

    @Override
    public int run(String... args) {
        Poet poet = AiServices.create(Poet.class, model);
        System.out.println(poet.answer("Devoxx"));

        return 0;
    }

    interface Poet {
        @SystemMessage("You are a Shakespeare, all your response must be in iambic pentameter.")
        @UserMessage("Write a poem about {{topic}}. It should not be more than 5 lines long.")
        String answer(@V("topic") String topic);
    }
}