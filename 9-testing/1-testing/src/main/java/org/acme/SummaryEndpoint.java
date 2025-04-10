package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/summary")
public class SummaryEndpoint {

    final SummarizationService ai;

    public SummaryEndpoint(SummarizationService ai) {
        this.ai = ai;
    }

    @POST
    public String summarize(String input) {
        return ai.summarize(input);
    }

}
