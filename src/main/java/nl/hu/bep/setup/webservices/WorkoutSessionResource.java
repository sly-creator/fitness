package nl.hu.bep.setup.webservices;

import nl.hu.bep.setup.domain.User;
import nl.hu.bep.setup.domain.Workout;
import nl.hu.bep.setup.domain.WorkoutSession;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Path("workoutsession")
public class WorkoutSessionResource {

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkoutSessions(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof User current) {
            ArrayList<HashMap<String, Object>> workoutSessions = new ArrayList<>();
            for (WorkoutSession session : current.getWorkoutSessions()) {
                workoutSessions.add(session.toMap());
            }
            return Response.ok(workoutSessions).build();
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
    public Response postWorkoutSession(@Context SecurityContext sc, String requestStr) {
        if (sc.getUserPrincipal() instanceof User current) {
            try {
                // Parse the incoming JSON request
                StringReader strReader = new StringReader(requestStr);
                JsonReader jsonReader = Json.createReader(strReader);
                JsonObject requestBody = jsonReader.readObject();


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(requestBody.getString("startDate"), formatter);
                LocalDate endDate = LocalDate.parse(requestBody.getString("endDate"), formatter);


                // Convert LocalDate to Date
                Date startDateAsDate = java.sql.Date.valueOf(startDate);
                Date endDateAsDate = java.sql.Date.valueOf(endDate);
                System.out.println("Start date: " + startDateAsDate);
                long sessionId = System.currentTimeMillis();
                System.out.println("Session ID: " + sessionId);

                // Retrieve the workout name
                String workoutName = requestBody.getString("workoutName", "");

                Workout workout;
                if (workoutName.isEmpty()) {
                    workout = new Workout();
                    current.addWorkout(workout);
                    System.out.println("Created new Workout: " + workout);
                } else {
                    // Retrieve the workout by name
                    workout = current.getWorkoutByName(workoutName);
                    System.out.println("Retrieved Workout: " + workout);

                    if (workout == null) {
                        JsonObjectBuilder responseObject = Json.createObjectBuilder();
                        responseObject.add("msg", "Workout not found");
                        return Response.status(Response.Status.NOT_FOUND).entity(responseObject.build()).build();
                    }
                }

                // Create and add the new workout session
                WorkoutSession newSession = new WorkoutSession(startDateAsDate, endDateAsDate, workout,sessionId);
                current.addWorkoutSession(newSession);

                return Response.ok("Workout session added").build();
            } catch (Exception err) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("msg", "Error adding workout session");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
            }
        }
        JsonObjectBuilder responseObject = Json.createObjectBuilder();
        responseObject.add("msg", "no user found");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWorkoutSession(@Context SecurityContext sc, @PathParam("id") long id) {
        if (sc.getUserPrincipal() instanceof User current) {
            try {
                WorkoutSession sessionToDelete = current.getWorkoutSessionById(id);

                if (sessionToDelete != null) {
                    current.deleteWorkoutSession(sessionToDelete);
                    return Response.ok("Workout session deleted").build();
                } else {
                    JsonObjectBuilder responseObject = Json.createObjectBuilder();
                    responseObject.add("msg", "Workout session not found");
                    return Response.status(Response.Status.NOT_FOUND).entity(responseObject.build()).build();
                }
            } catch (Exception err) {
                JsonObjectBuilder responseObject = Json.createObjectBuilder();
                responseObject.add("msg", "Error deleting workout session");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
            }
        }
        JsonObjectBuilder responseObject = Json.createObjectBuilder();
        responseObject.add("msg", "No user found");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseObject.build()).build();
    }
}
