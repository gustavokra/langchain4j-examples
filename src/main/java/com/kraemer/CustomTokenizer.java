package com.kraemer;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.Tokenizer;

public class CustomTokenizer implements Tokenizer {

    @Override
    public int estimateTokenCountInText(String text) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estimateTokenCountInText'");
    }

    @Override
    public int estimateTokenCountInMessage(ChatMessage message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estimateTokenCountInMessage'");
    }

    @Override
    public int estimateTokenCountInMessages(Iterable<ChatMessage> messages) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estimateTokenCountInMessages'");
    }

    @Override
    public int estimateTokenCountInToolSpecifications(Iterable<ToolSpecification> toolSpecifications) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estimateTokenCountInToolSpecifications'");
    }

    @Override
    public int estimateTokenCountInToolExecutionRequests(Iterable<ToolExecutionRequest> toolExecutionRequests) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'estimateTokenCountInToolExecutionRequests'");
    }
    
}
