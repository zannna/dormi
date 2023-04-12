import React from 'react';
import  '../styles/general.css'
import Navigation from './Navigation';
const Reserve = () => {
    return (
       <>
        <Navigation/>
       <div class="reservation-main">
         <div id="reservation-container">  
           <h2><span>reserve device</span></h2> 
           <div id="devices-container">
             <div class="device-button-container">
               vaccum cleaner
               <a href="/schedule">
                <button id="vac" class="device-button"><img src={require("../assets/vac.svg").default} alt="logo"></img></button>
               </a>
              </div>
             <div class="device-button-container">
                washing machine
                <a href="/schedule">
                  <button id="washer" class="device-button"><img src={require("../assets/washer.svg").default} alt="logo"></img></button>
                </a>
             </div>
             <div class="device-button-container">
                <br/>iron <br/>
                <a href="/schedule">
                  <button id="iron" class="device-button"><img src={require("../assets/iron.svg").default} alt="logo"></img></button>
                </a>
             </div>
           </div>
         </div>         
       </div>
       </>
     );
    };

export default Reserve;