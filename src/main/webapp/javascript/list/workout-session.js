const workoutSessions = document.getElementById("workout-sessions");

function getWorkouts() {
  fetch("/restservices/workoutsession", {
    headers: {
      Authorization: getBearerToken(),
    },
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      data.forEach((workoutSession) => {
        let workoutSessionHTML = `<div>
                <h2>${workoutSession?.workout?.name}</h2>
                <p>
                    ${workoutSession?.startDate} - ${workoutSession?.endDate}
                </p>
                <p>${workoutSession?.workout?.description}</p>
                <img src="${workoutSession?.workout?.image}" alt="${workoutSession?.workout?.name}"/>
                <p>${workoutSession?.workout?.workoutType}</p>
                <div>
                  ${workoutSession?.workout
                    ?.map((exercise) => {
                      return `<p>${exercise.name}</p>`;
                    })
                    .join("")}
                </div>
            </div>`;
        
      
        let workoutSessionDiv = document.createElement("div");
        workoutSessionDiv.innerHTML = workoutSessionHTML;
        workoutSessions.appendChild(workoutSessionDiv);
      });
    });
}

getWorkouts();
checkLogin();
