import jwt_decode from "jwt-decode";
const URL = "http://localhost:8080/"

export const hasJwtValid = () => {
  const accessToken = localStorage.getItem("access");
  const refreshToken = localStorage.getItem("refresh");

  if (accessToken && refreshToken) {
    const decodedAccess = jwt_decode(accessToken);
    console.log(new Date(decodedAccess.exp * 1000).toString());
    console.log(new Date(Date.now()).toString());
    if (decodedAccess.exp * 1000 < Date.now()) {

      const decodedRefresh = jwt_decode(refreshToken);
      if (decodedRefresh.exp * 1000 > Date.now()) {
        const result = getRefreshToken(refreshToken).then((result) => {
          if (result == true) {
            return true;
          }
          else {
            clearLocalStorage();
            return false;
          }
        });

      }
      else
        return false;


    }
    else
      return true;
  }
  else
    return false;

}
export const getRefreshToken = async (refreshToken) => {
  const refreshURL = "refresh";
  const refreshTokenObj = { refreshToken };
  console.log(JSON.stringify(refreshTokenObj));

  return fetch(URL + refreshURL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(refreshTokenObj),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(res.status);
      }
      return res.json();
    })
    .then((response) => {
      console.log(response.token);
      if (response.token != null) {
        localStorage.setItem("access", response.token);
        localStorage.setItem("refresh", response.refresh_token);
        return true;
      } else {
        return false;
      }
    })
    .catch((error) => {
      console.error(error);
      return false;
    });

}
export const clearLocalStorage = () => {
  localStorage.removeItem('access');
  localStorage.removeItem('refresh');
}
export const createHeaderWithJwt = () => {
  return ("Bearer " + localStorage.getItem("access"));
}
export const getRoles = () => {
  const decodedAccess = jwt_decode(localStorage.getItem("access"));
  return decodedAccess.authorities;
}
export const hasExtendedRights = () => {
  const roles = ["ADMIN", "EDITOR"];
  const userRoles = getRoles();
  return userRoles.find(role => roles.includes(role));
}




