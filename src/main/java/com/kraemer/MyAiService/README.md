# Criando um *AI Service*
Este serviço é a interface que seu código utiliza para interagir com a LLM.

package io.quarkiverse.langchain4j.samples;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

```
@RegisterAiService(
        tools = EmailService.class
)
public interface MyAiService {

    @SystemMessage("You are a professional poet")
    @UserMessage("""
                Write a poem about {topic}. The poem should be {lines} lines long. Then send this poem by email.
            """)
    String writeAPoem(String topic, int lines);
}
```

1. @RegisterAiService registra o serviço de IA.
2. O atributo tools define as ferramentas que a LLM pode usar. Durante a interação, a LLM pode invocar essas ferramentas e refletir suas saídas.
3. @SystemMessage registra uma mensagem do ssitema, setando o contexto inicial ou "escopo".
4. @UserMessage serve como prompt.

O método invoca o LLM, iniciando a interação entre o LLM e a aplicação, começando com a mensagem de sistema e então a mensagem de usuário. A aplicação invoca o método e recebe a resposta.