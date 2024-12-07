package com.kraemer;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/langchain4j-starter/")
public class MyAiResource {

    @Inject
    private MyAiPoetService aiPoetservice;

    @Inject
    private MyAiTriagedReview aiTriageService;

    @Inject
    private MyAiChatService aiChatService;

    @GET
    @Path("poem")
    @Produces(MediaType.APPLICATION_JSON)
    public Response writePoem(@HeaderParam("topic") String topic, @HeaderParam("lines") int lines) {
        return Response.ok(aiPoetservice.writeAPoem(topic, lines)).build();
    }

    @GET
    @Path("triage-review")
    @Produces(MediaType.APPLICATION_JSON)
    public Response triage(@HeaderParam("review") String review) {
        return Response.ok(aiTriageService.triage(review)).build();
    }

    @GET
    @Path("chat")
    @Produces(MediaType.APPLICATION_JSON)
    public Response chat(@HeaderParam("memoryId") int memoryId, @HeaderParam("message") String message) {
        return Response.ok(aiChatService.chat(memoryId, message)).build();
    }

}
