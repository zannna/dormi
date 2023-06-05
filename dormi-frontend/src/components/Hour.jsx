import React from 'react';
import '../styles/general.css'
const Hour = ({ changeColor, colors, k, i, j, date, setClickedHour, block }) => {

    const setClicked = (() => {
        if (block) {
            setClickedHour(date);
        }

    });

    if ((i - 1) % 4 == 0 && i != 65)
        return <div className="time" key={k} style={{
            gridArea: `${i}  / ${j} /  ${i + 1}  / ${j + 1}`,
            borderBottom: `1px solid black`, background: `${colors[k] != 0 ? colors[k] : "none"}`
        }}
            onClick={() => setClicked()}
            value={date}></div>;
    else
        return <div className="time" key={k} style={{
            gridArea: `${i}  / ${j} /  ${i + 1}  / ${j + 1}`,
            background: `${colors[k] != 0 ? colors[k] : "none"}`
        }}
            onClick={() => setClicked()}
            value={date}  ></div>;
}

export default Hour;
