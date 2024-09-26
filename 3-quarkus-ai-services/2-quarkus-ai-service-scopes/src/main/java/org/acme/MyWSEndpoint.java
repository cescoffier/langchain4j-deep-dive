package org.acme;

import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;

@WebSocket(path = "/ws")
public class MyWSEndpoint {

    private final AiServices.ConversationalMemoryAssistant assistant;

    MyWSEndpoint(AiServices.ConversationalMemoryAssistant assistant) {
        this.assistant = assistant;
    }

    @OnTextMessage
    String reply(String question) {
        return assistant.answer(question);
    }

}
