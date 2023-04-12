import React, {useEffect, useState} from 'react';
import { useNavigate } from "react-router-dom";
import  '../styles/general.css'

const Register = () => {
  const [valid, setValid]=useState(true);
  const [email, setEmail]=useState("");
  const [password, setPassword]=useState("");
  const [repeatedPassword, setRepeatedPassword]=useState("");
  const [username, setUsername]=useState("");
  const [surname, setSurname]=useState("");
  const [university, setUniversity]=useState("");
  const [dormitory, setDormitory]=useState("");
  const [room, setRoom]=useState("");
  const [blankEmail, setBlankEmail]=useState(true); 
  const [validEmail, setValidEmail]=useState(false);
  const emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  const [validPassword, setValidPassword]=useState(false);
  const [blankPassword, setBlankPassword]=useState(true);
  const [errorMsg, setErrorMsg]=useState("");
  const navigate = useNavigate();
  useEffect(()=>
  {   if(email.trim().length !== 0)
        {
          setBlankEmail(false);
          setValidEmail(emailRegex.test(email));
        }
       
  }, [email])

  useEffect(()=>
  {
    if(password.trim().length !== 0 && repeatedPassword.trim().length !== 0)
      {
        setBlankPassword(false);
        if( password===repeatedPassword)
          setValidPassword(true);
        else
          setValidPassword(false);

      }
  },[password, repeatedPassword])

  useEffect(() => {
    console.log(validPassword);
  }, [validPassword]);

  const sendRegisterRequest=(event) => 
  {
      event.preventDefault();
      const data = { 
        username: username,
        email:email,
        password:password,
        surname:surname,
        university:university,
        dormitory:dormitory,
        room:room
      };
    
      fetch("http://localhost:8080/register", {
        method: "POST", 
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      }).then((response) => {
        if (!response.ok) {
          throw new Error(response.status);
        }
       
      })
      .catch((error) => {
        if(error==400)
        setErrorMsg("Email already taken")
        console.log(error)
      });
      navigate("/login");
      
    }
    return (
      <div id="first-main">
        <div id="logo-container">
          <img src={require("../assets/logo-dormi.png")} alt="logo"></img>
          <div id="logo-name">Dormi</div>
        </div>
        <form id="registration-form" onSubmit={sendRegisterRequest}>
          <label>email</label>
          <input id="email" value={email} onChange={(event)=>setEmail(event.target.value)}   className={validEmail || blankEmail ? 'good-input': 'wrong-input' } ></input>
          <label>password</label>
          <input id="password" type="password" value={password} onChange={(event)=>setPassword(event.target.value)} className={validPassword || blankPassword ? 'good-input': 'wrong-input' }></input>
          <label>repeatedPassword</label>
          <input id="repeatedPassword" type="password" value={repeatedPassword} onChange={(event)=>setRepeatedPassword(event.target.value)} className={validPassword || blankPassword ? 'good-input': 'wrong-input' }></input>
          <label>name</label>
          <input id="username" value={username} onChange={(event)=>setUsername(event.target.value)} className={valid ? 'good-input': 'wrong-input' }></input>
          <label>surname</label>
          <input id="surname" value={surname} onChange={(event)=>setSurname(event.target.value)} className={valid ? 'good-input': 'wrong-input' }></input>
          <label>university</label>
          <input id="university" value={university} onChange={(event)=>setUniversity(event.target.value)} className={valid ? 'good-input': 'wrong-input' }></input>
          <label>name of dormitory</label>
          <input id="dormitory" value={dormitory} onChange={(event)=>setDormitory(event.target.value)} className={valid ? 'good-input': 'wrong-input' }></input>
          <label>number of room</label>
          <input id="room" value={room} onChange={(event)=>setRoom(event.target.value)} className={valid ? 'good-input': 'wrong-input' }></input>
          <div class="button-container">
                <a href='/'>
                  <button type="button" class="white-button">cancel</button>
                </a>
                  <button disabled={!validEmail || !validPassword ? true : false} id="submit" type="submit" class="white-button">submit</button>
            </div>
        </form>
      </div>
    );
  };
export default Register;