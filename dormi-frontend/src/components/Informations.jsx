import React, { useState, useEffect, useRef } from 'react';
import '../styles/general.css'
import Navigation from './Navigation';
import AuthContext from "../AuthProvider";
import { sendMessage, getAllMessages, getUserNumber } from '../context/methods';
import SockJS from 'sockjs-client';
import { over } from "stompjs"
let stompClient = null;
const Informations = () => {
  const [loaded, setloaded] = useState(false);
  const [newMessage, setNewMessage] = useState("");
  const [messagesList, setMessagesList] = useState([]);
  const [userNumber, setUserNumber] = useState("");
  const [topic, setTopic] = useState("");
  const scrollRef = useRef(null);

  const connect = () => {
    let url = 'http://localhost:8080/chat';
    let Sock = new SockJS(url);
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  }

  const onConnected = () => {
    setloaded(false);
    stompClient.subscribe('/topic/' + topic, onMessageReceived);


  }
  const onMessageReceived = (payload) => {
    if (loaded) {

      let payloadData = JSON.parse(payload.body);
      setMessagesList(prevMessagesList => [...prevMessagesList, payloadData]);
      setNewMessage("");

    }
  }

  const onError = (err) => {
    console.log("problem z polaczeniem");

  }
  useEffect(() => {
    if (loaded)
      connect();
  }, [loaded]);


  useEffect(() => {
    getAllMessages().then((response) => {
      setMessagesList(response);
      scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
      getUserNumber().then((id) => {
        setUserNumber(id.number);
        setTopic(id.topic);
        setloaded(true);
      });


    });

  }, []);
  useEffect(() => {
  }, [userNumber]);
  useEffect(() => {
    scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
  }, [messagesList]);
  const handleSendMessage = () => {
    sendMessage(newMessage).then((id) => { })


  }
  const createMessageElement = (owner, id, mess, num) => {
    return (owner && owner != "null" && userNumber != num) ? <div className="message" key={id}>
      <h1>{owner}</h1>
      <p>{mess}</p>
    </div> :
      <div className="user-message">
        <p>{mess}</p>
      </div>
  }
  return (
    <>
      <Navigation />
      <div className="reservation-main">
        <div id="moving-container">
          <h2><span>informations</span></h2>
          <div id="all-messages" ref={scrollRef}>
            {messagesList?.map((p) => {
              return (createMessageElement(p.owner, p.id_mess, p.message, p.numberOfOwner));
            })}

          </div>
          <div className="input-container">
            <input value={newMessage} onChange={(event) => setNewMessage(event.target.value)}></input>
            <button id="send-button" onClick={() => handleSendMessage()} type="submit"><img src={require("../assets/send.svg").default} alt="send-icon"></img></button>
          </div>
        </div>

      </div>
    </>

  );
};



export default Informations;
