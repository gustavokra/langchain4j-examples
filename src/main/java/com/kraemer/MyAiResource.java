package com.kraemer;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("api/v1/langchain4j-starter/")
public class MyAiResource {
    
    @Inject
    private MyAiService service;

    @GET
    @Path("poem")
    @Produces(MediaType.APPLICATION_JSON)
    public jakarta.ws.rs.core.Response writePoem(@HeaderParam("topic") String topic, @HeaderParam("lines") int lines) {
        return jakarta.ws.rs.core.Response.ok(service.writeAPoem(topic, lines)).build();
    }

    @GET
    @Path("triage-review")
    @Produces(MediaType.APPLICATION_JSON)
    public jakarta.ws.rs.core.Response triage(@HeaderParam("review") String review) {
        return jakarta.ws.rs.core.Response.ok(service.triage(review)).build();
    } 

}
