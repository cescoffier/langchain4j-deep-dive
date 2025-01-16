package org.acme;

import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import io.quarkiverse.langchain4j.RegisterAiService;

@QuarkusMain
public class QuarkusAiServiceSimple implements QuarkusApplication {

    @Inject
    Assistant assistant;

    @Override
    @ActivateRequestContext
    public int run(String... args) {
        System.out.println("Answer 1: " + assistant.answer("My name is Clement, can you say \"Hello World\" in Greek to make Georgios happy?"));
        System.out.println("Answer 2: " + assistant.answer("What's my name?"));
        return 0;
    }

    @RegisterAiService
    interface Assistant {
        String answer(String question);
    }
}