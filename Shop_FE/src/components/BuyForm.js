import React, {useEffect, useRef, useState} from "react";
import "../css/BuyForm.css"
import { Link } from "react-router-dom";
function BuyForm({productQuantity,productColor,productSize,productImage,colorInit,sizeInit,imageInit,quantityInit,setShowBuyForm,cost}){

    let [colorSelected,setColorSelected] = useState(colorInit);
    let [sizeSelected,setSizeSelected] = useState(sizeInit);
    let [imageSelected,setImageSelected] = useState(imageInit);
    let [quantity,setQuantity] = useState(quantityInit);
    let [quantitySelected,setQuantitySelected] = useState(1);
    let [total,setTotal] = useState(0);

    const imagePrePath = "https://canifa.com/img/1000/1500/resize/"
    const colorPrePath = "https://media.canifa.com/attribute/swatch/"

    const convertPrice = (price)=>{
        let str = price.toString();
        let result = "";
        let count = 0;
        for(let i=str.length-1;i>=0;i--){
            count++;
            result = str[i]+result;
            if(count===3&&i!==0){
                result = "."+result;
                count=0;
            }
        }
        return result+"₫";
    }

    const getQuantity = (color,size) => {
        let colorId = color!==null?color.id:colorSelected.id;
        let sizeId = size!==null?size.id:sizeSelected.id;
        if(productQuantity!==null && sizeId != null && colorId != null){
            for(let i=0;i<productQuantity.length;i++){
                if(Number(productQuantity[i].colorId)===colorId && Number(productQuantity[i].sizeId)===sizeId){
                    setQuantity(productQuantity[i].value);
                    break;
                }
            }
        }
    }
    const setValue = () => {
        let input = document.querySelector(".buy-form-left input");
        let value = 1;
        if(quantitySelected!==null)
            value = quantitySelected;
        setTotal(convertPrice(value*cost));
        console.log(value*cost,cost,value)
    }
    useEffect(()=>{
        setValue();
    },[quantitySelected])

    return(
        <div className="buy-form-container">
            <div className="buy-form-content">
                <button onClick={()=>{setShowBuyForm(false)}} className="buy-form-button-close"> Close </button>
                <div className="buy-form-left">
                    <p> Mau sac: {colorSelected!==null?colorSelected.label:""} </p>
                    <ul className="product-detail-select-color">  
                        {productColor!==null?productColor.map((item,index)=>{
                            return(
                                <li key={index} onClick={()=>{setColorSelected(item);setImageSelected(productImage[item.id][0]);getQuantity(item,null);setValue()}}>
                                    <img className={colorSelected!==null&&colorSelected.id===item.id?"product-detail-color-focus":"product-detail-color-not-focus"} src={colorPrePath+item.image}/>
                                </li>
                            )
                        }):""}
                    </ul>
                    <p> Kich thuoc: </p>
                    <ul className="product-detail-select-size">  
                        {productSize!==null?productSize.map((item,index)=>{
                            return(
                                <li key={index} onClick={()=>{setSizeSelected(item);getQuantity(null,item);setValue()}} className={sizeSelected!==null&&sizeSelected.id===item.id?"product-detail-size-focus":"product-detail-size-not-focus"}>
                                    <div> {item.label} </div>
                                </li>
                            )
                            }):""
                        }
                    </ul>
                    <p> Con lai: {quantity!==null?quantity:""} </p>
                    <p className="buy-form-quantity"> So luong: {quantitySelected} 
                        <button className={quantitySelected>1?"buy-form-quantity-button-focus":"buy-form-quantity-button-not-focus"} onClick={()=>{setQuantitySelected(quantitySelected>1?quantitySelected-1:1)}}> - </button> 
                        <button className={quantitySelected<quantity?"buy-form-quantity-button-focus":"buy-form-quantity-button-not-focus"} onClick={()=>{setQuantitySelected(quantitySelected<quantity?quantitySelected+1:quantitySelected)}}> + </button>  
                    </p>
                    <p> Tong so tien: {total} </p>
                    <button className="buy-form-button"> Mua ngay </button>
                </div>
                <div className="buy-form-right">
                    <img src={imagePrePath+imageSelected}/>
                </div>
            </div>
        </div>
    )
}

export default BuyForm;