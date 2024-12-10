package com.kraemer;

import dev.langchain4j.data.image.Image;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService
@ApplicationScoped
public interface ImageGenerator {

    Image generate(String prompt);  
}
