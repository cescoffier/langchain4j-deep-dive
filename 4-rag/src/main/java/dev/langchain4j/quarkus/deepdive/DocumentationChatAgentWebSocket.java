package dev.langchain4j.quarkus.deepdive;

import io.quarkus.logging.Log;
import io.quarkus.websockets.next.OnError;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;

import io.smallrye.mutiny.Multi;

@WebSocket(path = "/doc-support")
public class DocumentationChatAgentWebSocket {

    private final DocumentationChatAgent documentationChatAgent;

    public DocumentationChatAgentWebSocket(DocumentationChatAgent documentationChatAgent) {
        this.documentationChatAgent = documentationChatAgent;
    }

    @OnOpen
    public String onOpen() {
        return "Welcome to Quarkus Help! I'm Quarky. How can I make your day more Quarkus-y?";
    }

    @OnError
    public String onError(Throwable error) {
        var message = "error occurred during chat: %s".formatted(error.getMessage());
        Log.error(message, error);

        return "I'm sorry, but an %s".formatted(message);
    }

    @OnTextMessage
    public Multi<String> onTextMessage(String message) {
        return this.documentationChatAgent.chat(message);
    }
}
