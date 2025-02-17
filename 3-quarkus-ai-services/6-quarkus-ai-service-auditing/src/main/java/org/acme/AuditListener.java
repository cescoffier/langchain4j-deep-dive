package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import io.quarkus.logging.Log;

import io.quarkiverse.langchain4j.audit.InitialMessagesCreatedEvent;
import io.quarkiverse.langchain4j.audit.LLMInteractionCompleteEvent;
import io.quarkiverse.langchain4j.audit.LLMInteractionFailureEvent;
import io.quarkiverse.langchain4j.audit.ResponseFromLLMReceivedEvent;
import io.quarkiverse.langchain4j.audit.ToolExecutedEvent;

@ApplicationScoped
public class AuditListener {
  public void initialMessagesCreated(@Observes InitialMessagesCreatedEvent e) {
    Log.infof(
			"Initial messages:\nsource: %s\nsystemMessage: %s\nuserMessage: %s",
	    e.sourceInfo(),
	    e.systemMessage(),
	    e.userMessage()
    );
  }

  public void llmInteractionComplete(@Observes LLMInteractionCompleteEvent e) {
    Log.infof(
			"LLM interaction complete:\nsource: %s\nresult: %s",
	    e.sourceInfo(),
	    e.result()
    );
  }

  public void llmInteractionFailed(@Observes LLMInteractionFailureEvent e) {
    Log.infof(
			"LLM interaction failed:\nsource: %s\nfailure: %s",
	    e.sourceInfo(),
	    e.error().getMessage()
    );
  }

  public void responseFromLLMReceived(@Observes ResponseFromLLMReceivedEvent e) {
    Log.infof(
			"Response from LLM received:\nsource: %s\nresponse: %s",
	    e.sourceInfo(),
	    e.response().content().text()
    );
  }

  public void toolExecuted(@Observes ToolExecutedEvent e) {
    Log.infof(
			"Tool executed:\nsource: %s\nrequest: %s(%s)\nresult: %s",
	    e.sourceInfo(),
	    e.request().name(),
	    e.request().arguments(),
	    e.result()
    );
  }
}
