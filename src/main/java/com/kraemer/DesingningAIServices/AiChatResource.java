package com.kraemer.DesingningAIServices;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/chat")
public class AiChatResource {
    
    @Inject
    private AiChatService aiService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response chat(@QueryParam("message") String message) {
        return Response.ok(aiService.chat(message)).build();
    }
}
