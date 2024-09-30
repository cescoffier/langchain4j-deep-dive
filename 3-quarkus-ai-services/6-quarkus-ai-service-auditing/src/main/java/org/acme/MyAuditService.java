package org.acme;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.output.Response;
import io.quarkiverse.langchain4j.audit.Audit;
import io.quarkiverse.langchain4j.audit.AuditService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class MyAuditService implements AuditService {
    @Override
    public Audit create(Audit.CreateInfo createInfo) {
        return new MyAudit(createInfo);
    }

    @Override
    public void complete(Audit audit) {
        // Store the audit in a database
    }


    private static class MyAudit extends Audit {

        private final String id;
        private final String method;

        public MyAudit(CreateInfo createInfo) {
            super(createInfo);
            id = UUID.randomUUID().toString();
            method = createInfo.interfaceName() + "#" + createInfo.methodName();
        }

        @Override
        public void initialMessages(Optional<SystemMessage> systemMessage, UserMessage userMessage) {
            Log.infof("[%s - %s] Initial messages: systemMessage=%s, userMessage=%s", method, id, systemMessage, userMessage);
        }

        @Override
        public void addLLMToApplicationMessage(Response<AiMessage> response) {
            Log.infof("[%s - %s] LLM to application message: response=%s", method, id, response);
        }


        @Override
        public void onFailure(Exception e) {
            Log.errorf(e, "[%s - %s] Failure", method, id);
        }

        @Override
        public void onCompletion(Object result) {
            Log.infof("[%s - %s] Completion: result=%s", method, id, result.toString());
        }
    }

}
