package com.kraemer;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(modelName = "m1", chatMemoryProviderSupplier = CustomMessageWindowProvider.class)
public interface MyAiChatService {

    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
