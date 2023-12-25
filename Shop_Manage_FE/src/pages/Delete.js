import React,{useState, useEffect, Fragment} from "react";
import { useParams } from "react-router-dom";
import Notification from "../components/Notification";
import DeleteData from "../service/DeleteData";
import "../css/DeleteForm.css"
function Delete(){
    let { name } = useParams();

    const notification = new Notification();
    
    const settingProductDeleteForm = [
        {type:"text",dataName:"id",name:"deleteProductId",placeholder:"Id",value:""},
    ]
    const settingQuantityDeleteForm = [
        {type:"text",dataName:"id",name:"deleteQuantityId",placeholder:"Id",value:""},
    ]
    const settingSizeDeleteForm = [
        {type:"text",dataName:"id",name:"deleteSizeId",placeholder:"Id",value:""},
    ]
    const settingColorDeleteForm = [
        {type:"text",dataName:"id",name:"deleteColorId",placeholder:"Id",value:""},
    ]
    let [settingDeleteForm, setSettingDeleteForm] = useState(settingProductDeleteForm);

    const handleDeleteProduct = (e) => {
        e.preventDefault();
        if(e.target.deleteProductId.value===""){
            eventError("deleteProductId","deleteProductIdError","Please enter id");
            return;
        }
        notification.comfirm({
            title:"Delete Product",
            message:"Are you sure you want to delete this product?",
            handleAccept:()=>{DeleteData(name,e.target.deleteProductId.value)},
            handleCancel:null,
            titleAccept:"Yes Delete It",
            titleCancel:"No"
        })
    }
    const handleDeleteQuantity = (e) => {
        e.preventDefault();
        if(e.target.deleteQuantityId.value===""){
            eventError("deleteQuantityId","deleteQuantityIdError","Please enter id");
            return;
        }
        notification.comfirm({
            title:"Delete Quantity",
            message:"Are you sure you want to delete this quantity?",
            handleAccept:()=>{DeleteData(name,e.target.deleteQuantityId.value)},
            handleCancel:null,
            titleAccept:"Yes Delete It",
            titleCancel:"No"
        })
    }
    const handleDeleteColor = (e) => {
        e.preventDefault();
        if(e.target.deleteColorId.value===""){
            eventError("deleteColorId","deleteColorIdError","Please enter id");
            return;
        }
        notification.comfirm({
            title:"Delete Color",
            message:"Are you sure you want to delete this color?",
            handleAccept:()=>{DeleteData(name,e.target.deleteColorId.value)},
            handleCancel:null,
            titleAccept:"Yes Delete It",
            titleCancel:"No"
        })
    }
    const handleDeleteSize = (e) => {
        e.preventDefault();
        if(e.target.deleteSizeId.value===""){
            eventError("deleteSizeId","deleteSizeIdError","Please enter id");
            return;
        }
        notification.comfirm({
            title:"Delete Size",
            message:"Are you sure you want to delete this size?",
            handleAccept:()=>{DeleteData(name,e.target.deleteSizeId.value)},
            handleCancel:null,
            titleAccept:"Yes Delete It",
            titleCancel:"No"
        })
    }
    
    const eventError = (name,nameError,message)=>{
        console.log(name,nameError,message);
        document.querySelector("input[name='"+name+"']").classList.add("error-class");   
        document.querySelector("input[name='"+name+"']").addEventListener("input",()=>{removeEventError(name,nameError)});
        document.querySelector("."+nameError).innerHTML = message;
        document.querySelector("."+nameError).style.display = "block";
    }

    const removeEventError = (name,nameError)=>{
        document.querySelector("input[name='"+name+"']").classList.remove("error-class"); 
        document.querySelector("."+nameError).style.display = "none";
    }

    const handleChangePath = ()=>{
        document.querySelectorAll('.inputDeleteForm > input').forEach(item => {item.classList.remove("error-class");item.value="";});
        document.querySelectorAll('.inputDeleteForm > p').forEach(item => {item.style.display = "none";});
    }

    useEffect(()=>{
        handleChangePath();
        if(name==="products") setSettingDeleteForm(settingProductDeleteForm);
        if(name==="quantities") setSettingDeleteForm(settingQuantityDeleteForm);
        if(name==="sizes") setSettingDeleteForm(settingSizeDeleteForm);
        if(name==="colors") setSettingDeleteForm(settingColorDeleteForm);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[name])

    

    const handleSubmit = (e)=>{
        e.preventDefault();
        if(name==="products") handleDeleteProduct(e);
        else if(name==="quantities") handleDeleteQuantity(e);
        else if(name==="sizes") handleDeleteSize(e);
        else if(name==="colors") handleDeleteColor(e);
    }

    return (
        <div>
            <form  onSubmit={handleSubmit} className="deleteForm"> 
                <h1> Delete {name}</h1>
                <div className="inputDeleteForm">
                    {settingDeleteForm.map((item,index)=>{
                        return(
                            <Fragment key={index}>
                                <label for={item.name}> {item.placeholder} </label>
                                <input type={item.type} name={item.name} placeholder={item.placeholder} defaultValue={""}/>
                                <p className={item.name+"Error"}></p>
                            </Fragment>
                        )
                    })}
                </div>
                <input type="submit" value="Submit" />
            </form>
        </div>
    );
}

export default Delete