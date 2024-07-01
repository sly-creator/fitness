package nl.hu.bep.setup.domain;

import nl.hu.bep.setup.domain.enums.WorkoutType;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Workout implements Serializable {
    private static final long serialVersionUID = 1L;

    public long id;
    private String name;
    private String description;
    private String image;

    private ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    private WorkoutType workoutType = WorkoutType.Strength;


    public Workout( String name, String description, String image, WorkoutType workoutType) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.workoutType = workoutType;
        this.id = System.currentTimeMillis();
    }

    public Workout(){

    }

    public void addExercise(Exercise exercise){
        exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise){
        exercises.remove(exercise);
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public boolean containsExercise(Exercise exercise){
        return exercises.contains(exercise);
    }
    public boolean equals(Workout workout){
        return this.id == workout.id;
    }


    public void setName(String name) {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }


    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("description", description);
        map.put("image", image);
        map.put("workoutType", workoutType);
        ArrayList<HashMap<String, String>> exercises = new ArrayList<>();
        for (Exercise exercise : this.exercises) {
            exercises.add(exercise.toMap());
        }
        map.put( "exercises", exercises);
        map.put("id", id);
        return map;
    }


}
