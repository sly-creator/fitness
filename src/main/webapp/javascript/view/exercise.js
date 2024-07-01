const container = document.getElementById("container");

const getExercise = async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const id = urlParams.get("id");

  if (!id) {
    container.innerHTML = "No exercise found";
    return;
  }

  fetch(`/restservices/exercise`, {
    headers: {
      Authorization: getBearerToken(),
    },
  })
    .then((response) => response.json())
    .then((exercises) => {
      const data = exercises.find((exercise) => exercise.id == id);

      let exerciseHTML = `<div>
            <img src="${data.image}" alt="${data.name}"/>
                <h2>${data.name}</h2>
                <p>${data.muscleType}</p>
                <p>${data.description}</p>
                <p>${data.exerciseType}</p>
                <p>${data.reps}</p>
                <p>${data.sets}</p>
            </div>`;

      container.innerHTML = exerciseHTML;
    });
};

getExercise();
checkLogin();
