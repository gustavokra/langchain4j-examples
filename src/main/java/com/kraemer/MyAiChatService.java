package com.kraemer;

import com.kraemer.customMemoryProvider.CustomMessageWindowProvider;
import com.kraemer.repositories.CustomerRepository;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;

// tools = { CustomerRepository.class },
@RegisterAiService(modelName = "m1", 
        chatMemoryProviderSupplier = CustomMessageWindowProvider.class)
public interface MyAiChatService {

    @ToolBox(value = { CustomerRepository.class })
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
