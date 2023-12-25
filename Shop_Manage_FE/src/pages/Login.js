import React,{Fragment, useEffect, useState} from "react";
import Authenticate from "../service/Authenticate";
import { Link } from "react-router-dom";
import HandleStorage from "../Func/HandleStorage";
import "../css/LoginForm.css";
import {AiOutlineCodepen} from "react-icons/ai";
function Login(){

    const handleStorage = new HandleStorage();

    const settingLoginForm = [
        {type:"text",dataName:"username",name:"loginUsername",placeholder:"Username",nullable:false,value:""},
        {type:"text",dataName:"password",name:"loginPassword",placeholder:"Password",nullable:false,value:""},
    ]
    const settingSignupForm = [
        {type:"text",dataName:"username",name:"signupUsername",placeholder:"Username",nullable:false,value:""},
        {type:"text",dataName:"password",name:"signupPassword",placeholder:"Password",nullable:false,value:""},
        {type:"text",dataName:"comfirmPassword",name:"signupComfirmPassword",placeholder:"Comfirm Password",nullable:false,value:""},
        {type:"text",dataName:"fullname",name:"signupFullname",placeholder:"Full Name",nullable:false,value:""},
        {type:"text",dataName:"email",name:"signupEmail",placeholder:"Email",nullable:false,value:""},
        {type:"text",dataName:"phone",name:"signupPhone",placeholder:"Phone",nullable:false,value:""},
        {type:"text",dataName:"gender",name:"signupGender",placeholder:"Gender",nullable:false,value:""},
        {type:"date",dataName:"birthday",name:"signupBirthday",placeholder:"Birthday",nullable:false,value:""},
        {type:"text",dataName:"avatar",name:"signupAvatar",placeholder:"Avatar",nullable:true,value:""},
    ]
    let [settingForm,setSettingForm] = useState(settingLoginForm);
    let [isLogin,setIsLogin] = useState(true);
    const handleSubmit = (e)=>{
        e.preventDefault();
        if(isLogin){
            handleLogin(e);
        }
        else{
            handleSignup(e);
        }
    }
    const handleLogin = (e)=>{
        const data = {
            username:e.target.loginUsername.value,
            password:e.target.loginPassword.value
        }
        if(checkErrorValue(data))
        Authenticate(data).then(data=>checkResponseLogin(data));
    }

    const checkResponseLogin = (data)=>{
        console.log(data)
        if(data.status==="OK"){
            handleStorage.setUser(data.data);
            document.getElementById("back-to-home").click();
        }
        else if(data.status==="BAD_REQUEST"){
            eventError(null,"common-error","Tài khoản chưa được kích hoạt");
        }
        else{
            eventError(null,"common-error","Tài khoản hoặc mật khẩu không chính xác");
        }
    }

    const handleSignup = (e)=>{
        const data = {
            username:e.target.signupUsername.value.replace(/ */g,""),
            password:e.target.signupPassword.value,
            comfirmPassword:e.target.signupComfirmPassword.value,
            fullname:e.target.signupFullname.value,
            email:e.target.signupEmail.value,
            phone:e.target.signupPhone.value,
            gender:e.target.signupGender.value,
            birthday:e.target.signupBirthday.value,
            avatar:e.target.signupAvatar.value,
        }
        if(data.password!==data.comfirmPassword){
            eventError("signupComfirmPassword","signupComfirmPasswordError","Comfirm Password is not match");
        }
        if(checkErrorValue(data))
        console.log(data)
        // Authenticate(data).then(data=>checkResponse(data));
    }

    
    const eventError = (name,nameError,message)=>{
        if(name!==null){
            document.querySelector("input[name='"+name+"']").classList.add("error-class");   
            document.querySelector("input[name='"+name+"']").addEventListener("input",()=>{removeEventError(name,nameError)});
        }
        document.querySelector("."+nameError).innerHTML = message;
        document.querySelector("."+nameError).style.display = "block";
    }
    
    const removeEventError = (name,nameError)=>{
        if(name!==null){
            if(document.querySelector("input[name='"+name+"']")!==null)
            document.querySelector("input[name='"+name+"']").classList.remove("error-class"); 
        }
        if(document.querySelector("."+nameError)!==null)
            document.querySelector("."+nameError).style.display = "none";
    }

    const checkErrorValue = (data) => {
        let check = 1;
        settingForm.forEach((item)=>{
            if(!item.nullable&&data[item.dataName]!==undefined&&data[item.dataName]===""){
                eventError(item.name,item.name+"Error",item.placeholder+" is required");
                check = 0;
            }
        })
        return check;
    }

    const handleChangePath = ()=>{
        document.querySelectorAll('.login-form > form > input').forEach(item => {item.classList.remove("error-class");item.value="";});
        document.querySelectorAll('.login-form > form > p').forEach(item => {item.style.display = "none";});
    }
   
    const test = () => {
        if(document.querySelector("input[name=username]")!==null){
            document.querySelector("input[name=username]").style.affter = "red"
        }
    }
    const changeState = () => {
        handleChangePath()
        if(isLogin){
            setIsLogin(false)
            setSettingForm(settingSignupForm)
        }
        else{
            setIsLogin(true)
            setSettingForm(settingLoginForm)
        }
    }
    return(
        <div className="login-page-container">
            <Link className="back-to-home" to={'/'}> Back to Home </Link>
            <div className="login-form" onClick={()=>{removeEventError(null,"common-error")}}>
                <AiOutlineCodepen/>
                <h1> {isLogin?"Login":"Sign up"} </h1>
                <form onSubmit={handleSubmit} >
                    {settingForm.map((item,index)=>{
                        return(
                            <Fragment key={index}>
                                <input type={item.type} name={item.name} placeholder={item.placeholder}/>
                                <p className={item.name+"Error"}></p>
                            </Fragment>
                        )
                    })}
                    <p className="common-error"> </p>
                    <input type="submit" value={isLogin?"Login":"Sign Up"}/>
                </form>
                {/* <div> <Link to={'/forgotpassword'}> Forgot Password? </Link> </div> */}
                <div className="button-login-form-change-state" onClick={()=>{changeState()}}> {isLogin?"Don't have an account yet? Sign Up":"Already registered user? Log in"}  </div>
            </div>
        </div>
    )
}

export default Login;