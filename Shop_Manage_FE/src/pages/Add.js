import React, {useState, useEffect, Fragment } from "react";
import { useParams } from "react-router-dom";
import "../css/AddForm.css"
import AddData from "../service/AddData";
import HandleDate from "../Func/HandleDate";
function Add(){
    let { name } = useParams();
    let [settingAddForm, setSettingAddForm] = useState([]);

    const addData = new AddData();
    const handleDate = new HandleDate();

    const settingAdminEditForm = [
        {type:"text",dataName:"id",name:"editAdminId",placeholder:"Id",nullable:false,value:""},
        {type:"text",dataName:"username",name:"editAdminUsername",placeholder:"Username",nullable:false,value:""},
        {type:"text",dataName:"password",name:"editAdminPassword",placeholder:"Password",nullable:false,value:""},
        {type:"text",dataName:"phone",name:"editAdminPhone",placeholder:"Phone",nullable:false,value:""},
        {type:"text",dataName:"email",name:"editAdminEmail",placeholder:"Email",nullable:false,value:""},
        {type:"text",dataName:"fullName",name:"editAdminFullName",placeholder:"FullName",nullable:false,value:""},
        {type:"text",dataName:"roles",name:"editAdminRoles",placeholder:"Roles",nullable:false,value:""},
        {type:"text",dataName:"gender",name:"editAdminGender",placeholder:"Gender",nullable:false,value:""},
        {type:"date",dataName:"birthday",name:"editAdminBirthday",placeholder:"Birthday",nullable:false,value:""},
        {type:"text",dataName:"avatar",name:"editAdminAvatar",placeholder:"Avatar",nullable:true,value:""},
    ]

    const settingCustomerEditForm = [
        {type:"text",dataName:"id",name:"editCustomerId",placeholder:"Id",nullable:false,value:""},
        {type:"text",dataName:"username",name:"editCustomerUsername",placeholder:"Username",nullable:false,value:""},
        {type:"text",dataName:"password",name:"editCustomerPassword",placeholder:"Password",nullable:false,value:""},
        {type:"text",dataName:"phone",name:"editCustomerPhone",placeholder:"Phone",nullable:false,value:""},
        {type:"text",dataName:"email",name:"editCustomerEmail",placeholder:"Email",nullable:false,value:""},
        {type:"text",dataName:"fullName",name:"editCustomerFullName",placeholder:"FullName",nullable:false,value:""},
        {type:"text",dataName:"roles",name:"editCustomerRoles",placeholder:"Roles",nullable:false,value:""},
        {type:"text",dataName:"gender",name:"editCustomerGender",placeholder:"Gender",nullable:false,value:""},
        {type:"date",dataName:"birthday",name:"editCustomerBirthday",placeholder:"Birthday",nullable:false,value:""},
        {type:"text",dataName:"avatar",name:"editCustomerAvatar",placeholder:"Avatar",nullable:true,value:""},
    ]

    const settingProductAddForm = [
        {type:"text",dataName:"id",name:"addProductId",placeholder:"Id",nullable:false,value:""},
        {type:"text",dataName:"categoryId",name:"addProductCategoryId",placeholder:"CategoryId",nullable:false,value:""},
        {type:"text",dataName:"sizeId",name:"addProductSizeId",placeholder:"SizeId",nullable:false,value:""},
        {type:"text",dataName:"colorId",name:"addProductColorId",placeholder:"ColorId",nullable:false,value:""},
        {type:"text",dataName:"sku",name:"addProductSku",placeholder:"Sku",nullable:false,value:""},
        {type:"text",dataName:"name",name:"addProductName",placeholder:"Name",nullable:false,value:""},
        {type:"text",dataName:"description",name:"addProductDescription",placeholder:"Description",nullable:false,value:""},
        {type:"text",dataName:"regularPrice",name:"addProductRegularPrice",placeholder:"RegularPrice",nullable:false,value:""},
        {type:"text",dataName:"finalPrice",name:"addProductFinalPrice",placeholder:"FinalPrice",nullable:false,value:""},
        {type:"text",dataName:"image",name:"addProductImage",placeholder:"Image",nullable:false,value:""},
        {type:"text",dataName:"materials",name:"addProductMaterials",placeholder:"Materials",nullable:false,value:""},
        {type:"text",dataName:"instruction",name:"addProductInstruction",placeholder:"Instruction",nullable:false,value:""},
    ]
    const settingQuantityAddForm = [
        {type:"text",dataName:"id",name:"addQuantityId",placeholder:"Id",nullable:false,value:""},
        {type:"text",dataName:"productId",name:"addQuantityProductId",placeholder:"ProductId",nullable:false,value:""},
        {type:"text",dataName:"sizeId",name:"addQuantitySizeId",placeholder:"SizeId",nullable:false,value:""},
        {type:"text",dataName:"colorId",name:"addQuantityColorId",placeholder:"ColorId",nullable:false,value:""},
        {type:"text",dataName:"value",name:"addQuantityValue",placeholder:"Value",nullable:false,value:""},
        {type:"text",dataName:"mediaGallery",name:"addQuantityMediaGallery",placeholder:"MediaGallery",nullable:false,value:""},
    ]
    const settingSizeAddForm = [
        {type:"text",dataName:"id",name:"addSizeId",placeholder:"Id",nullable:false,value:""},
        {type:"text",dataName:"label",name:"addSizeLabel",placeholder:"Label",nullable:false,value:""},
    ]
    const settingColorAddForm = [
        {type:"text",dataName:"id",name:"addColorId",placeholder:"Id",nullable:false,value:""},
        {type:"text",dataName:"label",name:"addColorLabel",placeholder:"Label",nullable:false,value:""},
        {type:"text",dataName:"image",name:"addColorImage",placeholder:"Image",nullable:false,value:""},
    ]
    
    const handleAddAdmin = (e) => {
        e.preventDefault();
        const data = {
            id: e.target.editAdminId.value,
            username: e.target.editAdminUsername.value,
            password: e.target.editAdminPassword.value,
            email: e.target.editAdminEmail.value,
            phone: e.target.editAdminPhone.value,
            fullName: e.target.editAdminFullName.value,
            roles: e.target.editAdminRoles.value,
            birthday: handleDate.toISOString(e.target.editAdminBirthday.value),
            gender: e.target.editAdminGender.value,
            avatar: e.target.editAdminAvatar.value,
        }
        if(checkErrorValue(data)) addData.addAccount(name,data);
    }
    const handleAddCustomer = (e) => {
        e.preventDefault();
        const data = {
            id: e.target.editCustomerId.value,
            username: e.target.editCustomerUsername.value,
            password: e.target.editCustomerPassword.value,
            email: e.target.editCustomerEmail.value,
            phone: e.target.editCustomerPhone.value,
            fullName: e.target.editCustomerFullName.value,
            roles: e.target.editCustomerRoles.value,
            birthday: handleDate.toISOString(e.target.editCustomerBirthday.value),
            gender: e.target.editCustomerGender.value,
            avatar: e.target.editCustomerAvatar.value,
        }
        if(checkErrorValue(data)) addData.addAccount(name,data);
    }
    const handleAddProduct = (e) => {
        e.preventDefault();
        const data = {
            id:e.target.addProductId.value,
            categoryId:e.target.addProductCategoryId.value,
            sizeId:e.target.addProductSizeId.value===""?"":e.target.addProductSizeId.value.endsWith(",")?e.target.addProductSizeId.value:e.target.addProductSizeId.value+",",
            colorId:e.target.addProductColorId.value===""?"":e.target.addProductColorId.value.endsWith(",")?e.target.addProductColorId.value:e.target.addProductColorId.value+",",
            sku:e.target.addProductSku.value,
            name:e.target.addProductName.value,
            description:e.target.addProductDescription.value,
            regularPrice:e.target.addProductRegularPrice.value,
            finalPrice:e.target.addProductFinalPrice.value,
            image:e.target.addProductImage.value,
            materials:e.target.addProductMaterials.value,
            instruction:e.target.addProductInstruction.value,
            createdAt:new Date().toISOString(),
        }
        if(checkErrorValue(data)) addData.addData(name,data);
    }

    const handleAddQuantity = (e) => {
        e.preventDefault();
        const data = {
            id:e.target.addQuantityId.value,
            sku:e.target.addQuantityProductId.value+"-"+e.target.addQuantitySizeId.value+"-"+e.target.addQuantityColorId.value,
            productId:e.target.addQuantityProductId.value,
            sizeId:e.target.addQuantitySizeId.value,
            colorId:e.target.addQuantityColorId.value,
            value:e.target.addQuantityValue.value,
            mediaGallery:e.target.addQuantityMediaGallery.value
        }
        if(checkErrorValue(data)) addData.addData(name,data);
    }

    const handleAddSize = (e) => {
        e.preventDefault();
        const data = {
            id:e.target.addSizeId.value,
            label:e.target.addSizeLabel.value
        }
        if(checkErrorValue(data)) addData.addData(name,data);
    }

    const handleAddColor = (e) => {
        e.preventDefault();
        const data = {
            id:e.target.addColorId.value,
            label:e.target.addColorLabel.value,
            image:e.target.addColorImage.value
        }
        if(checkErrorValue(data)) addData.addData(name,data);
    }

    const checkErrorValue = (data) => {
        let check = 1;
        settingAddForm.forEach((item)=>{
            if(!item.nullable&&data[item.dataName]!==undefined&&data[item.dataName]===""){
                eventError(item.name,item.name+"Error",item.placeholder+" is required");
                check = 0;
            }
        })
        return check;
    }

    const handleChangePath = ()=>{
        document.querySelectorAll('.inputDeleteForm > input').forEach(item => {item.classList.remove("error-class");item.value="";});
        document.querySelectorAll('.inputDeleteForm > p').forEach(item => {item.style.display = "none";});
    }

    useEffect(()=>{
        handleChangePath();
        if(name==="admins") setSettingAddForm(settingAdminEditForm);
        if(name==="customers") setSettingAddForm(settingCustomerEditForm);
        if(name==="products") setSettingAddForm(settingProductAddForm);
        if(name==="quantities") setSettingAddForm(settingQuantityAddForm);
        if(name==="sizes") setSettingAddForm(settingSizeAddForm);
        if(name==="colors") setSettingAddForm(settingColorAddForm);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[name])

    const eventError = (name,nameError,message)=>{
        document.querySelector("input[name='"+name+"']").classList.add("error-class");   
        document.querySelector("input[name='"+name+"']").addEventListener("input",()=>{removeEventError(name,nameError)});
        document.querySelector("."+nameError).innerHTML = message;
        document.querySelector("."+nameError).style.display = "block";
    }
    
    const removeEventError = (name,nameError)=>{
        if(document.querySelector("input[name='"+name+"']")!==null)
        document.querySelector("input[name='"+name+"']").classList.remove("error-class"); 
        document.querySelector("."+nameError).style.display = "none";
    }

    const handleSubmit = (e)=>{
        e.preventDefault();
        if(name==="admins") handleAddAdmin(e);  
        else if(name==="customers") handleAddCustomer(e);
        else if(name==="products") handleAddProduct(e);
        else if(name==="quantities") handleAddQuantity(e);
        else if(name==="sizes") handleAddSize(e);
        else if(name==="colors") handleAddColor(e);
    }
    
    return (
        <div>
            <form  onSubmit={handleSubmit} className="addForm"> 
                <h1> Add {name}</h1>
                <div className="inputAddForm">
                    {settingAddForm.map((item,index)=>{
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

export default Add