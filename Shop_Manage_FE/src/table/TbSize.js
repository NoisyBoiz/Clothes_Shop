import {Link} from "react-router-dom";
import {MdDeleteOutline} from "react-icons/md";
import {BiEditAlt,BiSelectMultiple} from "react-icons/bi";
import Notification from "../components/Notification";
import {PiSelection} from "react-icons/pi";

import "../css/TbSize.css"


function Size({data,select,handleSelect,handleDeleteData}){

    const notification = new Notification();

    return(
        <>
        <ul className="header-list-size"> 
            <h1> Size </h1>
            <li> Select </li>
            <li> Id </li>
            <li> Label </li>
            <li> Edit </li>
            <li> Delete </li>
        </ul>
        {data.map((item,index)=>{
            return(
                <ul key={index} className={`list-size-rows ${select.includes(item.id)?"selectActive":""}`}> 
                    <li> 
                        {select.includes(item.id)?<BiSelectMultiple  onClick={()=>handleSelect(item.id)} />:<PiSelection  onClick={()=>handleSelect(item.id)}/>}
                    </li>
                    <li> {item.id} </li>
                    <li> {item.label} </li>
                    <li> <Link to={"/edit/sizes/"+item.id} > <BiEditAlt/> </Link></li>
                    <li onClick={()=>{
                        notification.comfirm({
                        title:"Delete Size",
                        message:"Are you sure you want to delete this size?",
                        handleAccept:()=>{
                            // handleDeleteData("sizes",item.id)
                            console.log("accept delete")
                        },
                        handleCancel:null,
                        titleAccept:"Yes Delete It",
                        titleCancel:"No"
                    })}}> <MdDeleteOutline/> </li>
                </ul>
            )
        })}
    </>
    )
}

export default Size;