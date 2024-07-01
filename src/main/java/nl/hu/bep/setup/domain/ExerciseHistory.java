package nl.hu.bep.setup.domain;

import java.io.Serializable;
import java.util.HashMap;

public class ExerciseHistory implements Serializable {
    static final long serialVersionUID = 1L;

    public long id;
    private int sets;
    private int weight;
    private Exercise exercise;

    public ExerciseHistory( int sets, int weight, Exercise exercise,long id) {
        this.sets = sets;
        this.weight = weight;
        this.exercise = exercise;
        this.id = id;

    }

    public ExerciseHistory(){
    }



    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sets", String.valueOf(sets));
        map.put("weight", String.valueOf(weight));
        map.put("id", String.valueOf(id));
        map.put("exercise", exercise.toMap());

        return map;
    }

    public boolean equals(ExerciseHistory exerciseHistory){
        return this.id == exerciseHistory.id;
    }

    public long getId() {
        return id;
    }
}
