package com.kraemer.DesingningAIServices;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface AiChatWithMemoryService {
    
    @SystemMessage("Você é um programador profissional")
    public String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
