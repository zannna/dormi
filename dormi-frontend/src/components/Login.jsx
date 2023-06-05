import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/general.css'
import { sendLoginRequest } from '../context/methods';

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const navigate = useNavigate();


  const handleLoginSubmit = (event) => {
    sendLoginRequest(event, navigate, email, password, setErrorMsg).then(() => { });
  }

  return (
    <div id="first-main">
      <div id="logo-container">
        <img src={require("../assets/logo-dormi.png")} alt="logo"></img>
        <div id="logo-name">Dormi</div>
      </div>
      <form id="registration-form" onSubmit={handleLoginSubmit} >
        <label>email</label>
        <input required id="email" value={email} onChange={(event) => setEmail(event.target.value)}></input>
        <label>password</label>
        <input required id="password" value={password} onChange={(event) => setPassword(event.target.value)}></input>
        <div id="wrong-input">{errorMsg}</div>
        <div className="button-container">
          <a href='/'>
            <button type="button" className="white-button">cancel</button>
          </a>
          <button id="submit" type="submit" className="white-button" >submit</button>
        </div>
      </form>
    </div>
  );
};
export default Login;