import React, { useState, useEffect } from 'react';
import '../styles/general.css'
import { changeProblemStatus } from '../context/methods';
import { hasExtendedRights } from '../context/jwtAuth';
const ProblemElement = ({ id, description, status, path, load, reject, done }) => {
    const [showStatus, setShowStatus] = useState(false);
    const [imgPath, setImgPath] = useState("../assets/load.svg");
    const handleMouseLeave = () => {
        setTimeout(() => { setShowStatus(false) }, 300);
    }
    const modifyPath = (num) => {
        if (num === 1)
            setImgPath(load);
        else if (num === 0)
            setImgPath(done);
        else if (num === -1)
            setImgPath(reject);

    }
    const modifyStatus =  (statusNum) => {
        changeProblemStatus(id, 0).then((changedStatus)=>{
            if (changedStatus)

            modifyPath(statusNum);
        });
        
    }
    useEffect(() => {
        modifyPath(status);
    }, [status]);


    return (<div className="problem-container" >
        <div className="problem" >
            <div className="text-container">
                {description}
            </div>
            <img src={imgPath} alt="load" onClick={() => setShowStatus(true)}></img>

            {hasExtendedRights() && showStatus &&
                <div className="status-menu" onMouseLeave={handleMouseLeave}>
                    <ul>
                        <li onClick={() => modifyStatus(0)}>
                            <img src={require("../assets/done.svg").default} alt="load" ></img>
                            done
                        </li>
                        <li onClick={() => modifyStatus(1)}>
                            <img src={require("../assets/load.svg").default} alt="load"></img>
                            make
                        </li>
                        <li onClick={() => modifyStatus(-1)}>
                            <img src={require("../assets/reject.svg").default} alt="load" ></img>
                            reject
                        </li>
                    </ul>
                </div>}

        </div>
    </div>);
}

export default ProblemElement;
