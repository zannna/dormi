import React, { useState, useEffect } from 'react';
import '../styles/general.css'
import Navigation from './Navigation';
import ProblemElement from './ProblemElement';
import load from "../assets/load.svg";
import reject from "../assets/reject.svg";
import done from "../assets/done.svg";
import { reportProblem, getAllUserProblems, getUnresolvedProblems } from '../context/methods';
import { hasExtendedRights} from '../context/jwtAuth';
const Problems = () => {
  const [description, setDescription] = useState("");
  const [problemList, setProblemList] = useState([]);
  useEffect(() => {
    if(hasExtendedRights())
    getUnresolvedProblems().then((result) => { setProblemList(result) });
    else
    getAllUserProblems().then((result) => { setProblemList(result) });
  }, []);
  const handleReportProblem = async (event) => {
    event.preventDefault();
    const idNewProblem = await reportProblem(description);
    if (idNewProblem) {
      const prob = { id_problem: idNewProblem, status: 1, description: description };
      setProblemList([prob, ...problemList]);
      setDescription("");
    }
  }
  return (<>
    <Navigation />
    <div className="reservation-main">
      <div id="moving-container">
        <h2><span>state of issues</span></h2>
        <div id="all-messages" >
          {
            problemList?.map((p) => {
              return (
                <ProblemElement key={p.id_problem} id={p.id_problem} description={p.description}
                  status={p.status} load={load} reject={reject} done={done} />
              )
            })
          }
        </div>
        <h2><span>report problem</span></h2>
        <form id="registration-form">
          <label>room number</label>
          <input></input>
          <label>problem</label>
          <input id="description" value={description} onChange={(event) => setDescription(event.target.value)}></input>
          <div className="button-container">
            <button className="white-button" onClick={(event) => {
              handleReportProblem(event)
            }}>send</button>
          </div>
        </form>
      </div>

    </div>
  </>

  );
};

export default Problems;
