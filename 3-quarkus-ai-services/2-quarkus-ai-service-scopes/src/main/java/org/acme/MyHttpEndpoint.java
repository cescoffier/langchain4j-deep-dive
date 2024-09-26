package org.acme;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/")
public class MyHttpEndpoint {

    private final AiServices.ShortMemoryAssistant shortMemoryAssistant;
    private final AiServices.LongMemoryAssistant longMemoryAssistant;

    MyHttpEndpoint(AiServices.ShortMemoryAssistant shortMemoryAssistant, AiServices.LongMemoryAssistant longMemoryAssistant) {
        this.shortMemoryAssistant = shortMemoryAssistant;
        this.longMemoryAssistant = longMemoryAssistant;
    }

    @POST
    @Path("/short")
    public String shortMemory(String question) {
        return shortMemoryAssistant.answer(question);
    }

    @POST
    @Path("/long")
    public String longMemory(String question) {
        return longMemoryAssistant.answer(123, question);
    }
}
