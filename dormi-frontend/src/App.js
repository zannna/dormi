import Login from './components/Login';
import Register from './components/Register';
import Reserve from './components/Reserve';
import Informations from './components/Informations';
import Problems from './components/Problems';
import Schedule from './components/Schedule';
import First from './components/First';
import { Routes, Route } from "react-router-dom";
function App() {
  return (
    <>
    <Routes>
  <Route path='/' element={<First/>} />
  <Route path='/login' element={<Login/>} />
  <Route path='/register' element={<Register/>} />
  <Route path='/reserve' element={<Reserve/>} />
  <Route path='/informations' element={<Informations/>} />
  <Route path='/problems' element={<Problems/>} />
  <Route path='/schedule' element={<Schedule/>} />
  </Routes>
  </>
  );
}

export default App;
