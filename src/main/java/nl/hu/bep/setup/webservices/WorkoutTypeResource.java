package nl.hu.bep.setup.webservices;


import nl.hu.bep.setup.domain.enums.WorkoutType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("workout-types")
public class WorkoutTypeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkoutTypes() {
        WorkoutType[] workoutTypes = WorkoutType.values();
        return Response.ok(workoutTypes).build();
    }
}
