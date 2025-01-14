package io.quarkiverse.langchain4j.sample;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.quarkus.logging.Log;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

import dev.langchain4j.agent.tool.Tool;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;

@ApplicationScoped
public class EmailService {

    @Inject
    Mailer mailer;

    @Tool("send the given content by email")
    @WithSpan("EmailService.sendAnEmail")
    public void sendAnEmail(@SpanAttribute("arg.content") String content) {
        Log.infof("Sending an email: %s", content);
        mailer.send(Mail.withText("sendMeALetter@quarkus.io", "A poem for you", content));
    }

}
