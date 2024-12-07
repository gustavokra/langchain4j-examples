package com.kraemer;

import java.util.function.Supplier;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

public class CustomMessageWindowProvider implements Supplier<ChatMemoryProvider> {
    
    private final InMemoryChatMemoryStore store = new InMemoryChatMemoryStore();

    @Override
    public CustomChatMemoryProvider get() {
        return new CustomChatMemoryProvider() {

            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .maxMessages(5)
                        .id(memoryId)
                        .chatMemoryStore(store)
                        .build();
            }
        };
    }

}
