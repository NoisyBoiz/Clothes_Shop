import React,{useState} from "react";
import { Link } from "react-router-dom";
import "../css/Navbar.css";
import {FaUserTie,FaClipboardUser,FaRegRectangleList,FaDeleteLeft} from "react-icons/fa6";
import {CgSize} from "react-icons/cg";
import {BiSolidColor} from "react-icons/bi";
import {BsBoxSeamFill} from "react-icons/bs";
import {IoIosArrowUp, IoIosArrowDown} from "react-icons/io";
import {MdFormatListBulletedAdd} from "react-icons/md";
import {FaEdit,FaHome} from "react-icons/fa";
import {RiNumbersFill} from "react-icons/ri";
import {AiOutlineCodepen} from "react-icons/ai";
function Navbar() {
  let [openAdmin,setOpenAdmin] = useState(false);
  let [openCustomer,setOpenCustomer] = useState(false);
  let [openProduct,setOpenProduct] = useState(false);
  let [openQuantity,setOpenQuantity] = useState(false);
  let [openColor,setOpenColor] = useState(false);
  let [openSize,setOpenSize] = useState(false);

  const openPages = [
    {name:"admin",value:openAdmin,set:setOpenAdmin},
    {name:"customer",value:openCustomer,set:setOpenCustomer},
    {name:"product",value:openProduct,set:setOpenProduct},
    {name:"quantity",value:openQuantity,set:setOpenQuantity},
    {name:"color",value:openColor,set:setOpenColor},
    {name:"size",value:openSize,set:setOpenSize}
  ];

  const handleOpenPage = (pageName) => {
    openPages.forEach((page)=>{
      page.name===pageName?page.set(!page.value):page.set(false);
    })
  }
  const settingCategory = [
    {name:"admin",state:openAdmin,stateSet:setOpenAdmin,icon:<FaUserTie/>,linkList:"/list/admins",linkAdd:"/add/admins",linkEdit:"/edit/admins",linkDelete:"/delete/admins"},
    {name:"customer",state:openCustomer,stateSet:setOpenCustomer,icon:<FaClipboardUser/>,linkList:"/list/customers",linkAdd:"/add/customers",linkEdit:"/edit/customers",linkDelete:"/delete/customers"},
    {name:"product",state:openProduct,stateSet:setOpenProduct,icon:<BsBoxSeamFill/>,linkList:"/list/products",linkAdd:"/add/products",linkEdit:"/edit/products",linkDelete:"/delete/products"},
    {name:"quantity",state:openQuantity,stateSet:setOpenQuantity,icon:<RiNumbersFill/>,linkList:"/list/quantities",linkAdd:"/add/quantities",linkEdit:"/edit/quantities",linkDelete:"/delete/quantities"},
    {name:"color",state:openColor,stateSet:setOpenColor,icon:<BiSolidColor/>,linkList:"/list/colors",linkAdd:"/add/colors",linkEdit:"/edit/colors",linkDelete:"/delete/colors"},
    {name:"size",state:openSize,stateSet:setOpenSize,icon:<CgSize/>,linkList:"/list/sizes",linkAdd:"/add/sizes",linkEdit:"/edit/sizes",linkDelete:"/delete/sizes"},

  ];
  return (
      <>
        <nav className="navbar">
          <h1> <Link to={`/`} className="direction-name"> <AiOutlineCodepen/> Clothes </Link> </h1>
          <ul>
            <li className="direction-container"> 
              <Link to={`/`} className="direction-name"> <FaHome/> Home </Link> 
            </li>
            {
              settingCategory.map((item,index)=>{
                return(
                  <li className="direction-container" key={index}>
                    <div className="direction-name" onClick={()=>{handleOpenPage(item.name)}}> {item.icon} {item.name} {item.state?<IoIosArrowUp/>:<IoIosArrowDown/>}</div>
                    <ul style={{display:item.state?'block':'none'}} className="navbar-dropdown">
                      <li> <Link to={item.linkList}> <FaRegRectangleList/> List </Link> </li>
                      <li> <Link to={item.linkAdd}> <MdFormatListBulletedAdd/> Add </Link> </li>
                      <li> <Link to={item.linkEdit}> <FaEdit/> Edit </Link> </li>
                      <li> <Link to={item.linkDelete}> <FaDeleteLeft/> Delete </Link> </li>
                    </ul>
                  </li>
                )
              })
            }
          </ul>
        </nav>
      </>
    )
}

export default Navbar;