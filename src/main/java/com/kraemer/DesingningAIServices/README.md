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

## Declaraão de método IA
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