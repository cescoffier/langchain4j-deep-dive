package org.acme;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.RequestScoped;

@RegisterAiService
@RequestScoped
interface Assistant {
    String answer(@MemoryId int id, @UserMessage String question);
}
