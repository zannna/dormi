import React from 'react';
import '../styles/general.css'
import Navigation from './Navigation';

const Reserve = () => {

  return (
    <>
      <Navigation />
      <div className="reservation-main">
        <div id="reservation-container">
          <h2><span>reserve device</span></h2>
          <div id="devices-container">
            <div className="device-button-container">
              vaccum cleaner
              <a href="/schedule/vaccum">
                <button id="vac" className="device-button"><img src={require("../assets/vac.svg").default} alt="logo"></img></button>
              </a>
            </div>
            <div className="device-button-container">
              washing machine
              <a href="/schedule/washer">
                <button id="washer" className="device-button"><img src={require("../assets/washer.svg").default} alt="logo"></img></button>
              </a>
            </div>
            <div className="device-button-container">
              <br />iron <br />
              <a href="/schedule/iron">
                <button id="iron" className="device-button"><img src={require("../assets/iron.svg").default} alt="logo"></img></button>
              </a>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Reserve;