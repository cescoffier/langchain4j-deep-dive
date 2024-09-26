package org.acme;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/")
public class MyHttpEndpoint {

    private final Assistant assistant;


    MyHttpEndpoint(Assistant assistant) {
        this.assistant = assistant;
    }

    @POST
    @Path("/memory")
    public String shortMemory(@RestQuery int id, String question) {
        return assistant.answer(id, question);
    }

}
