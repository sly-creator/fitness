const form = document.getElementById("add-form");

const muscleType = document.getElementById("muscleType");
const exerciseType = document.getElementById("exerciseType");

function getMuscleTypes() {
  fetch("/restservices/muscle-types")
    .then((response) => response.json())
    .then((data) => {
      muscleType.innerHTML = "";

      data.forEach((muscle) => {
        const option = document.createElement("option");
        option.value = muscle;
        option.innerHTML = muscle;
        muscleType.add(option);
      });
    });
}

function getExerciseTypes() {
  fetch("/restservices/exercise-types")
    .then((response) => response.json())
    .then((data) => {
      exerciseType.innerHTML = "";

      data.forEach((exercise) => {
        const option = document.createElement("option");
        option.value = exercise;
        option.innerHTML = exercise;
        exerciseType.add(option);
      });
    });
}

function addExercise(e) {
  e.preventDefault();
  console.log("addExercise");

  const formData = new FormData(form);

  const exercise = {
    name: formData.get("name"),
    description: formData.get("description"),
    image: formData.get("image") ?? "",
    sets: parseInt(formData.get("sets") ?? "0"),
    reps: parseInt(formData.get("reps") ?? "0"),
    muscleType: formData.get("muscleType"),
    exerciseType: formData.get("exerciseType"),
  };

  console.log(exercise);
  // HERE

  fetch("/restservices/exercise/add", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: getBearerToken(),
    },
    body: JSON.stringify(exercise),
  }).then((response) => {
    if (response.ok) {
      form.reset();
      alert("Exercise added successfully!");
    } else {
      alert("Failed to add exercise!");
    }
  });
}

form.addEventListener("submit", addExercise);

getMuscleTypes();
getExerciseTypes();

checkLogin();
