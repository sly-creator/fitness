package nl.hu.bep.setup.domain;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class User implements Serializable, Principal {
    static final long serialVersionUID = 1L;
    private final static String saveFileName = "users.obj";

    private final String name;
    private final String password;

    private ArrayList<Exercise> exercises = new ArrayList<>();

    private ArrayList<Workout> workouts = new ArrayList<>();

    private ArrayList<WorkoutSession> workoutSessions = new ArrayList<>();

    private ArrayList<ExerciseHistory> exerciseHistories = new ArrayList<>();

    private static ArrayList<User> users;

    private final static String savePath = "/home";


    public User(String name, String password) {
        this.name = name;
        this.password = password;

    }

    public void addExerciseHistory(ExerciseHistory exerciseHistory) {
        if (exerciseHistories == null) {
            exerciseHistories = new ArrayList<>();
            saveUsers(users);
        }
        exerciseHistories.add(exerciseHistory);
        saveUsers(users);
    }

    public void addWorkoutSession(WorkoutSession workoutSession) {
        workoutSessions.add(workoutSession);
        saveUsers(users);
    }

    public void deleteWorkoutSession(WorkoutSession workoutSession) {
        workoutSessions.remove(workoutSession);
        saveUsers(users);
    }

    public WorkoutSession getWorkoutSessionById(long id) {
        return workoutSessions.stream().filter(ws -> ws.id == id).findFirst().orElse(null);
    }

    public Workout getWorkoutByName(String workoutName) {
        for (Workout workout : workouts) {
            if (workout.getName().equalsIgnoreCase(workoutName)) {
                return workout;
            }
        }
        return null;
    }


    public ArrayList<WorkoutSession> getWorkoutSessions() {
        return workoutSessions;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        saveUsers(users);
    }

    public void deleteExercise(Exercise exercise) {
        exercises.remove(exercise);
        saveUsers(users);
    }

    public void deleteWorkout(Workout workout) {
        workouts.remove(workout);
        saveUsers(users);
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void addWorkout(Workout workout) {
        workouts.add(workout);
        saveUsers(users);
    }

    public ArrayList<Workout> getWorkouts() {
        if (workouts == null) {
            workouts = new ArrayList<>();
        }
        return workouts;
    }


    public static User getUser(String username, String password) {
        loadUsers();
        User user = getUserByPasswordAndName(username, password);
        if (user != null) {
            return user;
        }
        return registerUser(username, password);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static User getUserByName(String user) throws IOException, ClassNotFoundException {
        loadUsers();
        System.out.println(users);
        return users.stream().filter(u -> u.getName().equals(user)).findFirst().orElse(null);
    }

    public static User getUserByPasswordAndName(String name, String password) {
        for (User user : users) {
            if (user.name.equals(name) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static User registerUser(String name, String password) {
        loadUsers();
        User checkIfExists = getUserByPasswordAndName(name, password);
        if (checkIfExists != null) {
            System.out.println("User already exists: " + name);
            return checkIfExists;
        }

        User newUser = new User(name, password);
        users.add(newUser);
        System.out.println(users);
        saveUsers(users);

        System.out.println("User registered successfully: " + name);
        return newUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User myUser = (User) o;
        return Objects.equals(name, myUser.name);
    }

    public static void loadUsers() {
        Path objectStorage = Path.of(savePath, saveFileName);
        try (InputStream is = Files.newInputStream(objectStorage);
             ObjectInputStream ois = new ObjectInputStream(is)) {
            // Loads the object from the selected path
            users = (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>();
            System.out.println("Failed to load users: " + e.getMessage());
        }
        saveUsers(users);
    }

    public static void saveUsers(List<User> users) {
        Path saveDir = Path.of(savePath);
        try {
            if (!Files.exists(saveDir)) {
                Files.createDirectories(saveDir);
                System.out.println("Directory created: " + saveDir);
            }
        } catch (IOException e) {
            System.out.println("Failed to create save directory: " + e.getMessage());
            return;
        }

        Path objectStorage = Path.of(savePath, saveFileName);
        try (OutputStream os = Files.newOutputStream(objectStorage);
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            // Saves the object at the selected path
            oos.writeObject(users);
            System.out.println("Users saved successfully to: " + objectStorage);
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Failed to save users: " + e.getMessage());
        }
    }
    public List<ExerciseHistory> getExerciseHistories() {
        if (exerciseHistories != null) {
            return exerciseHistories;
        } else {
            return new ArrayList<>();
        }
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", name);
        map.put("exercises", exercises);
        map.put("workouts", workouts);
        return map;
    }

    public Exercise getExerciseById(Long id) {
        for (Exercise exercise : exercises) {
            if (exercise.getId() ==(id)) {
                return exercise;
            }
        }
        return null;
    }

    public ExerciseHistory getExerciseHistoryById(long exerciseHistoryId) {
        for (ExerciseHistory history : exerciseHistories) {
            if (history.getId() ==(exerciseHistoryId)) {
                return history;
            }
        }
        return null;
    }

    public void deleteExerciseHistory(ExerciseHistory exerciseHistoryToRemove) {
        exerciseHistories.remove(exerciseHistoryToRemove);
        saveUsers(users);
    }

}
