import React, { useState, useEffect } from 'react';
import Hour from './Hour';
import { setNewColor, getColor } from './Colors';
import ModalReservation from './ModalReservation';
const Time = ({ scroll, reservations, monday, device, numberOfDevice, setReload }) => {

  const [colors, setColors] = useState(Array.from({ length: 420 }, (_, i) => "0"));
  const [reserved, setReserved] = useState([]);
  const [isInitialized, setIsInitialized] = useState(false);
  const [showPopup, setShowPopup] = useState(false);
  const [clickPosition, setClickPosition] = useState({ x: 700, y: 800 });
  const [block, setBlock] = useState(true);
  const [modalPosition, setModalPosition] = useState({ x: 0, y: 0 });
  const [clickedHour, setClickedHour] = useState();
  const [reservedColors, setReservedColors] = useState([]);
  const [reservedHours, setReservedHours] = useState("");
  const handleClick = (event) => {
    if (modalPosition.x != event.clientX && modalPosition.y != event.clientY) {
      setClickPosition({ x: event.clientX, y: event.clientY + window.pageYOffset });
      setBlock(false);
      setShowPopup(true);
    }
  };

  const times = [];
  let start = 7.0;
  let oldStart = 7.0;
  let a = 2;
  let b = a + 4;
  useEffect(() => {
    setIsInitialized(true);

  }, []);
  useEffect(() => {
    if (reservations && reservations.length > 0 && times.length === 464 && isInitialized) {
      setIsInitialized(false);
      setInitialColors();

    }
  }, [times]);



  const setInitialColors = () => {

    let gridIndex = 16;
    for (let i = 0; i < reservations.length; i++) {

      while (gridIndex < times.length) {
        let d1 = new Date(reservations[i][0]).getTime();
        let d2 = new Date(reservations[i][1]).getTime();
        let field = new Date(times[gridIndex].props.date).getTime();
        if (field >= d1 && field <= d2) {
          if (field === d1) {
            changeColor(gridIndex - 16, "d1", reservations[i][0]);
          }
          else if (field === d2) {
            changeColor(gridIndex - 16, "d2", reservations[i][1]);
            break;
          }
          else {
            changeColor(gridIndex - 16, "nic");
            reserved.push(gridIndex - 16);
          }
        }
        gridIndex++;

      }
    }
  }
  const changeColor = (i, change, m) => {
    setColors(prevColors => {
      const newColors = [...prevColors];
      if (change === "d1") {
        newColors[i] = getColor();
      }
      else if (change === "nic") {
        newColors[i] = prevColors[i - 1];
      }
      else if (change === "d2") {
        setNewColor();
      }
      return newColors;
    });
  };




  for (let i = 0; i < 64; i++) {
    if ((start - Math.floor(start)).toFixed(2) == 0.60)
      start += 0.40;
    if (i == 60)
      times.push(<div className="time" key={-i - 1} id={'time' + i} style={{ gridArea: `${a}  / 1 /  ${b}  / 2` }}>{parseFloat(start).toFixed(2)}</div>);
    else if (i % 4 == 0) {
      times.push(<div className="time" key={-i - 1} style={{ gridArea: `${a}  / 1 /  ${b}  / 2`, borderBottom: `1px solid black` }}>{parseFloat(start).toFixed(2)}</div>);
      a = b;
      b += 4;
    } start += 0.15;
  }
  let k = 0;
  let dateOfReservation = new Date(monday + "T07:00:00");
  for (let j = 2; j < 9; j++) {
    for (let i = 2; i < 66; i++) {
      const hourToSend = new Date(dateOfReservation.getTime());
      times.push(<Hour changeColor={changeColor} colors={colors} k={k} i={i} j={j} date={hourToSend} setClickedHour={setClickedHour} block={block} />);
      k++;
      dateOfReservation.setMinutes(dateOfReservation.getMinutes() + 15);
    }
    dateOfReservation.setDate(dateOfReservation.getDate() + 1);
    dateOfReservation.setHours(7);
  }




  return <div id="big-plan" onClick={block ? handleClick : undefined}> {showPopup && <ModalReservation position={clickPosition} setBlock={setBlock}
    block={block} setModalPosition={setModalPosition} clickedHour={clickedHour} device={device.device} numberOfDevice={numberOfDevice} setReservedColors={setReservedColors}
    setReservedHours={setReservedHours} setReload={setReload} />}<div className="plan">
      <div id="empty" className="time"> </div>
      <div id="monday" className="time">Monday</div>
      <div id="tuesday" className="time">Tuesday</div>
      <div id="wednesday" className="time">Wednesday</div>
      <div id="thursday" className="time">Thursday</div>
      <div id="friday" className="time">Friday</div>
      <div id="suturday" className="time">Suturday</div>
      <div id="sunday" className="time">Sunday</div>
      {times}
    </div>
  </div>

}
export default Time;