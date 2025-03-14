package org.acme;

import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@QuarkusMain
public class MyMcpClientApp implements QuarkusApplication {

    @Inject
    Assistant assistant;

    @Override
    public int run(String... args) {
        String prompt = """
                What time is it?
                """;
        String answer = assistant.answer(prompt);
        System.out.println(answer);
        return 0;
    }

    @RegisterAiService
    @ApplicationScoped
    interface Assistant {
        String answer(String question);
    }
}
