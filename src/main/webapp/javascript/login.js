// HTML Elements
const loginForm = document.getElementById("login-form");
const errorMsg = document.getElementById("error-msg");
const loginButton = document.getElementById("login-button");

function login() {
  let formData = new FormData(loginForm);
  let jsonRequestBody = {
    username: formData.get("username"),
    password: formData.get("password"),
  };

  // emty fields check
  if (!jsonRequestBody.username || !jsonRequestBody.password) {
    errorMsg.innerHTML = "Please fill in all fields.";
    return;
  }

  // Send authentication request
  fetch("/restservices/authentication/login", {
    method: "POST",
    body: JSON.stringify(jsonRequestBody),
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((res) => {
      if (res.ok) {
        return res.json().then((data) => {
          // Store JWT token in localStorage upon successful login
          window.localStorage.setItem("userJWT", data.JWT);
          // Redirect to home.html
          window.location = "/public/dashboard.html";
        });
      } else {
        // Handle errors
        return res.json().then((data) => {
          throw new Error(data.msg || "Login failed");
        });
      }
    })
    .catch((err) => {
      errorMsg.innerHTML = err.message;
    });
}

// Add event listener to login button
loginButton.addEventListener("click", login);
