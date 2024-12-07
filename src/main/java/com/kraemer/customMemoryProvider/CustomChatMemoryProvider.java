package com.kraemer.customMemoryProvider;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.inject.Singleton;

@Singleton

public class CustomChatMemoryProvider implements ChatMemoryProvider {
    private final ChatMemoryStore store;

    public CustomChatMemoryProvider() {
        this.store = createCustomStore();
    }

    private static ChatMemoryStore createCustomStore() {
        return null;
    }

    @Override
    public ChatMemory get(Object memoryId) {
        return createCustomMemory(memoryId);
    }

    private static ChatMemory createCustomMemory(Object memoryId) {
        return null;
    }
}
