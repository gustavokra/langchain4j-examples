package com.kraemer;

import com.kraemer.customMemoryProvider.CustomMessageWindowProvider;

import dev.langchain4j.data.image.Image;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService(modelName = "m1", 
        chatMemoryProviderSupplier = CustomMessageWindowProvider.class)
@ApplicationScoped
public interface ImageGenerator {

    Image generate(String prompt);  
}
