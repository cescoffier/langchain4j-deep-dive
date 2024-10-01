package org.acme;


import dev.langchain4j.service.UserMessage;
import dev.langchain4j.web.search.WebSearchTool;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.ApplicationScoped;


@QuarkusMain
public class WebSearchExample implements QuarkusApplication {

    private final Assistant assistant;

    public WebSearchExample(Assistant assistant) {
        this.assistant = assistant;
    }

    @Override
    public int run(String... args) {
        String question = "Was there a Java version released in 2024?";
        String answer = assistant.chatNoTools(question);
        System.out.println("No tools: " + answer);


        answer = assistant.chat(question);
        System.out.println("-------------------");
        System.out.println("With tools: " + answer);

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

        String chatNoTools(String userMessage);
    }
}