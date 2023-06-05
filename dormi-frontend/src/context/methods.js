import { clearLocalStorage, hasJwtValid, createHeaderWithJwt } from './jwtAuth';
const URL = "http://localhost:8080/"
export const sendLoginRequest = (event, navigate, email, password, setErrorMsg) => {
  event.preventDefault();
  const data = {
    email: email,
    password: password,
  };
  return fetch(URL + "login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then(res => {
      if (!res.ok) {
        throw new Error(res.status);
      }
      return res.json();
    })
    .then((response) => {
      if (response.token != null) {
        localStorage.setItem('access', response.token);
        localStorage.setItem('refresh', response.refresh_token);

        navigate("/reserve");
      }
    })
    .catch((error) => {
      setErrorMsg("Bad credentials");
      if (error == 403) {
        console.log(error);
        setErrorMsg("bad credentials");
      }
    });

}
export const sendLogoutRequest = () => {
  fetch(URL + "mylogout").then(clearLocalStorage());
  window.location.reload();
}

export const registerUser = (navigate, email, password, username, surname, university, dormitory, room, setErrorMsg) => {
  return fetch(URL + "register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email, password, username, surname, university, dormitory, room }),
  })
    .then(res => {
      if (!res.ok) {
        throw new Error(res.status);
      }
      else
        navigate("/login");

    })
    .catch((error) => {
      if (error == 403) {
        console.log(error);
        setErrorMsg("bad credentials");
      }
    });
}

export const sendRequest = (selectedMethod, selectedBody, url) => {
  const elm = selectedBody ?
    {
      method: selectedMethod,
      headers: {
        "Content-Type": "application/json",
        'Authorization': createHeaderWithJwt()
      },
      body: JSON.stringify(selectedBody),
    } : {
      method: selectedMethod,
      headers: {
        "Content-Type": "application/json",
        'Authorization': createHeaderWithJwt()
      },

    };
  if (hasJwtValid())
    return fetch(URL + url, elm)
      .then(res => {
        if (!res.ok) {
          return res.status;
        }
        return res.json();
      })
      .then(response => {
        return response;
      })
      .catch(error => {

        if (error == 400)
          console.log(error)
        if (error = SyntaxError)
          return true;
      });

}
export const getAllMessages = () => {
  return sendRequest("GET", null, "all").then((response) => {
    return response;
  });
}
export const sendMessage = (message) => {
  return sendRequest("POST", { message: message }, "messages/send").then((response) => {
    return response;
  });

}
export const getAllUserProblems = () => {

  return sendRequest("GET", null, "userProblems").then((response) => { return response });
}
export const getUnresolvedProblems = () => {

  return sendRequest("GET", null, "unresolvedProblems").then((response) => { return response });
}
export const changeProblemStatus = (id_problem, status) => {
  return sendRequest("PUT", { id_problem, status }, "status").then((response) => {
    if (response)
      return status;
    else
      return null;
  });
}
export const reportProblem = (description) => {
  return sendRequest("POST", { description: description }, "addProblem").then((response) => {
    return response;
  });

}
export const getAllReservations = (device) => {
  return sendRequest("GET", null, "reservations/" + device.device).then((response) => {
    return response;
  });
}
export const sendReservation = (startDate, endDate, device, numberOfDevice) => {
  return sendRequest("POST", { device, numberOfDevice: numberOfDevice, startDate: startDate, endDate: endDate }, "addReservation").then((response) => {
    return response;
  });
}
export const getUserNumber = () => {
  return sendRequest("POST", null, "numberOfUser").then((response) => {
    return response;
  });
}
