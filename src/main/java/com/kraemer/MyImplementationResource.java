package com.kraemer;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("api/v1/langchain4j-starter")
public class MyImplementationResource {
    
    @Inject
    private MyAiService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public jakarta.ws.rs.core.Response writePoem(@HeaderParam("topic") String topic, @HeaderParam("lines") int lines) {
        return jakarta.ws.rs.core.Response.ok(service.writeAPoem(topic, lines)).build();
    }

}
