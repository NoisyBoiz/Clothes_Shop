import React,{useEffect, useState} from "react";
import { Link } from "react-router-dom";
import {IoIosArrowUp, IoIosArrowDown} from "react-icons/io";
import {FaHome} from "react-icons/fa";
import {AiOutlineCodepen} from "react-icons/ai";
import GetData from "../service/GetData";
import "../css/Navbar.css";

function Navbar() {
  const getData = new GetData();
  let [categories,setCategories] = useState(null);

  useEffect(()=>{
    getData.listAll("categories").then((data)=>{
      HandleCategory(data.data);
    })
  },[0]);

  const HandleCategory = (data)=>{
    let dict = {};
    data.forEach((item)=>{
      let clothesType = item.name.split("-")[0].replaceAll("_"," ");
      let humanType = item.name.split("-")[1].replaceAll("_"," ");
      let path = "/clothes/"+item.id;
      if(!(humanType in dict)){dict[humanType] = [];}
      dict[humanType].push({"name":clothesType,"path":path});
    })
    let arr = [];
    for(let key in dict){
      arr.push({humanType:key,clothesType:dict[key]});
    }
    setCategories(arr);
  }

  let [openPages,setOpenPages] = useState(null);
  const handleOpenPages = (name)=>{
    if(openPages===name) setOpenPages(null);
    else setOpenPages(name);
  }

  return (
      <>
        <nav className="navbar">
          <h1> <Link to={`/`} className="direction-name"> <AiOutlineCodepen/> Clothes </Link> </h1>
          <ul>
            <li className="direction-container"> 
              <Link to={`/`} className="direction-name">  Home <FaHome/> </Link> 
            </li>
            {categories!==null?categories.map((item,index)=>{
                return(
                  <li className="direction-container" key={index}>
                    <div className="direction-name" onClick={()=>{handleOpenPages(item.humanType)}}> {item.humanType} {openPages===item.humanType?<IoIosArrowUp/>:<IoIosArrowDown/>} </div>
                    <ul style={{display:openPages===item.humanType?'block':'none'}} className="navbar-dropdown">
                      {item.clothesType.map((item,index)=>{
                          return(
                            <li key={index}> <Link to={item.path}> {item.name} </Link> </li>
                          )
                      })}
                    </ul>
                  </li>
                )
              }):""
            }
          </ul>
        </nav>
      </>
    )
}

export default Navbar;