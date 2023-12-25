import React, {useEffect, useRef, useState} from "react";
import { Link } from "react-router-dom";
import '../css/Header.css';
import { BsPerson } from "react-icons/bs";
import {FiMenu} from "react-icons/fi";
import Navbar from "./Navbar.js";
import HandleStorage from "../Func/HandleStorage";
import HandleLanguage from "../Func/HandleLanguage";
import {MdOutlineLogout} from "react-icons/md";
function Header() {
    let [dataUser, setDataUser] = useState(null);
    let [openNavbar,setOpenNavbar] = useState(false);
    const handleStorage = new HandleStorage();
    const handleLanguage = new HandleLanguage();
    let menuRef = useRef(null);
    useEffect(()=>{
        console.log("get")
        setDataUser(handleStorage.getUser());
    },[0]);
    useEffect(()=>{
        document.addEventListener('mousedown',handleClickOutside);
        return () => {
            document.removeEventListener('mousedown',handleClickOutside);
        }
    }
    ,[]);
    const handleClickOutside = (e) => {
        if(menuRef.current && !menuRef.current.contains(e.target)){
            setOpenNavbar(false);
        }
    }
    const logout = () => {
        handleStorage.clear();
        setDataUser(null);
    }
    const getLastName = (fullName) => {
        let arr = fullName.split(" ");
        return arr[arr.length-1];
    }
    const creatAvatar = (fullName) => {
        handleLanguage.toNonAccentVietnamese(getLastName(dataUser.fullName)).charAt(0).toUpperCase();
    }
    return (
        <div className="header">
            <div className="header-left">
                <div ref={menuRef} className="menu" > 
                    <div className="menu-button" onClick={()=>setOpenNavbar(!openNavbar)}> <FiMenu/> </div>
                    <div style={{display:openNavbar?'block':'none'}} className="header-navbar">
                        <Navbar/>
                    </div>
                </div>
                
            </div>
            <div className="header-right">
                <div className="header-user-infor">
                    {dataUser===null?
                        <Link to={'/login'} className="login"> <BsPerson/> </Link>:
                        <>
                            <Link to={"/userinfor"} className="user-avatar"> 
                                {dataUser.avatar!==null?
                                    <img src={"https://scontent.fhan20-1.fna.fbcdn.net/v/t39.30808-1/344301415_759435305652727_712466161310445233_n.jpg?stp=c63.48.272.272a_dst-jpg_p320x320&_nc_cat=111&ccb=1-7&_nc_sid=fe8171&_nc_ohc=MiHdim2-gv0AX-xlxBG&_nc_ht=scontent.fhan20-1.fna&oh=00_AfDrn1d9zxpi1_YvzuCaKH2sXNE7v_GobjlPpY1zagi2Ww&oe=6507ABE2"}/>
                                    :"None avatar"}  
                            </Link>
                            <button onClick={logout} className="logout"> <MdOutlineLogout/> </button>
                        </>
                    }
                </div>
            </div>
        </div>
    );
}
export default Header;