import {Link} from "react-router-dom";
import {MdDeleteOutline} from "react-icons/md";
import {BiEditAlt,BiSelectMultiple} from "react-icons/bi";
import Notification from "../components/Notification";
import {PiSelection} from "react-icons/pi";

import "../css/TbProduct.css"

function Product({data,select,handleSelect,handleDeleteData}){

    const notification = new Notification();

    return(
        <div className="product-container">
        <div className="product-table">  
            <ul className="header-list-product"> 
                <h1> Product </h1>
                <li> Select </li>
                <li> Id </li>
                <li> CategoryId </li>
                <li> SizeId </li>
                <li> ColorId </li>
                <li> Sku </li>
                <li> Name </li>
                <li> Description </li>
                <li> RegularPrice </li>
                <li> FinalPrice </li>
                <li> Image </li>
                <li> Materials </li>
                <li> Instruction </li>
                <li> Deleted </li>
                <li> CreatedAt </li>
                <li> UpdatedAt </li>
                <li> DeletedAt </li>
                <li> Edit </li>
                <li> Delete </li>
            </ul>
            {data.map((item,index)=>{
                return(
                    <ul key={index} className={`list-product-rows ${select.includes(item.id)?"selectActive":""}`}> 
                        <li> 
                            {select.includes(item.id)?<BiSelectMultiple  onClick={()=>handleSelect(item.id)} />:<PiSelection  onClick={()=>handleSelect(item.id)}/>}
                        </li>
                        <li> {item.id} </li>
                        <li> {item.categoryId} </li>
                        <li> <text> {(item.sizeId!==null&&item.sizeId!==undefined)&&item.sizeId.split(",").join("\n")} </text> </li>
                        <li> <text> {(item.colorId!==null&&item.colorId!==undefined)&&item.colorId.split(",").join("\n")} </text> </li>
                        <li> {item.sku} </li>
                        <li> {item.name} </li>
                        <li> <text> {(item.description!==null&&item.description!==undefined)&&item.description.split(/,|\./).join("\n")} </text> </li>
                        <li> {item.regularPrice} </li>
                        <li> {item.finalPrice} </li>
                        <li> {item.image} </li>
                        <li> <text> {(item.materials!==null&&item.materials!==undefined)&&item.materials.split(/<br>|<br|<b>|<\/b>/).join("\n")} </text> </li>
                        <li> <text> {(item.instruction!==null&&item.instruction!==undefined)&&item.instruction.split(/<br>|<br|<b>|<\/b>/).join("\n")} </text> </li>
                        <li> {item.deleted===null?"False":(item.deleted?"True":"False")} </li>
                        <li> <text> {(item.createdAt!==null&&item.createdAt!==undefined)&&item.createdAt.split(/T/).join("\nT").split(/\+/).join("\n+")} </text> </li>
                        <li> <text> {(item.updatedAt!==null&&item.updatedAt!==undefined)?item.updatedAt.split(/T/).join("\nT").split(/\+/).join("\n+"):"None"} </text> </li>
                        <li> <text> {(item.deletedAt!==null&&item.deletedAt!==undefined)?item.deletedAt.split(/T/).join("\nT").split(/\+/).join("\n+"):"None"} </text> </li>
                        <li> <Link to={"/edit/products/"+item.id} > <BiEditAlt/> </Link></li>
                        <li onClick={()=>{
                            notification.comfirm({
                                title:"Delete Product",
                                message:"Are you sure you want to delete this product?",
                                handleAccept:()=>{
                                    // handleDeleteData("products",item.id)
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
        </div>
    </div>
    )
}

export default Product;