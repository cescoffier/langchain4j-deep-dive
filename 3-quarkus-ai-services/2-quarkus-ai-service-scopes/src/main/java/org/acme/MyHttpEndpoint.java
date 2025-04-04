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
        // id is hard coded 123 but should differentiate between say users
        // maybe passing the user id
        return longMemoryAssistant.answer(123, question);
    }
}
