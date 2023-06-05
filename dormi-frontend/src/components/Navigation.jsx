import React, { useState } from 'react';
import '../styles/general.css'
import { sendLogoutRequest } from '../context/methods';
const Navigation = () => {
    const [profile, setProfile] = useState(false);
    const logout = () => {
        sendLogoutRequest();
    }
    return (
        <nav id="main-nav">
            <div id="nav-logo-container">
                <img src={require("../assets/logo-dormi.png")} alt="logo"></img>
                <div id="logo-name">Dormi</div>
            </div>
            <ul id="app-options">
                <a href="/reserve"><li>reserve</li></a>
                <a href="/informations"><li>informations</li></a>
                <a href="/problems"><li>problems</li></a>
            </ul>
            <div className="dropdown" onClick={() => setProfile(!profile)}>
                <img id="profile-img" src={require("../assets/ludzik.svg").default} alt="profile"></img>
                {
                    profile &&
                    <div className="dropdown-menu">
                        <a href="/" onClick={logout}><button>logout</button></a>
                    </div>
                }
            </div>
        </nav>
    );

};
export default Navigation;
