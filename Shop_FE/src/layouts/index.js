import React from 'react';
import Navbar from '../components/Navbar.js';
import Header from '../components/Header.js';
import '../css/Layouts.css';
function Layout({children}){
    return (
        <div className={"layout"}>  
            <div className={"header-layout"}> <Header/> </div>
            <div className={"navbar-layout"}> <Navbar/> </div>
            <div className={"content-layout"}> {children} </div>
        </div>
    )
}
export default Layout;