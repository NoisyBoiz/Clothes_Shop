import {Link} from "react-router-dom";
import {MdDeleteOutline} from "react-icons/md";
import {BiEditAlt,BiSelectMultiple} from "react-icons/bi";
import Notification from "../components/Notification";
import {PiSelection} from "react-icons/pi";

import "../css/TbQuantity.css"

function Quantity({data,select,handleSelect,handleDeleteData}){

    const notification = new Notification();

    return(
        <>  
            <ul className="header-list-quantity"> 
                <h1> Quantity </h1>
                <li> Select </li>
                <li> Id </li>
                <li> Sku </li>
                <li> ProductId </li>
                <li> SizeId </li>
                <li> ColorId </li>
                <li> Value </li>
                <li> MediaGallery </li>
                <li> Edit </li>
                <li> Delete </li>
            </ul>
            {data.map((item,index)=>{
                return(
                    <ul key={index} className={`list-quantity-rows ${select.includes(item.id)?"selectActive":""}`}> 
                        <li> 
                            {select.includes(item.id)?<BiSelectMultiple  onClick={()=>handleSelect(item.id)} />:<PiSelection  onClick={()=>handleSelect(item.id)}/>}
                        </li>
                        <li> {item.id} </li>
                        <li> {item.sku} </li>
                        <li> {item.productId} </li>
                        <li> {item.sizeId} </li>
                        <li> {item.colorId} </li>
                        <li> {item.value} </li>
                        <li> {(item.mediaGallery!==null&&item.mediaGallery!==undefined)&&item.mediaGallery.split(",").join("\n")} </li>
                        <li> <Link to={"/edit/quantities/"+item.id} > <BiEditAlt/> </Link></li>
                        <li onClick={()=>{
                            notification.comfirm({
                                title:"Delete Quantity",
                                message:"Are you sure you want to delete this quantity?",
                                handleAccept:()=>{
                                    // handleDeleteData("quantities",item.id)
                                    console.log("accept delete")
                                },
                                handleCancel:()=>{console.log("cancel")},
                                titleAccept:"Yes Delete It",
                                titleCancel:"No"
                            })
                        }}> <MdDeleteOutline/> </li>
                    </ul>
                )
            })}
        </>
    )
}

export default Quantity;