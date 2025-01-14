package dev.langchain4j.quarkus.deepdive;

import jakarta.enterprise.context.control.ActivateRequestContext;

import io.quarkus.logging.Log;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;

@WebSocket(path = "/customer-support-agent")
@ActivateRequestContext
public class CustomerSupportAgentWebSocket {

    private final CustomerSupportAgent customerSupportAgent;

    public CustomerSupportAgentWebSocket(CustomerSupportAgent customerSupportAgent) {
        this.customerSupportAgent = customerSupportAgent;
    }

    @OnOpen
    public String onOpen() {
        return "Welcome to Miles of Smiles! How can I help you today?";
    }

    @OnTextMessage
    public String onTextMessage(String message) {
        try {
            return customerSupportAgent.chat(message);
        } catch (Exception e) {
            Log.error("Error calling the LLM", e);
            return "Sorry, I am unable to process your request at the moment. Please try again later.";
        }
    }
}
