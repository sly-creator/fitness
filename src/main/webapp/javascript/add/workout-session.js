const form = document.getElementById("add-form");
const workoutElement = document.getElementById("workout");

function getWorkouts() {
  console.log("getworkouts");
  fetch("/restservices/workout", {
    headers: {
      Authorization: getBearerToken(),
    },
  })
    .then((response) => response.json())
    .then((data) => {
      workoutElement.innerHTML = "";
      data.forEach((workout) => {
        const option = document.createElement("option");
        option.value = workout.name;
        option.innerHTML = workout.name;
        workoutElement.add(option);
      });
    });
}

function addWorkout(e) {
  e.preventDefault();
  console.log("addWorkout");

  const formData = new FormData(form);

  const workout = {
    startDate: formData.get("startDate"),
    endDate: formData.get("endDate"),
    workout: formData.get("workout"),
  };

  console.log(workout);

  fetch("/restservices/workoutsession/add", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: getBearerToken(),
    },
    body: JSON.stringify(workout),
  }).then((response) => {
    if (response.ok) {
      form.reset();
      alert("Workout session added successfully!");
    } else {
      alert("Failed to add workout session!");
    }
  });
}

form.addEventListener("submit", addWorkout);

getWorkouts();

checkLogin();
