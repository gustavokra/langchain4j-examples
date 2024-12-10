package com.kraemer.MyAiService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/langchain4j-starter/send-poem")
public class MyAiResource {

    @Inject
    private MyAiService aiPoetservice;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response writePoem(@HeaderParam("topic") String topic, @HeaderParam("lines") int lines) {
        return Response.ok(aiPoetservice.writeAPoem(topic, lines)).build();
    }

}
