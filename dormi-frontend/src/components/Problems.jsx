import React from 'react';
import  '../styles/general.css'
import Navigation from './Navigation';
const Problems = () => {
  return (  <>
        <Navigation/>
        <div class="reservation-main">
              <div id="moving-container">
                <h2><span>state of issues</span></h2> 
                <div class="problem">
                    <div class="text-container">
                    zepsuty grzejnik
                    </div>  
                    <img src={require("../assets/load.svg").default} alt="load"></img> 
                </div>
                <div class="problem">
                    <div class="text-container">
                    zepsuty grzejnikaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbccccc
                    </div>  
                    <img src={require("../assets/done.svg").default} alt="load"></img> 
                </div>
                <div class="problem">
                    <div class="text-container">
                    zepsuty czajnik
                    </div>  
                    <img src={require("../assets/reject.svg").default} alt="load"></img> 
                </div>
                <h2><span>report problem</span></h2> 
                <form id="registration-form">
                  <label>room number</label>
                  <input></input>
                  <label>problem</label>
                  <input></input>
                  <div class="button-container">
                      <button class="white-button" >send</button>
                  </div>
                </form>
            </div>
            
        </div>
    </>

  );
};

export default Problems;
