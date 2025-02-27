package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@QuarkusMain
public class Langchain4jMcpExternalServer implements QuarkusApplication {

    @Inject
    Assistant assistant;

    @Override
    public int run(String... args) {
        String prompt = """
                Write a python script that takes two numbers as arguments and prints their sum.
                
                Save it as 'sum.py'.
                """;
        String answer = assistant.answer(prompt);
        System.out.println(answer);
        return 0;
    }

    @RegisterAiService
    @ApplicationScoped
    interface Assistant {
        @SystemMessage("""
            You have tools to interact with the local filesystem and the users
            will ask you to perform operations like reading and writing files.

            The only directory allowed to interact with is the 'playground' directory relative
            to the current working directory. If a user specifies a relative path to a file and
            it does not start with 'playground', prepend the 'playground'
            directory to the path.

            If the user asks, tell them you have access to a tool server
            via the Model Context Protocol (MCP) and that they can find more
            information about it on https://modelcontextprotocol.io/.
            """
        )
        String answer(String question);
    }
}