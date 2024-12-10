package com.kraemer.MyAiService;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(tools = { EmailService.class })
public interface MyAiService {

    @SystemMessage("Você é um poeta profissional")
    @UserMessage("""
                Escreva um poema sobre {topic}. O poema tem que ter {lines} linhas. Então envie este poema por email.
            """)
    String writeAPoem(String topic, int lines);
    
}