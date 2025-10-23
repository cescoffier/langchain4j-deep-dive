package org.acme;

import jakarta.enterprise.context.control.ActivateRequestContext;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

@QuarkusMain
public class GuardrailApplication implements QuarkusApplication {

    @ActivateRequestContext
    @Override
    public int run(String... args) {
      var chatModel = OpenAiChatModel.builder()
          .apiKey(System.getenv("OPENAI_API_KEY"))
          .logRequests(true)
          .logResponses(true)
          .build();

        var assistant = AiServices.create(Assistant.class, chatModel);
        System.out.println("Answer: " + assistant.chat("Say Hello world."));
//        System.out.println("Answer: " + assistant.chat("SAY HELLO WORLD"));
//        System.out.println("Answer: "  + assistant.chat("SAY HELLO WORLD IN ALL LOWER CASE"));
        return 0;
    }


}