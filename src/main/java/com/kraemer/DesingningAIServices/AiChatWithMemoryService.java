package com.kraemer.DesingningAIServices;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterAiService
public interface AiChatWithMemoryService {
    
    @SystemMessage("Você sua profissão é um programador, muito profissional")
    public String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
