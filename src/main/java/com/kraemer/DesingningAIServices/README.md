# Designing AI services
Um AI Service empregra uma maneira declarativa de difinir interações com a LLM.
Ele opera como um intermediário, conhecido como o padrão *embassador*.

## Propósito
O AI Service serve como o ponto de conexão principal entre a aplicação e o LLM. Ele abstrai as especificações do LLM, encapsulando e declarando todas interações com uma interface única.

## @RegisterAiService
A anotação é fundamental para registrar um AI Service. É colocada em uma interface:
```
@RegisterAiService
public interface MyAiService {
    // methods.
}
```
Uma vez registreada, injete o AI Service na applicação:
```
@Inject MyAiService service;
```

### Importante
Os Beans criados por ```@RegisterAiService``` são ```@RequestScoped``` por padrão, permitindo remover objetos de configuração de contexto (memória) para chats. Isso pode ser inapropriado para CLIs ou WebSockets. Quando usar um serviço em um CLI, faz sentido que o serviço estja sob @ApplicationScoped, para isso basta anotar ```@ApplicationScoped```.

## Declaração de método IA
Com a interface anotada com @RegisterAiService, você modela interações com a LLM com métodos. Esses métodos aceitam parâmetros e são anotados com ```@SystemMessage``` e ```@UserMessage``` para definir instruções diretas à LLM:
```
@SystemMessage("You are a professional poet.")
@UserMessage("""
    Write a poem about {topic}. The poem should be {lines} lines long. Then send this poem by email.
""")
String writeAPoem(String topic, int lines);
```

### System Message
A anotação ```@SystemMessage``` define o escopo e instruções iniciais, servindo como a primeira mensagem enviada ao LLM. Isso delineia o papel do serviço de IA na interação:
```
@SystemMessage("""
    You are working for a bank, processing reviews about financial products. Triage reviews into positive and negative ones, responding with a JSON document.
    """
)
```

### User Message (Prompt)
A anotação ```@UserMessage``` define instruções primárias enviadas para a LLM. Isso tipicamente abrange requests e seu formato de resposta:
```
@UserMessage("""
    Your task is to process the review delimited by ---.
    Apply a sentiment analysis to the review to determine if it is positive or negative, considering various languages.

    For example:
    - "I love your bank, you are the best!" is a 'POSITIVE' review
    - "J'adore votre banque" is a 'POSITIVE' review
    - "I hate your bank, you are the worst!" is a 'NEGATIVE' review

    Respond with a JSON document containing:
    - the 'evaluation' key set to 'POSITIVE' if the review is positive, 'NEGATIVE' otherwise
    - the 'message' key set to a message thanking or apologizing to the customer. These messages must be polite and match the review's language.

    ---
    {review}
    ---
""")
TriagedReview triage(String review);
```

### Passar parâmetros e referenciar
Métodos IA podem pegar parâmetros referenciados no SystemMessage e no UserMessage usando a sintaxe {parametro}:
```
@SystemMessage("You are a professional poet")
@UserMessage("""
    Write a poem about {topic}. The poem should be {lines} lines long. Then send this poem by email.
""")
String writeAPoem(String topic, int lines);
```

O valor do @SystemMessage também é um template, que pode ser referanciar os vários parametros do métodos, e também tem acesso o parâmetro ```chat_history```que pode ser usado para iterar sobre o histórico do chat.

### Tipo de retorno da IA
Se o prompt define uma resposta em JSON precisamente, você pode mapear a resposta diretamente para um objeto:
```
TriagedReview triage(String review);
```
Nessa circunstancia, o Quarkus automáticamente cria uma instância de TriagedReview pela resposta em JSON fornecida pelo LLM.

Para aumentar a flexibilidade da criação do prompt, a palavra reservada {response_schema} pode ser usada com o prompt. Esse placeholder é dinamicamente trocado pelo schema do objeto de retorno do método. Por padrão, se o placeholder não for adicionado a ```@SystemMessage``` ou ```@@UserMessagem```@ ele será adicionado ao final do prompt. Para evitar a criação do eschema, pode-se setar a propriedade ```quarkus.langchain4j.response-schema```@ como false.

#### Recebendo mensagens passadas como Parâmetro.
Para passar uma mensagem como parâmetro, pode-se usasr o ```@UserMessage``` como parâmetro.
```
String chat(@UserMessage String userMessage);
```
A parâmetro deve ser do tipo String.


### Recebendo MemoryId como parâmetro
O memory abrange o contexto cumulatido da interação com a LLM. Para gerenciar o estado stateless do LLM, o contexto completo deve ser trocado entre o LLM e o AI Service.
O AI Service pode armazenar as últimas mensagens em uma memória, geralmente chamada de *context*. A anotação @MemoryId permite referenciar a memória para um usuário específido no método IA:
```
String chat(@MemoryId int memoryId, @UserMessage String userMessage);
```
### Configurando o modelo de chat de linguagem
Se você tem somente um modelo de lingaguem, nenhuma configuração em específico é necessária.
SE você tiver vários em sua aplicação, cada modelo precisa de um nome, que pode ser referenciado no AI Service.
```
@RegisterAiService(model="m1)
```

