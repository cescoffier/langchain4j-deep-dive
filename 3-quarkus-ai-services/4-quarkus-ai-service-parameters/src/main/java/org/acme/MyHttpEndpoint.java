package org.acme;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/")
public class MyHttpEndpoint {

    private final Assistant assistant;


    MyHttpEndpoint(Assistant assistant) {
        this.assistant = assistant;
    }


    record Question(int number, String player) {
    }

    @POST
    @Path("/nba")
    public List<String> question(Question question) {
        return assistant.ask(question.number, question.player);
    }

    @POST
    @Path("/nba-last")
    public Assistant.Entry questionComplex(Question question) {
        return assistant.ask(question);
    }

}
