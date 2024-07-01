const workoutsContainer = document.getElementById("exercise-container");

function getExercise() {
  fetch("/restservices/exercise", {
    headers: {
      Authorization: getBearerToken(),
    },
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      data.forEach((exercise) => {
        let exerciseDiv = document.createElement("div");
        let exerciseHTML = `<div>
            <img src="${exercise.image}" alt="${exercise.name}"/>
                <h2>${exercise.name}</h2>
                <p>${exercise.muscleType}</p>
                <p>${exercise.description}</p>
                <p>${exercise.exerciseType}</p>
                <p>${exercise.reps}</p>
                <p>${exercise.sets}</p>
                <a href="/public/view/exercise.html?id=${exercise.id}">View</a>
            </div>`;

        exerciseDiv.innerHTML = exerciseHTML;
        workoutsContainer.appendChild(exerciseDiv);
      });
    });
}

getExercise();
checkLogin();
