package com.kraemer.MyAiService;

import dev.langchain4j.agent.tool.Tool;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EmailService {

    @Inject
    private Mailer mailer;

    @Tool("Envie um email com o seguinte conteúdo")
    public void sendEmail(String body) {
        mailer.send(Mail.withText("gustavo.kraemer6@gmail.com", "poema com langchain4j", body));
    }
    
}
