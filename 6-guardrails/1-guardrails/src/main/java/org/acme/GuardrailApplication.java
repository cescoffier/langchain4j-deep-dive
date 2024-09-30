package org.acme;


import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.control.ActivateRequestContext;


@QuarkusMain
public class GuardrailApplication implements QuarkusApplication {

    private final Assistant assistant;

    public GuardrailApplication(Assistant assistant) {
        this.assistant = assistant;
    }

    @ActivateRequestContext
    @Override
    public int run(String... args) {
        //System.out.println(assistant.chat("Say Hello world."));
        System.out.println(assistant.chat("SAY HELLO WORLD"));
        return 0;
    }


}