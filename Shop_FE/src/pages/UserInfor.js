import React, { useState, useEffect } from "react";
import HandleStorage from "../Func/HandleStorage";
import "../css/UserInfor.css";
import HandleDate from "../Func/HandleDate";
function UserInfor() {
    let [userInfor,setUserInfor] = useState(null);
    const handleStorage = new HandleStorage();
    const handleDate = new HandleDate();
    useEffect(()=>{
        setUserInfor(handleStorage.getUser());
    },[0]);

    return (
        <>
        {userInfor!==null?
            <div className="userInfor">
                <div className="userInfor-card"> 
                    <img src={"https://scontent.fhan20-1.fna.fbcdn.net/v/t39.30808-1/344301415_759435305652727_712466161310445233_n.jpg?stp=c63.48.272.272a_dst-jpg_p320x320&_nc_cat=111&ccb=1-7&_nc_sid=fe8171&_nc_ohc=MiHdim2-gv0AX-xlxBG&_nc_ht=scontent.fhan20-1.fna&oh=00_AfDrn1d9zxpi1_YvzuCaKH2sXNE7v_GobjlPpY1zagi2Ww&oe=6507ABE2"}/>
                    <div>
                        <h1> {userInfor.fullName} </h1>
                        <ul>
                            <li> Phone: </li> <li> {userInfor.phone} </li>
                            <li> Email: </li> <li> {userInfor.email} </li>
                            <li> Roles: </li> <li> {userInfor.roles} </li>
                            <li> Gender: </li> <li> {userInfor.gender===null?"Male":"Female"} </li>
                            <li> Birthday: </li> <li> {handleDate.toDate(userInfor.birthday)} </li>
                        </ul>
                    </div>
                </div>
            </div>
            :""
        }
        </>
    )
}

export default UserInfor;