import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getAllReservations } from '../context/methods';
import '../styles/general.css'
import Navigation from './Navigation';
import Time from './Time';

const Schedule = () => {
  const device = useParams();
  const [reservations, setReservations] = useState([]);
  const [devicesList, setDevicesList] = useState([]);
  const [weeks, setWeeks] = useState([]);
  const [numberOfWeek, setNumberOfWeek] = useState(1);
  const [numberOfDevice, setNumberOfDevice] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  const [hasMounted, setHasMounted] = useState(false);

  useEffect(() => {
    setHasMounted(true);
  }, []);

  useEffect(() => {
    if (hasMounted && isLoading) {
      getAllReservations(device).then((response) => {
        setDevicesList(response.numberOfDevice);
        setWeeks(response.weeks);
        setReservations(response.reservations);
        setNumberOfDevice(0);
        setIsLoading(false);
      });
    }
  }, [hasMounted, isLoading, device]);
  useEffect(() => {
    setHasMounted(true);
    setIsLoading(true);
  }, [numberOfWeek]);

  const nextWeek = () => {
    if (numberOfWeek < 3) {
      setNumberOfWeek(prevNumberOfWeek => prevNumberOfWeek + 1);
    }
  };
  const setReload = (lb) => {
    setHasMounted(true);
    setIsLoading(true);
  }
  const previousWeek = () => {
    if (numberOfWeek > 0) {
      setNumberOfWeek(prevNumberOfWeek => prevNumberOfWeek - 1);
    }
  };

  return (
    <>
      <Navigation />
      <div className="reservation-main">
        <div id="plan-container">
          <div id="plan-nav">
            <div>
              <div id="week">{weeks.length !== 0 ? `${weeks[numberOfWeek][0]}  -  ${weeks[numberOfWeek][1]}` : ''}</div>
              {devicesList?.map((d) => {
                return <button key={d}>{d}</button>
              })}
            </div>
            <div>
              <img src={require("../assets/left-arrow.svg").default} alt="logo" onClick={previousWeek}></img>
              <img src={require("../assets/right-arrow.svg").default} alt="logo" onClick={nextWeek}></img>
            </div>
          </div>

          {!isLoading && reservations &&
            <Time reservations={reservations.length > 0 ? reservations[numberOfDevice][numberOfWeek] : null} monday={weeks[numberOfWeek][0]} device={device}
              numberOfDevice={devicesList ? devicesList[numberOfDevice] : null} setReload={setReload} />
          }

        </div>
      </div>

    </>
  );
};

export default Schedule;
