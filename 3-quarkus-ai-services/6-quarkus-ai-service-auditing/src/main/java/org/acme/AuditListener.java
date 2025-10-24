package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.logging.Log;

import dev.langchain4j.observability.api.event.AiServiceCompletedEvent;
import dev.langchain4j.observability.api.event.AiServiceErrorEvent;
import dev.langchain4j.observability.api.event.AiServiceResponseReceivedEvent;
import dev.langchain4j.observability.api.event.AiServiceStartedEvent;
import dev.langchain4j.observability.api.event.ToolExecutedEvent;

@ApplicationScoped
public class AuditListener {
  public void aiServiceStarted(@Observes AiServiceStartedEvent e) {
    Log.infof(
			"Initial messages:\nsource: %s\nsystemMessage: %s\nuserMessage: %s",
	    e.invocationContext(),
	    e.systemMessage(),
	    e.userMessage()
    );
  }

  public void aiServiceCompleted(@Observes AiServiceCompletedEvent e) {
    Log.infof(
			"LLM interaction complete:\nsource: %s\nresult: %s",
	    e.invocationContext(),
	    e.result()
    );
  }

  public void aiServiceError(@Observes AiServiceErrorEvent e) {
    Log.infof(
			"LLM interaction failed:\nsource: %s\nfailure: %s",
	    e.invocationContext(),
	    e.error().getMessage()
    );
  }

  public void aiServiceResponseReceived(@Observes AiServiceResponseReceivedEvent e) {
    Log.infof(
			"Response from LLM received:\nsource: %s\nresponse: %s",
	    e.invocationContext(),
      e.response().aiMessage().text()
    );
  }

  public void toolExecuted(@Observes ToolExecutedEvent e) {
    Log.infof(
			"Tool executed:\nsource: %s\nrequest: %s(%s)\nresult: %s",
	    e.invocationContext(),
	    e.request().name(),
	    e.request().arguments(),
      e.resultText()
    );
  }
}
