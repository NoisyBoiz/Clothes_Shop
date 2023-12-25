import {Link} from "react-router-dom";
import {MdDeleteOutline} from "react-icons/md";
import {BiEditAlt,BiSelectMultiple} from "react-icons/bi";
import Notification from "../components/Notification";
import {PiSelection} from "react-icons/pi";
import HandleDate from "../Func/HandleDate";
import "../css/TbAdmin.css"

function Admin({data,select,handleSelect, handleDeleteData}){

    const notification = new Notification();
    const handleDate = new HandleDate();
    
    const test  = ()=>{
        console.log("test");
    }

    return(
        <div className="admin-container">
            <div className="admin-table">  
                <ul className="header-list-admin"> 
                    <h1> Admin </h1>
                    <li> Select </li>
                    <li> Id </li>
                    <li> Username </li>
                    <li> Phone </li>
                    <li> Email </li>
                    <li> FullName </li>
                    <li> Roles </li>
                    <li> Gender </li>
                    <li> Birthday </li>
                    <li> Avatar </li>
                    <li> Deleted </li>
                    <li> CreatedAt </li>
                    <li> UpdatedAt </li>
                    <li> DeletedAt </li>
                    <li> Otp </li>
                    <li> OtpFailAttempts </li>
                    <li> OtpExp </li>
                    <li> Status </li>
                    <li> Edit </li>
                    <li> Delete </li>
                </ul>
                {data.map((item,index)=>{
                    return(
                        <ul key={index} className={`list-admin-rows ${select.includes(item.id)?"selectActive":""} `}> 
                            <li> 
                                {select.includes(item.id)?<BiSelectMultiple  onClick={()=>handleSelect(item.id)} />:<PiSelection  onClick={()=>handleSelect(item.id)}/>}
                            </li>
                            <li> {item.id} </li>
                            <li> {item.username} </li>
                            <li> {item.phone} </li>
                            <li> {item.email} </li>
                            <li> {item.fullName} </li>
                            <li> {item.roles} </li>
                            <li> {item.gender?"Female":"Male"} </li>
                            <li> {(item.birthday!==null&&item.birthday!==undefined)?handleDate.toDate(item.birthday):"None"} </li>
                            <li> {item.avatar===null?"None":item.avatar} </li>
                            <li> {item.deleted===null?"False":(item.deleted?"True":"False")} </li>
                            <li> <text>{(item.createdAt!==null&&item.createdAt!==undefined)?handleDate.toDateTime(item.createdAt).split(" ").join("\n"):"None"}</text></li>
                            <li> <text>{(item.updatedAt!==null&&item.updatedAt!==undefined)?handleDate.toDateTime(item.updatedAt).split(" ").join("\n"):"None"}</text></li>
                            <li> <text>{(item.deletedAt!==null&&item.deletedAt!==undefined)?handleDate.toDateTime(item.deletedAt).split(" ").join("\n"):"None"}</text></li>
                            <li> {item.otp===null?"None":item.otp} </li>
                            <li> {item.otpFailAttempts===null?"None":item.otpFailAttempts} </li>
                            <li> <text>{(item.otpExp!==null&&item.otpExp!==undefined)?handleDate.toDateTime(item.otpExp).split(" ").join("\n"):"None"}</text></li>
                            <li> {item.status?"True":"False"} </li>
                            <li> <Link to={"/edit/admins/"+item.id} > <BiEditAlt/> </Link></li>
                            <li onClick={()=>{
                                notification.comfirm({
                                    title:"Delete Admin",
                                    message:"Are you sure you want to delete this admin?",
                                    handleAccept:()=>{
                                        handleDeleteData("admins",item.id)
                                    },
                                    handleCancel:()=>{console.log("cancel")},
                                    titleAccept:"Yes Delete It",
                                    titleCancel:"No"
                                })
                            }}> <MdDeleteOutline/> </li>
                        </ul>
                    )
                })}
            </div>
        </div>
    )
}

export default Admin;