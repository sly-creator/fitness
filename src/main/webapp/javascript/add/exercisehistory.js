const form = document.getElementById("add-form");
const exerciseElement = document.getElementById("exercise");

function addExercisehistory(e) {
  e.preventDefault();
  console.log("addExercisehistory");

  const formData = new FormData(form);

  const exercise = {
    sets: parseInt(formData.get("sets") ?? "0"),
    weight: parseInt(formData.get("weight") ?? "0"),
    exerciseId: parseInt(formData.get("exercise")),
  };

  console.log(exercise);
  // HERE

  fetch("/restservices/exercisehistory/add", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: getBearerToken(),
    },
    body: JSON.stringify(exercise),
  }).then((response) => {
    if (response.ok) {
      form.reset();
      alert("Exercisehistory added successfully!");
    } else {
      alert("Failed to add exercisehistory!");
    }
  });
}

function getExercises() {
  console.log("getExercises");
  fetch("/restservices/exercise", {
    headers: {
      Authorization: getBearerToken(),
    },
  })
    .then((response) => response.json())
    .then((data) => {
        exerciseElement.innerHTML = "";
      data.forEach((exercise) => {
        const option = document.createElement("option");
        option.value = exercise.id;
        option.innerHTML = exercise.name;
        exerciseElement.appendChild(option);
      });
    });
}

form.addEventListener("submit", addExercisehistory);
getExercises();
checkLogin();
