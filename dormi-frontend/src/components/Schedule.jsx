import React, {useState} from 'react';
import { RouterProvider } from 'react-router-dom';
import  '../styles/general.css'
import Navigation from './Navigation';
const Time =()=>{
  const content = [];
  let start=7.0;
  let oldStart=7.0;
  let a=2;
  let b=a+4;
    // for (let i = 0; i < 48; i++) {
    //   content.push('time'+i);
      
    // }
    const times=[];
      for (let i = 0; i < 64; i++) {
      if((start-Math.floor(start)).toFixed(2)==0.60)
        start+=0.40; 
      if( i ==60)
      times.push(<div class="time" id={'time'+i} style={{ gridArea:  `${a}  / 1 /  ${b}  / 2`}}>{parseFloat(start).toFixed(2)}</div>);
      else if(i%4==0)
      {times.push(<div class="time" id={'time'+i} style={{ gridArea:  `${a}  / 1 /  ${b}  / 2`, borderBottom: `1px solid black`}}>{parseFloat(start).toFixed(2)}</div>);
      a=b;
      b+=4;
      }// else
      // times.push( <div id={'time'+i} style={{ gridArea:  `${a}  / 1 /  ${b}  / 2` }}></div> );
      start+=0.15; 
    }
    for (let j = 2; j < 9; j++) {
      for (let i = 2; i < 66; i++) 
      {
        if((i-1)%4==0 && i !=65)
        times.push(<div class="time" id={'time'+j*100+i} style={{ gridArea:  `${i}  / ${j} /  ${i+1}  / ${j+1}`, borderBottom: `1px solid black` }}></div>);
        else
        times.push(<div class="time" id={'time'+j*100+i} style={{ gridArea:  `${i}  / ${j} /  ${i+1}  / ${j+1}` }}></div>);
      }
    }
  return times;
  //   return content.map(c =>{ 
  //     // a+=4;
  //     // oldStart=start;
  //     // start+=0.15; 
  //     // if((start-Math.floor(start)).toFixed(2)==0.60)
  //     //   start+=0.40; 
  //     // if((oldStart-Math.floor(oldStart)).toFixed(2)==0.00)
  //     // return <div id={c} style={{ gridArea:  2 / 1 / 6 / 2; }}>{parseFloat(oldStart).toFixed(2)}</div> 
  //     // else
  //     // return <div id={c} style={{ gridArea:  2 / 1 / 6 / 2;}}></div> 
     
  // });
  }
const Schedule = () => {
    return (
      <>
        <Navigation/>
        <div class="reservation-main">
        <div id="plan-container">
          <div id="plan-nav">
            <div id="week">17.02-24.02</div>
            <div>
              <img src={require("../assets/left-arrow.svg").default} alt="logo"></img>
              <img src={require("../assets/right-arrow.svg").default} alt="logo"></img>
            </div>
          </div>
        <div class="plan">
          <div id="empty" class="time"> </div>
          <div id="monday" class="time">Monday</div>
          <div id="tuesday" class="time">Tuesday</div>
          <div id="wednesday" class="time">Wednesday</div>
          <div id="thursday" class="time">Thursday</div>
          <div id="friday" class="time">Friday</div>
          <div id="suturday" class="time">Suturday</div>
          <div id="sunday" class="time">Sunday</div>
          {<Time/>}
        </div>
        </div>
        </div>
      
      </>
    );
  };
export default Schedule;