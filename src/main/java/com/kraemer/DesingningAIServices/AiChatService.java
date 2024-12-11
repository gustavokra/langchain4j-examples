package com.kraemer.DesingningAIServices;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface AiChatService {

    @SystemMessage("Você é um programador profissional com muito" +
            " conhecimento e sucesso em sua carreira.")
    public String chat(@UserMessage String message);
}