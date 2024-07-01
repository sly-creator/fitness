package nl.hu.bep.setup.webservices;


import nl.hu.bep.setup.domain.Exercise;
import nl.hu.bep.setup.domain.User;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.HashMap;

@Path("user")
public class UserResource {

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof User current) {

            return Response.ok(current.toMap()).build();

        }
        {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }



}
