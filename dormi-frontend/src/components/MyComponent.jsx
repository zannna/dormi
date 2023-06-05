import React, { useContext } from 'react';
import AuthContext from '../AuthProvider';

const MyComponent = () => {
  const { auth, setAuth } = useContext(AuthContext);

  const handleLogin = () => {
    setAuth('logged in user');
  }

  return (<>
    <div>
      <p>Current user: {auth}</p>
      <button onClick={handleLogin}>Log in</button>
    </div></>
  );
}
export default MyComponent;




