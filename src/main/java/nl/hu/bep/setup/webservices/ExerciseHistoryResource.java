package nl.hu.bep.setup.webservices;

import nl.hu.bep.setup.domain.*;
import nl.hu.bep.setup.domain.enums.WorkoutType;


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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Path("exercisehistory")
public class ExerciseHistoryResource {

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExerciseHistory(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof User current) {
            ArrayList<HashMap<String, Object>> exercisehistories = new ArrayList<>();
            for (ExerciseHistory exerciseHistory : current.getExerciseHistories()) {
                exercisehistories.add(exerciseHistory.toMap());
            }
            return Response.ok(exercisehistories).build();
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
    public Response postExerciseHistory(@Context SecurityContext sc, String requestStr) {
        if (sc.getUserPrincipal() instanceof User current) {
            try {
                // Parse the incoming JSON request
                StringReader strReader = new StringReader(requestStr);
                JsonReader jsonReader = Json.createReader(strReader);
                JsonObject requestBody = jsonReader.readObject();

                // Retrieve exercise details from the request body
                int sets = requestBody.getInt("sets");
                System.out.println(sets);
                int weight = requestBody.getInt("weight");
                System.out.println(weight);
                long exerciseId = requestBody.getJsonNumber("exerciseId").longValue();
                System.out.println(exerciseId);
                long sessionId = System.currentTimeMillis();
                // Retrieve the Exercise object by id
                Exercise exercise = current.getExerciseById(exerciseId);
                System.out.println(exercise);
                if (exercise == null) {
                    JsonObjectBuilder responseObject = Json.createObjectBuilder();
                    responseObject.add("msg", "Exercise not found");
                    return Response.status(Response.Status.NOT_FOUND).entity(responseObject.build()).build();
                }

                // Create and add the new exercise history entry
                ExerciseHistory newHistory = new ExerciseHistory(sets, weight, exercise, sessionId);
                current.addExerciseHistory(newHistory);
                System.out.println(current.getExerciseHistories());

                return Response.ok("Exercise history added").build();
            } catch (Exception err) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("msg", "Error adding exercise history");
                System.out.println(err);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
            }
        }
        JsonObjectBuilder responseObject = Json.createObjectBuilder();
        responseObject.add("msg", "No user found");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteExerciseHistory(@Context SecurityContext sc, @PathParam("id") long exerciseHistoryId) {
        if (sc.getUserPrincipal() instanceof User current) {
            try {
                ExerciseHistory exerciseHistoryToDelete = current.getExerciseHistoryById(exerciseHistoryId);

                if (exerciseHistoryToDelete != null) {
                    current.deleteExerciseHistory(exerciseHistoryToDelete);
                    return Response.ok("Exercise history deleted").build();
                } else {
                    JsonObjectBuilder responseObject = Json.createObjectBuilder();
                    responseObject.add("msg", "Exercise history not found");
                    return Response.status(Response.Status.NOT_FOUND).entity(responseObject.build()).build();
                }
            } catch (Exception err) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("msg", "Error deleting exercise history");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
            }
        }
        JsonObjectBuilder responseObject = Json.createObjectBuilder();
        responseObject.add("msg", "No user found");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
    }
}
