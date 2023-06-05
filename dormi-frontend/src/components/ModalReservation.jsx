import React, { useState, useEffect } from 'react';
import { sendReservation } from '../context/methods';
import { getColor, setNewColor } from './Colors';
const ModalReservation = ({ scroll, position, setBlock, block, setModalPosition,
  clickedHour, device, numberOfDevice, setReservedColors, setReservedHours, setReload }) => {
  const [chosenStartHour, setChosenStartHour] = useState(false);
  const [chosenStartMinutes, setChosenStartMinutes] = useState(false);
  const [startingHour, setStartingHour] = useState("");
  const [startingMinutes, setStartingMinutes] = useState("");
  const [chosenEndingHour, setChosenEndingHour] = useState(false);
  const [chosenEndingMinutes, setChosenEndingMinutes] = useState(false);
  const [endingHour, setEndingHour] = useState("");
  const [endingMinutes, setEndingMinutes] = useState("");
  const [visible, setVisible] = useState(true);
  const [info, setInfo] = useState("");
  const hours = Array.from({ length: 16 }, (_, index) => index + 7);
  const minutes = Array.from({ length: 4 }, (_, index) => index * 15);

  useEffect(() => {
    if (!block)
      setVisible(true);
  }, [block]);

  const popupStyle = {
    position: 'absolute',
    top: position.y,
    left: position.x,
  };

  const handleCloseModal = (event) => {
    setBlock(true);
    setVisible(false);
    setModalPosition({ x: event.clientX, y: event.clientY });
    setChosenStartHour(false);
    setChosenStartMinutes(false);
    setChosenEndingHour(false);
    setChosenEndingMinutes(false);
    setStartingHour("__");
    setStartingMinutes("__");
    setEndingHour("__");
    setEndingMinutes("__");
    setInfo("");
  };
  const makeReservation = () => {
    if (startingHour && startingMinutes != null && endingHour && endingMinutes != null) {
      let start;
      let end;
      if (endingHour < startingHour || (endingHour == startingHour && startingMinutes >= endingMinutes)) {
        setInfo("The hours were provided inaapropriate");
      }
      else {
        start = new Date(clickedHour);
        start.setHours(startingHour - start.getTimezoneOffset() / 60);
        start.setMinutes(startingMinutes);
        end = new Date(clickedHour);
        end.setHours(endingHour - end.getTimezoneOffset() / 60);
        end.setMinutes(endingMinutes);
        sendReservation(start, end, device, numberOfDevice).then((result) => {
          if (result != true)
            setInfo("The hour is already occupied");
          else {
            setInfo("");
            setBlock(true);
            setVisible(false);
            let col1 = getColor();
            setNewColor();
            let col2 = getColor();
            setReservedColors([col1, col2]);
            setReservedHours([start, end]);
            setReload(true);
          }
        });

      }
    }
  }
  return (
    visible && (
      <div id="modal" style={popupStyle}>
        choose time
        <p>start</p>
        <div className="picker-container">
          <div>{startingHour ? startingHour : "__"}</div>
          <img
            src={require("../assets/black-down.svg").default}
            onClick={() => {
              setChosenStartHour(!chosenStartHour);
            }}
          ></img>
          <span>:</span>
          {chosenStartHour ? (
            <ul className="possible-time-start">
              {hours.map((h) => (
                <li key={"h1" + h} value={h} onClick={() => { setStartingHour(h); setChosenStartHour(false); }}>{h}</li>
              ))}
            </ul>
          ) : null}
          <div>{String(startingMinutes) ? startingMinutes : "__"}</div>
          <img
            src={require("../assets/black-down.svg").default}
            onClick={(event) => {
              setChosenStartMinutes(!chosenStartMinutes);
            }}
          ></img>
          {chosenStartMinutes ? (
            <ul className="possible-time-end">
              {minutes.map((m) => (
                <li key={"m1" + m} value={m} onClick={() => { setStartingMinutes(m); setChosenStartMinutes(false); }}>{m}</li>
              ))}
            </ul>
          ) : null}
        </div>
        <p>end</p>
        <div className="picker-container">
          <div>{endingHour ? endingHour : "__"}</div>
          <img
            src={require("../assets/black-down.svg").default}
            onClick={() => {
              setChosenEndingHour(!chosenEndingHour);
            }}
          ></img>
          <span>:</span>
          {chosenEndingHour ? (
            <ul className="possible-time-start">
              {hours.map((h) => (
                <li key={"h2" + h} value={h} onClick={() => { setEndingHour(h); setChosenEndingHour(false); }}>{h}</li>
              ))}
            </ul>
          ) : null}
          <div>{String(endingMinutes) ? endingMinutes : "__"}</div>
          <img
            src={require("../assets/black-down.svg").default}
            onClick={(event) => {
              setChosenEndingMinutes(!chosenEndingMinutes);
            }}
          ></img>
          {chosenEndingMinutes ? (
            <ul className="possible-time-end">
              {minutes.map((m) => (
                <li key={"m2" + m} value={m} onClick={() => { setEndingMinutes(m); setChosenEndingMinutes(false); }}>{m}</li>
              ))}
            </ul>
          ) : null}

        </div>
        <div id="reservation-buttons">
          <img src={require("../assets/reject.svg").default} onClick={handleCloseModal}></img>
          <img src={require("../assets/done.svg").default} onClick={makeReservation}></img>
        </div>
        <div id="info">
          {info}
        </div>
      </div>
    )
  );
};

export default ModalReservation;
