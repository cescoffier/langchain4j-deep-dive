package io.quarkiverse.langchain4j.sample.chatbot;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.logging.Log;

@Path("/alerts")
public class WeatherAlertsResource {

    @Inject
    AiWeatherService aiWeatherService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getWeatherAlerts(@QueryParam("state") String state) {
        if (state == null || state.isEmpty()) {
            throw new IllegalArgumentException("State parameter is required");
        }

        String weather = aiWeatherService.getWeatherAlerts(state);
        Log.info(weather);
        return weather;
    }
}
