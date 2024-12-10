package com.kraemer.MyAiService;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterAiService(tools = { EmailService.class })
public interface MyAiService {

    @SystemMessage("Você é um poeta profissional")
    @UserMessage("""
                Escreva um poema sobre {topic}. O poema tem que ter {lines} linhas. Então envie o conteúdo do poema por email.
            """)
    String writeAPoem(@MemoryId int memoryId, String topic, int lines);
    
}