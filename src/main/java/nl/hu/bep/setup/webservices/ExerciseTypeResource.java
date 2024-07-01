package nl.hu.bep.setup.webservices;

import nl.hu.bep.setup.domain.enums.ExerciseType;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("exercise-types")
public class ExerciseTypeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExerciseTypes() {
        ExerciseType[] exerciseTypes = ExerciseType.values();
        return Response.ok(exerciseTypes).build();
    }
}
