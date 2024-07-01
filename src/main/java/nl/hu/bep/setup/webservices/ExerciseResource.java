package nl.hu.bep.setup.webservices;


import nl.hu.bep.setup.domain.Exercise;
import nl.hu.bep.setup.domain.User;
import nl.hu.bep.setup.domain.enums.ExerciseType;
import nl.hu.bep.setup.domain.enums.MuscleType;


import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

@Path("exercise")
public class ExerciseResource {

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExercises(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof User current) {

            ArrayList<HashMap<String, String>> exercises = new ArrayList<>();
            for (Exercise exer : current.getExercises()) {
                exercises.add(exer.toMap());
            }

            return Response.ok(exercises).build();
        }
        JsonObjectBuilder responseObject = Json.createObjectBuilder();
        responseObject.add("msg", "no user found");

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
    }

    @POST
    @Path("/add")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postExercise(@Context SecurityContext sc, String requestStr) {
        if (sc.getUserPrincipal() instanceof User current) {
            try {

                StringReader strReader = new StringReader(requestStr);
                JsonReader jsonReader = Json.createReader(strReader);
                JsonObject requestBody = jsonReader.readObject();

                long sessionId = System.currentTimeMillis();
                String name = requestBody.getString("name");
                System.out.println(name);
                String description = requestBody.getString("description");
                System.out.println(description);
                String image = requestBody.getString("image");
                System.out.println(image);
                int sets = requestBody.getInt("sets");
                System.out.println(sets);
                int reps = requestBody.getInt("reps");
                System.out.println(reps);
                MuscleType muscleType = MuscleType.valueOf(requestBody.getString("muscleType"));
                System.out.println(muscleType);
                ExerciseType exerciseType = ExerciseType.valueOf(requestBody.getString("exerciseType"));
                System.out.println(exerciseType);


                Exercise exer = new Exercise(name, description, image, sets, reps, muscleType, exerciseType,sessionId);
                System.out.println(exer);
                current.addExercise(exer);


                return Response.ok("exercise Updated").build();
            } catch (Exception err) {

                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("msg", "no exercise found");
                System.out.println(err);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
            }
        }
        JsonObjectBuilder responseObject = Json.createObjectBuilder();
        responseObject.add("msg", "no user found");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
    }

    @DELETE
    @Path("/delete/{name}")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteExercise(@Context SecurityContext sc, @PathParam("name") String name) {
        if (sc.getUserPrincipal() instanceof User current) {
            try {
                Exercise exerciseToDelete = null;
                for (Exercise exer : current.getExercises()) {
                    if (exer.getName().equalsIgnoreCase(name)) {
                        exerciseToDelete = exer;
                        break;
                    }
                }

                if (exerciseToDelete != null) {
                    current.deleteExercise(exerciseToDelete);
                    return Response.ok("Exercise deleted").build();
                } else {
                    JsonObjectBuilder responseObject = Json.createObjectBuilder();
                    responseObject.add("msg", "Exercise not found");
                    return Response.status(Response.Status.NOT_FOUND).entity(responseObject.build()).build();
                }
            } catch (Exception err) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("msg", "Error deleting exercise");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
            }
        }
        JsonObjectBuilder responseObject = Json.createObjectBuilder();
        responseObject.add("msg", "No user found");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
    }
}

