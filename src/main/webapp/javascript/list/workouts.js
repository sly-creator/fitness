const workoutsContainer = document.getElementById("workouts-container");

function getWorkouts() {
  fetch("/restservices/workout", {
    headers: {
      Authorization: getBearerToken(),
    },
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      data.forEach((workout) => {
        let workoutDiv = document.createElement("div");
        let workoutHTML = `<div>
            <img src="${workout.image}" alt="${workout.name}"/>
                <h2>${workout.name}</h2>
                <p>${workout.workoutType}</p>
                <p>${workout.description}</p>
                <a href="/public/view/workout.html?id=${workout.id}">View</a>
            </div>`;

        workout.exercises.forEach((exercise) => {
          workoutHTML += `<p>${exercise.name}</p>`;
        });

        workoutDiv.innerHTML = workoutHTML;
        workoutsContainer.appendChild(workoutDiv);
      });
    });
}

getWorkouts();
checkLogin();
