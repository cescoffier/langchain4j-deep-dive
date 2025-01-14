package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.web.search.WebSearchTool;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;


@QuarkusMain
public class WebSearchExample implements QuarkusApplication {

    private final Assistant assistant;

    public WebSearchExample(Assistant assistant) {
        this.assistant = assistant;
    }

    @Override
    public int run(String... args) {
        String question = "Was there a Java version released in 2024?";
        var answerNoTools = assistant.chatNoTools(question);
        var answerWithTools = assistant.chat(question);

        System.out.println(question);
        System.out.println("\nNo tools:");
        System.out.println(answerNoTools);

        System.out.println("-------------------");
        System.out.println("\nWith tools:");
        System.out.println(answerWithTools);

        return 0;
    }

    @RegisterAiService
    @ApplicationScoped
    interface Assistant {

        @UserMessage("""
                Search for information about the user query: {query}, and answer the question.
                """)
        @ToolBox(WebSearchTool.class)
        String chat(String query);

        @UserMessage("""
                Search for information about the user query: {query}, and answer the question.
                """)
        String chatNoTools(String query);
    }
}