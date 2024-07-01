const form = document.getElementById("add-form");
const workoutType = document.getElementById("workoutType");
const exerciseTitles = document.getElementById("exerciseTitles");
const exerciseToAdd = document.getElementById("exerciseToAdd");
const addExerciseButton = document.getElementById("addExerciseButton");

function getWorkoutTypes() {
  fetch("/restservices/workout-types")
    .then((response) => response.json())
    .then((data) => {
      workoutType.innerHTML = "";
      data.forEach((workout) => {
        const option = document.createElement("option");
        option.value = workout;
        option.innerHTML = workout;
        workoutType.add(option);
      });
    });
}

let selectedExercises = [];

function getExercises() {
  console.log("getExercises");
  fetch("/restservices/exercise", {
    headers: {
      Authorization: getBearerToken(),
    },
  })
    .then((response) => response.json())
    .then((data) => {
      exerciseToAdd.innerHTML = "";
      data.forEach((exercise) => {
        if (
          selectedExercises.find(
            (selectedExercise) => selectedExercise.id === exercise.id
          )
        ) {
          return;
        }
        const option = document.createElement("option");
        option.value = exercise.id;
        option.innerHTML = exercise.name;
        exerciseToAdd.add(option);
      });
    });
}

function showExercises() {
  console.log("showExercises");
  exerciseTitles.innerHTML = "";
  selectedExercises.forEach((exercise) => {
    const title = document.createElement("p");
    const titleText = document.createTextNode(exercise.name);
    const deleteButton = document.createElement("button");

    deleteButton.type = "button";
    deleteButton.innerHTML = "Delete";
    deleteButton.onclick = () => deleteExercise(exercise.id);

    title.appendChild(titleText);
    title.appendChild(deleteButton);
    exerciseTitles.appendChild(title);
  });
}

function deleteExercise(id) {
  console.log("deleteExercise");
  selectedExercises = selectedExercises.filter(
    (exercise) => exercise.id !== id
  );
  getExercises();
  showExercises();
}

function addExercise() {
  console.log("addExercise");
  const selectedExercise = exerciseToAdd.options[exerciseToAdd.selectedIndex];

  if (
    !selectedExercise ||
    !selectedExercise.value ||
    selectedExercise.value === ""
  ) {
    alert("Please select an exercise to add!");
    return;
  }

  selectedExercises.push({
    id: selectedExercise.value,
    name: selectedExercise.innerHTML,
  });

  console.log(selectedExercises);
  getExercises();
  showExercises();
}

function addWorkout(e) {
  e.preventDefault();
  console.log("addWorkout");

  const formData = new FormData(form);

  const workout = {
    name: formData.get("name"),
    description: formData.get("description"),
    image: formData.get("image") ?? "",
    workoutType: formData.get("workoutType"),
    exercises: selectedExercises.map((exercise) => {
      return exercise.id;
    }),
  };

  console.log(workout);

  fetch("/restservices/workout/add", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: getBearerToken(),
    },
    body: JSON.stringify(workout),
  }).then((response) => {
    if (response.ok) {
      form.reset();
      selectedExercises = [];
      getExercises();
      showExercises();
      alert("Workout added successfully!");
    } else {
      alert("Failed to add workout!");
    }
  });
}

form.addEventListener("submit", addWorkout);
addExerciseButton.addEventListener("click", addExercise);

getWorkoutTypes();
getExercises();

checkLogin();