A configuração de vários modelos fica assim: 
```
# ensure that the model with the name 'm1', is provided by OpenAI
quarkus.langchain4j.m1.chat-model.provider=openai
# ensure that the model with the name 'm2', is provided by HuggingFace
quarkus.langchain4j.m2.chat-model.provider=huggingface

# configure the various aspects of each model
quarkus.langchain4j.openai.m1.api-key=sk-...
quarkus.langchain4j.huggingface.m2.api-key=sk-...
```

## Configurando o Context(Memória)
Como as LLMS não tem estado, a memória tem que ser trocada cada vez. Para previnir mensagens excessivas, é crucial evitar mensagens antigas.

O atributo ```chatMemoryProviderSupplier``` da anotação ```@RegisterAiService``` permite configurar o ```dev.langchain4j.memory.chat.ChatMemoryProvider```.
O valor padrão dessa anotação é ```RegisterAiService.BeanChatMemoryProviderSupplier.class```, o que significa que o AiService vai usar qualquer ChatMemoryProvider configurado pela aplicação ou o padrão provido pela extensão.

A implementação padrão do ```ChatMemoryProvider``` faz duas coisas:
- Usa qualquer bean ```dev.langchain4j.store.memory.chat.ChatMemoryStore```  que é configurado, como armazenamento de apoio.
    - Se a aplicação provê seu próprio bean ```ChatMemoryStore```, ele será usado ao invez do InMemoryChatMemoryStore padrão.
- Aproveita a configuração disponível em ```quarkus.langchain4j.chat-memory``` para construir o ```ChatMemoryProvider```.
    - Os valores padrões de configuração resultam no uso de ```dev.langchain4j.memory.chat.MessageWindowChatMemory``` com um tamanho de janela de 10.
    - Setando ```quarkus.langchain4j.chat-memory.type=token-window```, um ```dev.langchain4j.memory.chat.TokenWindowChatMemor``` será usado. Isso requer a utilização do Bean Tokenizer.
### Importante
A extensão automaticamente remove objetos ```ChatMemory``` de seu respectivo ```ChatMemoryStore``` quando a AI está fora de escopo (```@RequestScoped``` é o padrão).
Em caso de beans declarados como ```@Singleton``` ou ```@ApplicationScoped``` o ```io.quarkiverse.langchain4j.ChatMemoryRemover``` deve ser usado para remover elementos manualmente.

Se seu caso de uso não requer memória, deve-se usar ```@RegisterAiService(chatMemoryProviderSupplier = RegisterAiService.NoChatMemoryProviderSupplier.class```.

Um Ai service pode ter sua memória implantanda usando ```@SeedMemory```. 

### Uso avançado
```ChatMemoryProvider``` é bastante configuravel, apesar de sua configuração ser desnecessaria em muitos casos. Aqui tem um exemplo possível:
```
package io.quarkiverse.langchain4j.samples;

import jakarta.inject.Singleton;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;

@Singleton
public class CustomChatMemoryProvider implements ChatMemoryProvider {

    private final ChatMemoryStore store;

    public CustomChatMemoryProvider() {
        this.store = createCustomStore();
    }

    private static ChatMemoryStore createCustomStore() {
        // TODO: provide some kind of custom store
        return null;
    }

    @Override
    public ChatMemory get(Object memoryId) {
        return createCustomMemory(memoryId);
    }

    private static ChatMemory createCustomMemory(Object memoryId) {
        // TODO: implement using memoryId and store
        return null;
    }
}
```

Se por algum motivo IA Services precisam de um ChatMemoryProvider diferente, isso é possível configurando o atributo chatMemoryProviderSupplier da anotação @RegisterAiService e implementando um custom provider.
Exemplo:

```
package io.quarkiverse.langchain4j.samples;

import java.util.function.Supplier;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;

public class CustomProvider implements Supplier<ChatMemoryProvider> {

    private final InMemoryChatMemoryStore store = new InMemoryChatMemoryStore();

    @Override
    public ChatMemoryProvider get() {
        return new ChatMemoryProvider() {

            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder()
                        .maxMessages(20)
                        .id(memoryId)
                        .chatMemoryStore(store)
                        .build();
            }
        };
    }
}
```

```
@RegisterAiService(
    chatMemoryProviderSupplier = MySmallMemoryProvider.class)
```
Para LLMs que não dependem de memória, você não precisa configurar.

Em casos que envolvem multiplos uusários, tenha certeza de que cada usuário tem um memory ID único e passe o ID para o método IA.
```
String chat(@MemoryId int memoryId, @UserMessage String userMessage);
```