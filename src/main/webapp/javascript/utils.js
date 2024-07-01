function parseJwt(token) {
  // Parses the jwt token to object
  const base64Url = token.split(".")[1];
  const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
  const jsonPayload = decodeURIComponent(
    window
      .atob(base64)
      .split("")
      .map(function (c) {
        return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join("")
  );

  return JSON.parse(jsonPayload);
}

function isLoggedIn() {
  // Checks if user is logged in

  // Gets users jwt from local storage
  const userJWT = window.localStorage.getItem("userJWT");

  // If user does not have a jwt (not authenticated) redirect to login page
  if (!userJWT) {
    return false;
  }

  const user = parseJwt(userJWT);

  // Checks if the jwt is still valid
  let currentTimeInSeconds = new Date().getTime() / 1000;
  if (currentTimeInSeconds > user.exp) {
    // if token is expired remove it
    window.localStorage.removeItem("userJWT");
    return false;
  }

  return true;
}

function checkLogin() {
  // Checks if user is logged in, if not redirects to login page
  console.log("Checking login");
  if (!isLoggedIn()) {
    window.location.href = "/public/login.html";
  }
}


function getBearerToken() {
  // Gets the bearer token from local
  return `Bearer ${window.localStorage.getItem("userJWT")}`;
}