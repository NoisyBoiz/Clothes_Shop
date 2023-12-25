import React, {useEffect, useRef, useState} from "react";
import "../css/ClothesCard.css"
import { Link } from "react-router-dom";
function ClothesCard({data}){
    const imagePrePath = "https://canifa.com/img/1000/1500/resize/"
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
    return(
        <Link to={"/clothesDetail/"+data.id} className="product-card">
            <img src={imagePrePath+data.image}/>
            <div className="product-card-description">
                <p className="product-name"> {data.name} </p>
                <p className="product-final-price"> {convertPrice(data.finalPrice)} </p>
                {data.regularPrice&&data.regularPrice!==data.finalPrice?<p className="product-regular-price"> <span> {convertPrice(data.regularPrice)} </span> <span> -{100 - Math.round(data.finalPrice/data.regularPrice*100)}% </span></p>:""}
            </div>
        </Link>
    )
}

export default ClothesCard;