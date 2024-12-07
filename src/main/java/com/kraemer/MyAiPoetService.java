package com.kraemer;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(modelName = "m1", chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class)
public interface MyAiPoetService {
    
    @SystemMessage("You are a professional poet")
    @UserMessage("""
                Escreva um poema sobre {topic}. O poema tem que ter {lines} linhas.
            """)
    String writeAPoem(String topic, int lines);
}
