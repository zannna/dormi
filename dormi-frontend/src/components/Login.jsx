import React, {useState} from 'react';
import  '../styles/general.css'

const Login = () => {
    const [email, setEmail]=useState("");
    const [password, setPassword]=useState("");
    const [errorMsg, setErrorMsg]=useState("");
    const sendLoginRequest=(event) => 
  {
      event.preventDefault();
      const data = { 
        email:email,
        password:password,
      };
    
      fetch("http://localhost:8080/login", {
        method: "POST", 
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      }).then((response) => {
        if (!response.ok) {
          throw new Error(response.status);
        }
        if(response.data.token)
        {
          
        }
       
      })
      .catch((error) => {
        if(error==400)
        setErrorMsg("")
        console.log(error)
      });
      // navigate("/login");
      
    }
    return (
      <div id="first-main">
        <div id="logo-container">
          <img src={require("../assets/logo-dormi.png")} alt="logo"></img>
          <div id="logo-name">Dormi</div>
        </div>
        <form id="registration-form" onSubmit={sendLoginRequest}>
          <label>email</label>
          <input required id="email" value={email} onChange={(event)=>setEmail(event.target.value)}></input>
          <label>password</label>
          <input required id="password" value={password} onChange={(event)=>setPassword(event.target.value)}></input>
          <div class="button-container">
                <a href='/'>
                  <button type="button" class="white-button">cancel</button>
                </a>
                <a href="/reserve">
                  <button id="submit" type="button" class="white-button">submit</button>
                </a>
            </div>
        </form>
      </div>
    );
  };
export default Login;