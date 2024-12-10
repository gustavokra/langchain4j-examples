package com.kraemer.MyAiService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/v1/langchain4j-starter/send-poem")
public class MyAiResource {

    @Inject
    private MyAiService myAiService;

    @Inject
    private EmailService emailService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response writePoem(@HeaderParam("memoryId") int memoryId, @HeaderParam("topic") String topic, @HeaderParam("lines") int lines) {
        return Response.ok(myAiService.writeAPoem(memoryId, topic, lines)).build();
    }

}
