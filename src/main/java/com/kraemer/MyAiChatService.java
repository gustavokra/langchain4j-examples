package com.kraemer;

import com.kraemer.repositories.CustomerRepository;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;

// tools = { CustomerRepository.class },
@RegisterAiService
public interface MyAiChatService {

    @ToolBox(value = { CustomerRepository.class })
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
