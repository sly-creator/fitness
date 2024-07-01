package nl.hu.bep.setup.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;


public class WorkoutSession implements Serializable {
    static final long serialVersionUID = 1L;

    public long id;
    private Date startDate;
    private Date endDate;

    private Workout workout;

    public WorkoutSession( Date startDate, Date endDate, Workout workout, long id) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workout = workout;
    }

    public WorkoutSession(){
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public boolean equals(WorkoutSession workoutSession){
        return this.id == workoutSession.id;
    }

    public long getId() {
        return id;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("startDate", String.valueOf(startDate));
        map.put("endDate", String.valueOf(endDate));
        map.put("workout", (workout.toMap()));
        map.put("id", String.valueOf(id));

        return map;
    }
}
