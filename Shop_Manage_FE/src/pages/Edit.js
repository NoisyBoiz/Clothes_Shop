import React,{useState,useEffect,Fragment} from "react";
import { useParams } from "react-router-dom";
import Notification from "../components/Notification";
import EditData from "../service/EditData";
import GetData from "../service/GetData";
import "../css/EditForm.css"
import HandleDate from "../Func/HandleDate";

function Edit(){
    let { name } = useParams();
    let { id } = useParams();
    
    let [dataInit,setDataInit] = useState(null);
    let [settingEditForm, setSettingEditForm] = useState([]);

    const getData = new GetData();
    const notification = new Notification();
    const handleDate = new HandleDate();

    useEffect(()=>{
        if(id!==null&&id!==undefined){
            getData.searchById(name,id).then( rs=> {if(rs!==null&&rs!==undefined) setDataInit(rs.data)});
        }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    },[id])

    const settingAdminEditForm = [
        {type:"text",dataName:"id",name:"editAdminId",placeholder:"Id",nullable:false,value:""},
        // {type:"text",dataName:"username",name:"editAdminUsername",placeholder:"Username",nullable:false,value:""},
        // {type:"text",dataName:"password",name:"editAdminPassword",placeholder:"Password",nullable:false,value:""},
        {type:"text",dataName:"phone",name:"editAdminPhone",placeholder:"Phone",nullable:false,value:""},
        {type:"text",dataName:"email",name:"editAdminEmail",placeholder:"Email",nullable:false,value:""},
        {type:"text",dataName:"fullName",name:"editAdminFullName",placeholder:"FullName",nullable:false,value:""},
        // {type:"text",dataName:"roles",name:"editAdminRoles",placeholder:"Roles",nullable:false,value:""},
        {type:"text",dataName:"gender",name:"editAdminGender",placeholder:"Gender",nullable:false,value:""},
        {type:"text",dataName:"birthday",name:"editAdminBirthday",placeholder:"Birthday",nullable:true,value:""},
        {type:"text",dataName:"avatar",name:"editAdminAvatar",placeholder:"Avatar",nullable:true,value:""},
        // {type:"text",dataName:"deleted",name:"editAdminDeleted",placeholder:"Deleted",nullable:false,value:""},
        // {type:"text",dataName:"createdAt",name:"editAdminCreatedAt",placeholder:"CreatedAt",nullable:false,value:""},
        // {type:"text",dataName:"deletedAt",name:"editAdminDeletedAt",placeholder:"DeletedAt",nullable:true,value:""},
        // {type:"text",dataName:"otp",name:"editAdminOtp",placeholder:"Otp",nullable:true,value:""},
        // {type:"text",dataName:"otpFailAttempts",name:"editAdminOtpFailAttempts",placeholder:"OtpFailAttempts",nullable:false,value:""},
        // {type:"text",dataName:"otpExp",name:"editAdminOtpExp",placeholder:"OtpExp",nullable:true,value:""},
        // {type:"text",dataName:"status",name:"editAdminStatus",placeholder:"Status",nullable:false,value:""},
    ]

    const settingCustomerEditForm = [
        {type:"text",dataName:"id",name:"editCustomerId",placeholder:"Id",nullable:false,value:""},
        // {type:"text",dataName:"username",name:"editCustomerUsername",placeholder:"Username",nullable:false,value:""},
        // {type:"text",dataName:"password",name:"editCustomerPassword",placeholder:"Password",nullable:false,value:""},
        {type:"text",dataName:"phone",name:"editCustomerPhone",placeholder:"Phone",nullable:false,value:""},
        {type:"text",dataName:"email",name:"editCustomerEmail",placeholder:"Email",nullable:false,value:""},
        {type:"text",dataName:"fullName",name:"editCustomerFullName",placeholder:"FullName",nullable:false,value:""},
        // {type:"text",dataName:"roles",name:"editCustomerRoles",placeholder:"Roles",nullable:false,value:""},
        {type:"text",dataName:"gender",name:"editCustomerGender",placeholder:"Gender",nullable:false,value:""},
        {type:"text",dataName:"birthday",name:"editCustomerBirthday",placeholder:"Birthday",nullable:false,value:""},
        {type:"text",dataName:"avatar",name:"editCustomerAvatar",placeholder:"Avatar",nullable:true,value:""},
        // {type:"text",dataName:"deleted",name:"editCustomerDeleted",placeholder:"Deleted",nullable:false,value:""},
        // {type:"text",dataName:"createdAt",name:"editCustomerCreatedAt",placeholder:"CreatedAt",nullable:false,value:""},
        // {type:"text",dataName:"deletedAt",name:"editCustomerDeletedAt",placeholder:"DeletedAt",nullable:true,value:""},
        // {type:"text",dataName:"otp",name:"editCustomerOtp",placeholder:"Otp",nullable:true,value:""},
        // {type:"text",dataName:"otpFailAttempts",name:"editCustomerOtpFailAttempts",placeholder:"OtpFailAttempts",nullable:false,value:""},
        // {type:"text",dataName:"otpExp",name:"editCustomerOtpExp",placeholder:"OtpExp",nullable:true,value:""},
        // {type:"text",dataName:"status",name:"editCustomerStatus",placeholder:"Status",nullable:false,value:""},
    ]

    const settingProductEditForm = [
        {type:"text",dataName:"id",name:"editProductId",placeholder:"Id",nullable:false,value:""},
        {type:"text",dataName:"categoryId",name:"editProductCategoryId",placeholder:"CategoryId",nullable:false,value:""},
        {type:"text",dataName:"sizeId",name:"editProductSizeId",placeholder:"SizeId",nullable:false,value:""},
        {type:"text",dataName:"colorId",name:"editProductColorId",placeholder:"ColorId",nullable:false,value:""},
        {type:"text",dataName:"sku",name:"editProductSku",placeholder:"Sku",nullable:false,value:""},
        {type:"text",dataName:"name",name:"editProductName",placeholder:"Name",nullable:false,value:""},
        {type:"text",dataName:"description",name:"editProductDescription",placeholder:"Description",nullable:false,value:""},
        {type:"text",dataName:"regularPrice",name:"editProductRegularPrice",placeholder:"RegularPrice",nullable:false,value:""},
        {type:"text",dataName:"finalPrice",name:"editProductFinalPrice",placeholder:"FinalPrice",nullable:false,value:""},
        {type:"text",dataName:"image",name:"editProductImage",placeholder:"Image",nullable:false,value:""},
        {type:"text",dataName:"materials",name:"editProductMaterials",placeholder:"Materials",nullable:false,value:""},
        {type:"text",dataName:"instruction",name:"editProductInstruction",placeholder:"Instruction",nullable:false,value:""},
        {type:"text",dataName:"deleted",name:"editProductDeleted",placeholder:"Deleted",nullable:false,value:""},
        {type:"text",dataName:"createdAt",name:"editProductCreatedAt",placeholder:"CreatedAt",nullable:false,value:""},
        {type:"text",dataName:"deleteAt",name:"editProductDeleteAt",placeholder:"DeleteAt",nullable:false,value:""},
    ]

    const settingQuantityEditForm = [
        {type:"text",dataName:"id",name:"editQuantityId",placeholder:"Id",nullable:false,value:""},
        {type:"text",dataName:"productId",name:"editQuantityProductId",placeholder:"ProductId",nullable:false,value:""},
        {type:"text",dataName:"sizeId",name:"editQuantitySizeId",placeholder:"SizeId",nullable:false,value:""},
        {type:"text",dataName:"colorId",name:"editQuantityColorId",placeholder:"ColorId",nullable:false,value:""},
        {type:"text",dataName:"value",name:"editQuantityValue",placeholder:"Value",nullable:false,value:""},
        {type:"text",dataName:"mediaGallery",name:"editQuantityMediaGallery",placeholder:"MediaGallery",nullable:false,value:""},
    ]

    const settingSizeEditForm = [
        {type:"text",dataName:"id",name:"editSizeId",placeholder:"Id",nullable:false,value:""},
        {type:"text",dataName:"label",name:"editSizeLabel",placeholder:"Label",nullable:false,value:""},
    ]

    const settingColorEditForm = [
        {type:"text",dataName:"id",name:"editColorId",placeholder:"Id",nullable:false,value:""},
        {type:"text",dataName:"label",name:"editColorLabel",placeholder:"Label",nullable:false,value:""},
        {type:"text",dataName:"image",name:"editColorImage",placeholder:"Image",nullable:false,value:""},
    ]


    const handleEditAdmin = (e) => {
        e.preventDefault();
        const data = {
            id: e.target.editAdminId.value,
            // username: e.target.editAdminUsername.value,
            // password: e.target.editAdminPassword.value,
            phone: e.target.editAdminPhone.value,
            email: e.target.editAdminEmail.value,
            fullName: e.target.editAdminFullName.value,
            // roles: e.target.editAdminRoles.value,
            gender: e.target.editAdminGender.value,
            birthday: e.target.editAdminBirthday.value,
            avatar: e.target.editAdminAvatar.value,
            // deleted: e.target.editAdminDeleted.value,
            // createdAt: e.target.editAdminCreatedAt.value,
            // updatedAt: handleDate.timeNow(),
            // deletedAt: e.target.editAdminDeletedAt.value,
            // otp: e.target.editAdminOtp.value,
            // otpFailAttempts: e.target.editAdminOtpFailAttempts.value,
            // otpExp: e.target.editAdminOtpExp.value,
            // status: e.target.editAdminStatus.value
        }
        console.log(data);
        if(checkErrorValue(data)){
            notification.warning({
                title:"Warning",
                message:"Are you sure you want to edit this admin?",
                handleAccept:()=>{EditData(name,data)},
                handleCancel:null,
                titleAccept:"Yes Edit It",
                titleCancel:"No"
            })
        }
    }

    const handleEditCustomer = (e) => {
        e.preventDefault();
        const data = {
            id: e.target.editCustomerId.value,
            // username: e.target.editCustomerUsername.value,
            // password: e.target.editCustomerPassword.value,
            phone: e.target.editCustomerPhone.value,
            email: e.target.editCustomerEmail.value,
            fullName: e.target.editCustomerFullName.value,
            // roles: e.target.editCustomerRoles.value,
            gender: e.target.editCustomerGender.value,
            birthday: e.target.editCustomerBirthday.value,
            avatar: e.target.editCustomerAvatar.value,
            // deleted: e.target.editCustomerDeleted.value,
            // createdAt: e.target.editCustomerCreatedAt.value,
            // updatedAt: handleDate.timeNow(),
            // deletedAt: e.target.editCustomerDeletedAt.value,
            // otp: e.target.editCustomerOtp.value,
            // otpFailAttempts: e.target.editCustomerOtpFailAttempts.value,
            // otpExp: e.target.editCustomerOtpExp.value,
            // status: e.target.editCustomerStatus.value
        }
        console.log(data);
        if(checkErrorValue(data)){
            notification.warning({
                title:"Warning",
                message:"Are you sure you want to edit this admin?",
                handleAccept:()=>{EditData(name,data)},
                handleCancel:null,
                titleAccept:"Yes Edit It",
                titleCancel:"No"
            })
        }
    }

    const handleEditProduct = (e) => {
        e.preventDefault();
        const data = {
            id: e.target.editProductId.value,
            categoryId: e.target.editProductCategoryId.value,
            sizeId: e.target.editProductSizeId.value,
            colorId: e.target.editProductColorId.value,
            sku: e.target.editProductSku.value,
            name: e.target.editProductName.value,
            description: e.target.editProductDescription.value,
            price: e.target.editProductRegularPrice.value,
            finalPrice: e.target.editProductFinalPrice.value,
            image: e.target.editProductImage.value,
            materials: e.target.editProductMaterials.value,
            instruction: e.target.editProductInstruction.value,
            deleted: e.target.editProductDeleted.value,
            createdAt: e.target.editProductCreatedAt.value,
            updatedAt: handleDate.timeNow(),
            deleteAt: e.target.editProductDeleteAt.value
        }
        if(checkErrorValue(data)){
            notification.warning({
                title:"Warning",
                message:"Are you sure you want to edit this product?",
                handleAccept:()=>{EditData(name,data)},
                handleCancel:null,
                titleAccept:"Yes Edit It",
                titleCancel:"No"
            })
        }
    }
    const handleEditQuantity = (e) => {
        e.preventDefault();
        const data = {
            id: e.target.editQuantityId.value,
            productId: e.target.editQuantityProductId.value,
            sizeId: e.target.editQuantitySizeId.value,
            colorId: e.target.editQuantityColorId.value,
            value: e.target.editQuantityValue.value,
            mediaGallery: e.target.editQuantityMediaGallery.value
        }
        if(checkErrorValue(data)){
            notification.warning({
                title:"Warning",
                message:"Are you sure you want to edit this quantity?",
                handleAccept:()=>{EditData(name,data)},
                handleCancel:null,
                titleAccept:"Yes Edit It",
                titleCancel:"No"
            })
        }
    }
    const handleEditColor = (e) => {
        e.preventDefault();
        const data = {
            id: e.target.editColorId.value,
            label: e.target.editColorLabel.value,
            image: e.target.editColorImage.value
        }
        if(checkErrorValue(data)){
            notification.warning({
                title:"Warning",
                message:"Are you sure you want to edit this color?",
                handleAccept:()=>{EditData(name,data)},
                handleCancel:null,
                titleAccept:"Yes Edit It",
                titleCancel:"No"
            })
        }
    }
    const handleEditSize = (e) => {
        e.preventDefault();
        const data = {
            id: e.target.editSizeId.value,
            label: e.target.editSizeLabel.value
        }
        if(checkErrorValue(data)){
            notification.warning({
                title:"Warning",
                message:"Are you sure you want to edit this size?",
                handleAccept:()=>{EditData(name,data)},
                handleCancel:null,
                titleAccept:"Yes Edit It",
                titleCancel:"No"
            })
        }
    }

    const checkErrorValue = (data) => {
        let check = 1;
        settingEditForm.forEach((item)=>{
            if(item.nullable===false&&data[item.dataName]!==undefined&&data[item.dataName]===""){
                eventError(item.name,item.name+"Error",item.placeholder+" is required");
                check = 0;
            }
        })
        return check;
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
        if(name==="admins") setSettingEditForm(settingAdminEditForm);
        if(name==="customers") setSettingEditForm(settingCustomerEditForm);
        if(name==="products") setSettingEditForm(settingProductEditForm);
        if(name==="quantities") setSettingEditForm(settingQuantityEditForm);
        if(name==="sizes") setSettingEditForm(settingSizeEditForm);
        if(name==="colors") setSettingEditForm(settingColorEditForm);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[name])

    const handleSubmit = (e)=>{
        e.preventDefault();
        if(name==="admins") handleEditAdmin(e);
        else if(name==="customers") handleEditCustomer(e);
        else if(name==="products") handleEditProduct(e);
        else if(name==="quantities") handleEditQuantity(e);
        else if(name==="sizes") handleEditSize(e);
        else if(name==="colors") handleEditColor(e);
    }

    return (
        <div>
            <form onSubmit={handleSubmit} className="editForm">
                <h1> Edit {name} </h1>
                <div class="inputEditForm">
                    {settingEditForm.map((item,index)=>{
                        return(
                            <Fragment key={index}>  
                                <label> {item.placeholder} </label>
                                <input type={item.type} name={item.name} placeholder={item.placeholder} defaultValue={dataInit!==null&&dataInit!==undefined?dataInit[item.dataName]:""}/>
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

export default Edit