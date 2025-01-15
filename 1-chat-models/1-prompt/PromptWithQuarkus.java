//usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS io.quarkus.platform:quarkus-bom:3.17.7@pom
//DEPS io.quarkiverse.langchain4j:quarkus-langchain4j-openai:0.23.3
//JAVAC_OPTIONS -parameters
//JAVA_OPTIONS -Djava.util.logging.manager=org.jboss.logmanager.LogManager
//FILES application.properties

import io.quarkus.runtime.Startup;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkus.runtime.Quarkus;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.QuarkusApplication;
import jakarta.enterprise.context.control.ActivateRequestContext;
import io.smallrye.mutiny.Multi;

import java.util.concurrent.CountDownLatch;

import dev.langchain4j.model.chat.ChatLanguageModel;


@QuarkusMain
public class PromptWithQuarkus implements QuarkusApplication {
    private final PromptA promptA;
    private final PromptB promptB;
    private final PromptC promptC;
    private final ChatLanguageModel model;

    public PromptWithQuarkus(PromptA promptA, PromptB promptB, PromptC promptC, ChatLanguageModel model) {
        this.promptA = promptA;
        this.promptB = promptB;
        this.promptC = promptC;
        this.model = model;
    }

    @Override
    @ActivateRequestContext
    public int run(String... args) {
        // Use chat model to generate a response
        String answerA1 = model.generate("Say Hello World");
        System.out.println("Answer A1: " + answerA1);

        // Use an AI service to generate a response
//        String answerA2 = promptA.ask("Say Hello");
//        System.out.println("Answer A2: " + answerA2);

        // Use an AI service to generate a response selecting a specific model
//        String answerB = promptB.ask("Name five words that developers hate to hear most");
//        System.out.println("Answer B: " + answerB);

        // Streaming demo
//        Multi<String> answersC = promptC.ask("Write a poem about unicorns and grizzly bears");
//        CountDownLatch latch = new CountDownLatch(1);
//        System.out.println("Answer C: ");
//        answersC.subscribe().with(answerC -> {
//            System.out.println("\t" + answerC);
//        }, f -> {
//            System.out.println("Whoopsie!");
//        }, () -> latch.countDown());
//
//
//        try {
//            latch.await();
//        } catch (Exception e) {
//            // ignore me.
//        }
        return 0;
    }


    @RegisterAiService
    static interface PromptA {
        String ask(String prompt);
    }

    @RegisterAiService(modelName = "prompt-b")
    static interface PromptB {
        String ask(String prompt);
    }

    @RegisterAiService
    static interface PromptC {
        Multi<String> ask(String prompt);
    }
}
