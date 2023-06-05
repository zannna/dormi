import React from 'react';
import '../styles/general.css'
const First = () => {
  return (<div id="first-main">
    <div id="logo-container">
      <img src={require("../assets/logo-dormi.png")} alt="logo"></img>
      <div id="logo-name">Dormi</div>
    </div>
    <div>
      <h1>Simplify yor life in dormitory</h1>
      <p>Dormi allows residents to reserve equipments, report<br />
        defects or issues in their rooms or common areas,<br />
        communicate with each other and with the management<br />
        team.</p>
      <div className="button-container">
        <a href="/login">
          <button className="white-button">Sign in</button>
        </a>
        <a href="/register">
          <button className="white-button" >Sign up</button>
        </a>
      </div>
    </div>
  </div>

  );
};

export default First;