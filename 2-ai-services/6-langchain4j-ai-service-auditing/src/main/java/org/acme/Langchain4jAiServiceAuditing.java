package org.acme;

import jakarta.inject.Inject;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.observability.api.event.AiServiceCompletedEvent;
import dev.langchain4j.observability.api.event.AiServiceErrorEvent;
import dev.langchain4j.observability.api.event.AiServiceResponseReceivedEvent;
import dev.langchain4j.observability.api.event.AiServiceStartedEvent;
import dev.langchain4j.observability.api.event.InputGuardrailExecutedEvent;
import dev.langchain4j.observability.api.event.OutputGuardrailExecutedEvent;
import dev.langchain4j.observability.api.event.ToolExecutedEvent;
import dev.langchain4j.observability.api.listener.AiServiceCompletedListener;
import dev.langchain4j.observability.api.listener.AiServiceErrorListener;
import dev.langchain4j.observability.api.listener.AiServiceResponseReceivedListener;
import dev.langchain4j.observability.api.listener.AiServiceStartedListener;
import dev.langchain4j.observability.api.listener.InputGuardrailExecutedListener;
import dev.langchain4j.observability.api.listener.OutputGuardrailExecutedListener;
import dev.langchain4j.observability.api.listener.ToolExecutedEventListener;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;

@QuarkusMain
public class Langchain4jAiServiceAuditing implements QuarkusApplication {
  @Inject
  ChatModel model;

  @Override
  public int run(String... args) {
    var assistant = AiServices.builder(Assistant.class)
        .chatModel(model)
        .registerListeners(
            new MyAiServiceStartedListener(),
            new MyAiServiceResponseReceivedListener(),
            new MyAiServiceErrorListener(),
            new MyAiServiceCompletedListener(),
            new MyToolExecutedListener(),
            new MyInputGuardrailExecutedListener(),
            new MyOutputGuardrailExecutedListener()
        )
        .build();

    System.out.println("Answer: " + assistant.ask("Hello what is your name?"));
    return 0;
  }

  interface Assistant {
    @SystemMessage("You are a very useful assistant. Your name is Roger.")
    String ask(String question);
  }

  class MyAiServiceStartedListener implements AiServiceStartedListener {
    @Override
    public void onEvent(AiServiceStartedEvent event) {
      System.out.println("Got AiServiceStartedEvent:");
    }
  }

  class MyAiServiceResponseReceivedListener implements AiServiceResponseReceivedListener {
    @Override
    public void onEvent(AiServiceResponseReceivedEvent event) {
      System.out.println("Got AiServiceResponseReceivedEvent:");
    }
  }

  class MyAiServiceErrorListener implements AiServiceErrorListener {
    @Override
    public void onEvent(AiServiceErrorEvent event) {
      System.out.println("Got AiServiceErrorEvent:");
    }
  }

  class MyAiServiceCompletedListener implements AiServiceCompletedListener {
    @Override
    public void onEvent(AiServiceCompletedEvent event) {
      System.out.println("Got AiServiceCompletedEvent:");
    }
  }

  class MyToolExecutedListener implements ToolExecutedEventListener {
    @Override
    public void onEvent(ToolExecutedEvent event) {
      System.out.println("Got ToolExecutedEvent:");
    }
  }

  class MyInputGuardrailExecutedListener implements InputGuardrailExecutedListener {
    @Override
    public void onEvent(InputGuardrailExecutedEvent event) {
      System.out.println("Got InputGuardrailExecutedEvent:");
    }
  }

  class MyOutputGuardrailExecutedListener implements OutputGuardrailExecutedListener {
    @Override
    public void onEvent(OutputGuardrailExecutedEvent event) {
      System.out.println("Got OutputGuardrailExecutedEvent:");
    }
  }
}
