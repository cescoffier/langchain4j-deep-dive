package org.acme;

import java.util.Collections;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.logging.Log;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;

@ApplicationScoped
public class RedisMemoryStore implements ChatMemoryStore {

    public static final String KEY_PREFIX = "chat-memory:";

    private final ValueCommands<String, List<ChatMessage>> valueCommands;

    RedisMemoryStore(RedisDataSource redisDataSource) {
        this.valueCommands = redisDataSource.value(new TypeReference<>() {});
    }

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Log.infof("Getting messages for memory id %s", memoryId.toString());
        List<ChatMessage> chatMessages = valueCommands.get(KEY_PREFIX + memoryId);
        if (chatMessages != null) {
            return chatMessages;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        Log.infof("Update memory id %s", memoryId.toString());
        valueCommands.set(KEY_PREFIX + memoryId, messages);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        // I do not want to delete my memory!
    }
}
