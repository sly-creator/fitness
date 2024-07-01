const workoutsContainer = document.getElementById("exercise-history-container");

function getExerciseHistory() {
  fetch("/restservices/exercisehistory", {
    headers: {
      Authorization: getBearerToken(),
    },
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      data.forEach((history) => {
        let historyDiv = document.createElement("div");
        let historyHTML = `<div>
                <h2>exercisename: ${history.exercise.name}</h2>
                <p>sets: ${history.sets}</p>
                <p>eight: ${history.weight}</p>
                <p>reps: ${history.exercise.reps}</p>
                <p>id: ${history.id}</p>
                
            </div>`;
            

            historyDiv.innerHTML = historyHTML;
        workoutsContainer.appendChild(historyDiv);
      });
    });
}

getExerciseHistory();
checkLogin();
