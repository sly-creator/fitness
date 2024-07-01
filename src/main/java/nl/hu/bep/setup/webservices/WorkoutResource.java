package nl.hu.bep.setup.webservices;

import nl.hu.bep.setup.domain.Exercise;
import nl.hu.bep.setup.domain.User;
import nl.hu.bep.setup.domain.Workout;
import nl.hu.bep.setup.domain.enums.ExerciseType;
import nl.hu.bep.setup.domain.enums.WorkoutType;

import javax.annotation.security.RolesAllowed;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

@Path("workout")
public class WorkoutResource {

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkouts(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof User current) {
            ArrayList<HashMap<String, Object>> workouts = new ArrayList<>();
            for (Workout workout : current.getWorkouts()) {
                workouts.add(workout.toMap());
            }
            return Response.ok(workouts).build();
        }
        JsonObjectBuilder responseObject = Json.createObjectBuilder();
        responseObject.add("msg", "No user found");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
    }

    @POST
    @Path("/add")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addWorkout(@Context SecurityContext sc, String requestStr) {
        if (sc.getUserPrincipal() instanceof User current) {
            try {
                StringReader strReader = new StringReader(requestStr);
                JsonReader jsonReader = Json.createReader(strReader);
                JsonObject requestBody = jsonReader.readObject();

                String workoutName = requestBody.getString("name");
                System.out.println(workoutName);
                String workoutDescription = requestBody.getString("description");
                System.out.println(workoutDescription);
                String image = requestBody.getString("image");
                System.out.println(image);
                WorkoutType workoutType = WorkoutType.valueOf(requestBody.getString("workoutType"));
                System.out.println(workoutType);

                JsonArray values = requestBody.getJsonArray("exercises");
                Exercise[] exercises = new Exercise[values.size()];
                for (int i = 0; i < values.size(); i++) {
                    Exercise exercise = current.getExerciseById(Long.parseLong(values.getString(i)));
                    exercises[i] = exercise;
                }


                // Create a new Workout instance
                Workout newWorkout = new Workout(workoutName, workoutDescription, image, workoutType);
                System.out.println(newWorkout);

                for (Exercise exercise : exercises) {
                    newWorkout.addExercise(exercise);
                }

                current.addWorkout(newWorkout);
                System.out.println(current.getWorkouts());

                // Return success response
                return Response.ok("Workout added").build();
            } catch (Exception err) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("msg", "Error adding workout");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
            }
        }
        JsonObjectBuilder responseObject = Json.createObjectBuilder();
        responseObject.add("msg", "No user found");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
    }

    @DELETE
    @Path("/delete/{name}")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWorkout(@Context SecurityContext sc, @PathParam("name") String name) {
        if (sc.getUserPrincipal() instanceof User current) {
            try {
                // Find the workout by name
                Workout workoutToDelete = null;
                for (Workout workout : current.getWorkouts()) {
                    if (workout.getName().equalsIgnoreCase(name)) {
                        workoutToDelete = workout;
                        break;
                    }
                }

                // If workout found, delete it
                if (workoutToDelete != null) {
                    current.deleteWorkout(workoutToDelete); // Assuming removeWorkout method removes workout from user's workouts
                    return Response.ok("Workout deleted").build();
                } else {
                    JsonObjectBuilder responseObject = Json.createObjectBuilder();
                    responseObject.add("msg", "Workout not found");
                    return Response.status(Response.Status.NOT_FOUND).entity(responseObject.build()).build();
                }
            } catch (Exception err) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("msg", "Error deleting workout");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
            }
        }
        JsonObjectBuilder responseObject = Json.createObjectBuilder();
        responseObject.add("msg", "No user found");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
    }



}
