package org.acme;

import jakarta.enterprise.context.control.ActivateRequestContext;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class GuardrailApplication implements QuarkusApplication {

    private final Assistant assistant;

    public GuardrailApplication(Assistant assistant) {
        this.assistant = assistant;
    }

    @ActivateRequestContext
    @Override
    public int run(String... args) {
//        System.out.println("Answer: " + assistant.chat("Say Hello world."));
//        System.out.println("Answer: " + assistant.chat("SAY HELLO WORLD"));
        System.out.println("Answer: "  + assistant.chat("SAY HELLO WORLD IN ALL LOWER CASE"));
        return 0;
    }


}