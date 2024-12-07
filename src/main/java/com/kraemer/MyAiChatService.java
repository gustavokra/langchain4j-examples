package com.kraemer;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface MyAiChatService {
    
    String chat(@UserMessage String userMessage);
}
