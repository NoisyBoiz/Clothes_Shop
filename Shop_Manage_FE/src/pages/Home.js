import React,{useEffect,useState} from "react";
import "../css/Home.css"
import HandleStorage from "../Func/HandleStorage";

function Home() {

    // chrome.exe --disable-web-security --user-data-dir="c:/ChromeDevSession"

    let [userInfor,setUserInfor] = useState(null);
    const handleStorage = new HandleStorage();

    useEffect(()=>{
        setUserInfor(handleStorage.getUser());
    },[0]);

    return (
        <>
            <h1> Hello! </h1>
            {userInfor!==null?<h2>
                Welcome Back {userInfor.fullName}!
            </h2>:""}
        </>
    )
}
export default Home;