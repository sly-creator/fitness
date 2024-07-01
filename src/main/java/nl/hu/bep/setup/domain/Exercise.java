package nl.hu.bep.setup.domain;

import nl.hu.bep.setup.domain.enums.ExerciseType;
import nl.hu.bep.setup.domain.enums.MuscleType;

import java.io.Serializable;
import java.util.HashMap;


public class Exercise implements Serializable {
    static final long serialVersionUID = 1L;

    public long id;
    private  String name;
    private  String description;
    private  String image;
    private  int sets;
    private  int reps;
    private  MuscleType muscleType;
    private  ExerciseType exerciseType;

    public Exercise(String name, String description, String image, int sets, int reps, MuscleType muscleType, ExerciseType exerciseType, long id) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.sets = sets;
        this.reps = reps;
        this.muscleType = muscleType;
        this.exerciseType = exerciseType;
        this.id = id;
    }

    public Exercise(){

    }

    public long getId() {
        return id;
    }


    public boolean equals(Exercise exercise){
        return this.id == exercise.id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public MuscleType getMuscleType() {
        return muscleType;
    }

    public void setMuscleType(MuscleType muscleType) {
        this.muscleType = muscleType;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }
    public HashMap<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", this.name);
        map.put("description", this.description);
        map.put("image", this.image);
        map.put("sets", String.valueOf(this.sets));
        map.put("reps", String.valueOf(this.reps));
        map.put("muscleType", this.muscleType.toString());
        map.put("exerciseType", this.exerciseType.toString());
        map.put("id", String.valueOf(this.id));
        return map;
    }
}
