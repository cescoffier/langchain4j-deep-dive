package org.acme;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.tool.ToolProvider;
import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;

import java.util.List;


@QuarkusMain
public class Langchain4jMcpExternalServer implements QuarkusApplication {

    @Inject
    ChatLanguageModel model;

    @Override
    public int run(String... args) {
        McpTransport transport = new StdioMcpTransport.Builder()
                .command(List.of("npm", "exec", "@modelcontextprotocol/server-filesystem@0.6.2", "playground"))
                .logEvents(true)
                .build();
        McpClient client = new DefaultMcpClient.Builder()
                .transport(transport)
                .build();
        ToolProvider mcpToolProvider = new McpToolProvider.Builder()
                .mcpClients(List.of(client))
                .build();
        MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
                .id("user-id")
                .maxMessages(3)
                .build();
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .toolProvider(mcpToolProvider)
                .chatMemory(memory)
                .build();

        String prompt = "Write a python script that takes two numbers as arguments and prints their sum." +
                        "Save it as 'sum.py'.";
        String answer = assistant.answer(prompt);
        System.out.println(answer);
        return 0;
    }

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