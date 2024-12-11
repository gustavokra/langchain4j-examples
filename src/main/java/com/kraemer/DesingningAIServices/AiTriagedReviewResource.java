package com.kraemer.DesingningAIServices;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/triage-review")
public class AiTriagedReviewResource {
    
    @Inject
    private AiTriagedReviewService aiService;

    @GET
    @Path("triage-review")
    @Produces(MediaType.APPLICATION_JSON)
    public Response triage(@HeaderParam("review") String review) {
        return Response.ok(aiService.triage(review)).build();
    }

}
