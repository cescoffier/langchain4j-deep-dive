package org.acme;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/nba")
public class MyHttpEndpoint {

    private final Assistant assistant;

    MyHttpEndpoint(Assistant assistant) {
        this.assistant = assistant;
    }

    record Question(String player) {
    }

    @POST
    public Assistant.Entry ask(Question question) {
        return assistant.ask(question);
    }

}
