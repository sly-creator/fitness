const container = document.getElementById("container");

const getWorkouts = async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get("id");

  if (!id) {
    container.innerHTML = "No workouts found";
    return;
  }

  fetch(`/restservices/workout`, {
    headers: {
      Authorization: getBearerToken(),
    },
  })
    .then((response) => response.json())
    .then((workouts) => {
      const workout = workouts.find((workout) => workout.id == id);

      let workoutHTML = `<div>
            <img src="${workout.image}" alt="${workout.name}"/>
                <h2>${workout.name}</h2>
                <p>${workout.workoutType}</p>
                <p>${workout.description}</p>
            </div>`;

      workout.exercises.forEach((exercise) => {
        workoutHTML += `<p>${exercise.name}</p>`;
      });

      container.innerHTML = workoutHTML;
    });
};

getWorkouts();
checkLogin();
