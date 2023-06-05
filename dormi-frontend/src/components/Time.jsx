import React, { useReducer, useCallback, useEffect } from 'react';
import Hour from './Hour';
import { setNewColor, getColor } from './Colors';
import ModalReservation from './ModalReservation';
import { reducer, initialState } from '../utils/reducers/timeReducer';


const Time = ({ reservations, monday, device, numberOfDevice, setReload }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    dispatch({ type: "TOGGLE_INITIALIZATION" });
  }, []);

  ///??
  useEffect(() => {
    generateTimes(); 
    generateHourComponents();
  });
  ///??

  useEffect(() => {
    if (reservations && reservations.length > 0 && state.colors.length === 464 && state.isInitialized) {
      dispatch({ type: "TOGGLE_INITIALIZATION" });
      setInitialColors();
    }
  }, [state.colors, state.isInitialized, reservations]);

  const handleClick = useCallback((event) => {
    if (state.modalPosition.x !== event.clientX && state.modalPosition.y !== event.clientY) {
      dispatch({ type: "SET_CLICK_POSITION", payload: { x: event.clientX, y: event.clientY + window.pageYOffset } });
      dispatch({ type: "TOGGLE_BLOCK" });
      dispatch({ type: "TOGGLE_POPUP" });
    }
  }, [state.modalPosition.x, state.modalPosition.y]);

  const changeColor = useCallback((i, change) => {
    if (change === "d1") {
      dispatch({ type: "SET_COLORS", payload: state.colors.map((color, index) => index === i ? getColor() : color) });
    }
    else if (change === "nic") {
      dispatch({ type: "SET_COLORS", payload: state.colors.map((color, index) => index === i ? state.colors[i - 1] : color) });
    }
    else if (change === "d2") {
      setNewColor();
    }
  }, [state.colors]);

  const setInitialColors = () => {
    let gridIndex = 16;
    for (let i = 0; i < reservations.length; i++) {
      while (gridIndex < state.colors.length) {
        let d1 = new Date(reservations[i][0]).getTime();
        let d2 = new Date(reservations[i][1]).getTime();
        let field = new Date(state.colors[gridIndex].props.date).getTime();
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
            dispatch({ type: "SET_RESERVED", payload: [...state.reserved, gridIndex - 16] });
          }
        }
        gridIndex++;
      }
    }
  }

  const generateTimes = () => {
    let start = 7.0;
    let a = 2;
    let b = a + 4;
    for (let i = 0; i < 64; i++) {
        if ((start - Math.floor(start)).toFixed(2) === "0.60")
            start += 0.40;
        if (i === 60)
            times.push(<div className="time" key={-i - 1} id={'time' + i} style={{ gridArea: `${a}  / 1 /  ${b}  / 2` }}>{parseFloat(start).toFixed(2)}</div>);
        else if (i % 4 === 0) {
            times.push(<div className="time" key={-i - 1} style={{ gridArea: `${a}  / 1 /  ${b}  / 2`, borderBottom: `1px solid black` }}>{parseFloat(start).toFixed(2)}</div>);
            a = b;
            b += 4;
        } start += 0.15;
    }
  }


  const generateHourComponents = () => {
    let k = 0;
    let dateOfReservation = new Date(monday + "T07:00:00");
    for (let j = 2; j < 9; j++) {
        for (let i = 2; i < 66; i++) {
            const hourToSend = new Date(dateOfReservation.getTime());
            times.push(<Hour 
                changeColor={changeColor} 
                colors={state.colors} 
                k={k} 
                i={i} 
                j={j} 
                date={hourToSend} 
                setClickedHour={() => dispatch({ type: "SET_CLICKED_HOUR", payload: hourToSend })} 
                block={state.block} 
            />);
            k++;
            dateOfReservation.setMinutes(dateOfReservation.getMinutes() + 15);
        }
        dateOfReservation.setDate(dateOfReservation.getDate() + 1);
        dateOfReservation.setHours(7);
    }
  }

  const times = [];

  return (
    <div id="big-plan" onClick={state.block ? handleClick : undefined}> 
        {state.showPopup && 
            <ModalReservation 
                position={state.clickPosition} 
                setBlock={() => dispatch({ type: "TOGGLE_BLOCK" })} 
                block={state.block} 
                setModalPosition={(pos) => dispatch({ type: "SET_MODAL_POSITION", payload: pos })} 
                clickedHour={state.clickedHour} 
                device={device.device} 
                numberOfDevice={numberOfDevice} 
                setReservedColors={(colors) => dispatch({ type: "SET_RESERVED_COLORS", payload: colors })} 
                setReservedHours={(hours) => dispatch({ type: "SET_RESERVED_HOURS", payload: hours })} 
                setReload={setReload} 
            />
        }
        <div className="plan">
            <div id="empty" className="time"> </div>
            <div id="monday" className="time">Monday</div>
            <div id="tuesday" className="time">Tuesday</div>
            <div id="wednesday" className="time">Wednesday</div>
            <div id="thursday" className="time">Thursday</div>
            <div id="friday" className="time">Friday</div>
            <div id="suturday" className="time">Saturday</div>
            <div id="sunday" className="time">Sunday</div>
            {times}
        </div>
    </div>
  );
}

export default Time;
