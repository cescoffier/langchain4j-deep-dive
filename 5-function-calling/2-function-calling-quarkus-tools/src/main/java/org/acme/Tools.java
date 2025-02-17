package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.agent.tool.Tool;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;

@QuarkusMain
public class Tools implements QuarkusApplication {

    private final Assistant assistant;

    public Tools(Assistant assistant) {
        this.assistant = assistant;
    }

    @Override
    public int run(String... args) {
        String question = "What is the square root of the sum of the numbers of letters in the words \"hello\" and \"world\"?";
        System.out.println(question);
        String answer = assistant.chat(question);
        System.out.println(answer);

        return 0;
    }

    @RegisterAiService
    @ApplicationScoped
    interface Assistant {

        @ToolBox(Calculator.class)
        String chat(String userMessage);
    }

    @ApplicationScoped
    static class Calculator {

        @Tool("Calculates the length of a string")
        int stringLength(String s) {
            System.out.println("Called stringLength('%s')".formatted(s));
            return s.length();
        }

        @Tool("Calculates the sum of two numbers")
        int add(int a, int b) {
            System.out.println("Called add(%d, %d)".formatted(a, b));
            return a + b;
        }

        @Tool("Calculates the square root of a number")
        double sqrt(int x) {
            System.out.println("Called sqrt(%d)".formatted(x));
            return Math.sqrt(x);
        }
    }
}