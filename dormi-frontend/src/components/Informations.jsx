import React from 'react';
import  '../styles/general.css'
import Navigation from './Navigation';
const Informations = () => {
  return (  
    <>
    <Navigation/>
    <div class="reservation-main">
          <div id="moving-container">
            <h2><span>informations</span></h2> 
            <div id="all-messages">
            <div class="message">
              <img src={require("../assets/chłop.png")} alt="user-photo"></img>
                <h1>imie nazwisko</h1>
                <p>tralalalalala</p>
            </div>
            <div class="message">
              <img src={require("../assets/typiara.jpeg")} alt="user-photo"></img>
                <h1>imie nazwisko</h1>
                <p>tralalalalala</p>
            </div>
            <div class="user-message">
                <p>tralalalalala</p>
            </div>
            <div class="user-message">
                <p>tralalalalala</p>
            </div>
            <div class="user-message">
                <p>tralalalalala</p>
            </div>
          </div>
            <div className="input-container">
          <input></input>
          <button id="send-button"><img src={require("../assets/send.svg").default} alt="send-icon"></img></button> 
        </div>
        </div>
       
    </div>
</>

  );
};

export default Informations;
