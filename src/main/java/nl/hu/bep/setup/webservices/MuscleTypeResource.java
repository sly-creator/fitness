package nl.hu.bep.setup.webservices;

import nl.hu.bep.setup.domain.enums.MuscleType;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("muscle-types")
public class MuscleTypeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMuscleTypes() {
        MuscleType[] muscleTypes = MuscleType.values();
        return Response.ok(muscleTypes).build();
    }
}
