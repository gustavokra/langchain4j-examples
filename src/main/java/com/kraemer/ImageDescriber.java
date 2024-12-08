package com.kraemer;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.ImageUrl;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService(chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class)
@ApplicationScoped
public interface ImageDescriber {

    @UserMessage("Me diga o que tem nessa imagem, de forma breve.")
    String describe(@ImageUrl String url); 
}
