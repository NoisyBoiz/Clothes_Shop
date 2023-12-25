import {Link} from "react-router-dom";
import {MdDeleteOutline} from "react-icons/md";
import {BiEditAlt,BiSelectMultiple} from "react-icons/bi";
import Notification from "../components/Notification";
import {PiSelection} from "react-icons/pi";

import "../css/TbColor.css"

function Color({data,select,handleSelect,handleDeleteData}){

    const notification = new Notification();

    return(
        <>
            <ul className="header-list-color"> 
                <h1> Color </h1>
                <li> Select </li>
                <li> Id </li>
                <li> Label </li>
                <li> Image </li>
                <li> Edit </li>
                <li> Delete </li>
            </ul>
            {data.map((item,index)=>{
                return(
                    <ul key={index} className={`list-color-rows ${select.includes(item.id)?"selectActive":""}`}>
                        <li> 
                            {select.includes(item.id)?<BiSelectMultiple  onClick={()=>handleSelect(item.id)} />:<PiSelection  onClick={()=>handleSelect(item.id)}/>}
                        </li>
                        <li> {item.id} </li>
                        <li> {item.label} </li>
                        <li> {item.image} </li>
                        <li> <Link to={"/edit/colors/"+item.id} > <BiEditAlt/> </Link></li>
                        <li onClick={()=>{notification.comfirm({
                            title:"Delete Color",
                            message:"Are you sure you want to delete this color?",
                            handleAccept:()=>{
                                handleDeleteData("colors",item.id)
                            },
                            handleCancel:()=>{console.log("cancel")},
                            titleAccept:"Yes Delete It",
                            titleCancel:"No"
                        })}}> <MdDeleteOutline/> </li>
                    </ul>
                )
            })}
        </>
    )
}

export default Color;