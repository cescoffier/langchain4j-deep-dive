package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;


@QuarkusMain
public class Langchain4jAiServiceTools implements QuarkusApplication {

    @Inject
    ChatModel model;

    @Override
    public int run(String... args) {
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .tools(new Calculator())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();
        System.out.println("What's the length of 'Hello, world!'?");
        System.out.println("Answer 1: " + assistant.answer("What's the length of 'Hello, world!'?"));
        System.out.println("-------------------------------------");
        System.out.println("Can you sum 10 and 20?");
        System.out.println("Answer 2: " + assistant.answer("Can you sum 10 and 20?"));
        System.out.println("-------------------------------------");
        String question = "What is the square root of the sum of the numbers of letters in the words \"hello\" and \"world\"?";
        System.out.println(question);
        System.out.println("Answer 3: " + assistant.answer(question));

        return 0;
    }

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

    interface Assistant {
        String answer(String question);
    }
}