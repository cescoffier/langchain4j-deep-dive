package dev.langchain4j.quarkus.deepdive;

import io.quarkus.logging.Log;
import io.quarkus.websockets.next.OnClose;
import io.quarkus.websockets.next.OnError;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;

import io.smallrye.mutiny.Multi;

@WebSocket(path = "/doc-support")
public class DocumentationChatAgentWebSocket {
    private final DocumentationChatAgent documentationChatAgent;

    public DocumentationChatAgentWebSocket(DocumentationChatAgent documentationChatAgent) {
        this.documentationChatAgent = documentationChatAgent;
    }

    @OnOpen
    public String onOpen(WebSocketConnection connection) {
        Log.infof("Websocket connection %s opened", connection.id());
        return "Welcome to Quarkus Help! I'm Quarky. How can I make your day more Quarkus-y?";
    }

    @OnClose
    public void onClose(WebSocketConnection connection) {
        Log.infof("Websocket connection %s closed", connection.id());
    }

    @OnError
    public String onError(Throwable error) {
        var message = "error occurred during chat: %s".formatted(error.getMessage());
        Log.error(message, error);

        return "I'm sorry, but an %s".formatted(message);
    }

    @OnTextMessage
    public Multi<String> onTextMessage(String message) {
        Log.infof("Got chat query: %s", message);
        return this.documentationChatAgent.chat(message);
    }
}
