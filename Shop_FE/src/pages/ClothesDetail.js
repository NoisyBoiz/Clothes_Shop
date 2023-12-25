import React, {useEffect, useRef, useState} from "react";
import "../css/ClothesDetail.css"
import { useParams } from "react-router-dom";
import GetData from "../service/GetData";
import {IoIosArrowUp, IoIosArrowDown} from "react-icons/io";
import BuyForm from "../components/BuyForm.js";
function ClothesDetail({data}){
    let {id} = useParams()
    const getData = new GetData();
    let [productDetail,setProductDetail] = useState(null);
    let [productQuantity,setProductQuantity] = useState(null);
    let [productColor,setProductColor] = useState([]);
    let [productSize,setProductSize] = useState([]);
    let [productImage,setProductImage] = useState(null);

    let [colorSelected,setColorSelected] = useState(null);
    let [sizeSelected,setSizeSelected] = useState(null);
    let [imageSelected,setImageSelected] = useState(null);

    let [showDescription,setShowDescription] = useState(false);
    let [showInstruction,setShowInstruction] = useState(false);
    let [showMeterial,setShowMeterial] = useState(false);
    let [quantity,setQuantity] = useState(null);
    let [showBuyForm,setShowBuyForm] = useState(false);

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
    useEffect(()=>{
        getData.getProductQuantityById(id).then(data=>{
            setProductQuantity(data.data);
            console.log(data.data);
            getProductImage(data.data);
        });
        getData.getProductDetailById(id).then(data=>{
            setProductDetail(data.data); 
            setProductSize([]);
            getProductColorSize(data.data)
            console.log(data.data);
        });
    },[id])

    const getProductColorSize = (data) => {
        data.colorId.split(",").forEach((item)=>{ 
            if(item!=="") getData.getProductColorById(item).then(res=>{
                setProductColor(productColor=>[...productColor,res.data]);
            });
        });
        data.sizeId.split(",").forEach((item)=>{ 
            if(item!=="") getData.getProductSizeById(item).then(res=>{
                setProductSize(productSize=>[...productSize,res.data]);
            });
        });
    }
    const getProductImage = (data) => {
        // if(data===undefined) return
        let rs = {};
        data.forEach((item)=>{
            rs[item.colorId] = [];
            item.mediaGallery.split(",").forEach((i)=>{ 
                if(i!=="") rs[item.colorId].push(i);
            });
        });
        setProductImage(rs);
        setImageSelected(rs[data[0].colorId][0]);
    }

    useEffect(()=>{
        if(productColor!==null && productColor.length>0){
            for(let i=0;i<productColor.length;i++){
                for(let j=i+1;j<productColor.length;j++){
                    if(productColor[i].id===productColor[j].id){
                        productColor.splice(j,1);
                    }
                }
            }
            setColorSelected(productColor[0]);
        }
    },[productColor])
    useEffect(()=>{
        if(productSize!==null && productSize.length>0){
            for(let i=0;i<productSize.length;i++){
                for(let j=i+1;j<productSize.length;j++){
                    if(productSize[i].id===productSize[j].id){
                        productSize.splice(j,1);
                    }
                }
            }
            setSizeSelected(productSize[0]);
        }
    },[productSize])
    useEffect(()=>{
        console.log(productImage);
    },[productImage])

    useEffect(()=>{
        let colorId = colorSelected!==null?colorSelected.id:null;
        let sizeId = sizeSelected!==null?sizeSelected.id:null;
        if(productQuantity!==null && sizeId != null && colorId != null){
            for(let i=0;i<productQuantity.length;i++){
                if(Number(productQuantity[i].colorId)===colorId && Number(productQuantity[i].sizeId)===sizeId){
                    setQuantity(productQuantity[i].value);
                    break;
                }
            }
        }
    },[colorSelected,sizeSelected,productQuantity])

    return(
        <>
        <div className="product-detail-container">
            {productDetail!==null?<div className="product-detail-left"> 
                <p className="product-detail-name"> {productDetail.name} </p>
                <p> Ma san pham: {productDetail.sku} </p>
                <p className="product-final-price"> {convertPrice(productDetail.finalPrice)} </p>
                {productDetail.regularPrice&&productDetail.regularPrice!==productDetail.finalPrice?<p className="product-regular-price"> <span> {convertPrice(productDetail.regularPrice)} </span> <span> -{100 - Math.round(productDetail.finalPrice/productDetail.regularPrice*100)}% </span></p>:""}
                <p> Mau sac: {colorSelected!==null?colorSelected.label:""} </p>
                <ul className="product-detail-select-color">  
                    {productColor!==null?productColor.map((item,index)=>{
                        return(
                            <li key={index} onClick={()=>{setColorSelected(item);setImageSelected(productImage[item.id][0])}}>
                                <img className={colorSelected!==null&&colorSelected.id===item.id?"product-detail-color-focus":"product-detail-color-not-focus"} src={colorPrePath+item.image}/>
                            </li>
                        )
                    }):""}
                </ul>
                <p> Kich thuoc: </p>
                <ul className="product-detail-select-size">  
                    {productSize!==null?productSize.map((item,index)=>{
                         return(
                            <li key={index} onClick={()=>{setSizeSelected(item)}} className={sizeSelected!==null&&sizeSelected.id===item.id?"product-detail-size-focus":"product-detail-size-not-focus"}>
                                <div> {item.label} </div>
                            </li>
                        )
                        }):""
                    }
                </ul>
                <p className="product-detail-description" onClick={()=>{setShowDescription(!showDescription)}}> <span> Mo ta </span> <span> {showDescription?<IoIosArrowUp/>:<IoIosArrowDown/>} </span> </p>
                {showDescription?<p className="product-detail-description-text">{productDetail.description}</p>:""}
                <p className="product-detail-material" onClick={()=>{setShowMeterial(!showMeterial)}}> <span> Chat lieu </span> <span> {showMeterial?<IoIosArrowUp/>:<IoIosArrowDown/>} </span></p>
                {showMeterial?<p className="product-detail-material-text">{productDetail.materials}</p>:""}
                <p className="product-detail-instruction" onClick={()=>{setShowInstruction(!showInstruction)}}> <span> Huong dan su dung </span> <span> {showInstruction?<IoIosArrowUp/>:<IoIosArrowDown/>} </span></p>
                {showInstruction?<p className="product-detail-instruction-text">{productDetail.instruction}</p>:""}

                <p> So luong: {quantity!==null?quantity:""} </p>
                <button className="button-buy-product" onClick={()=>{setShowBuyForm(true)}}> Them vao gio hang </button>
            </div>:""}


            <div className="product-detail-right"> 
                <div className="product-detail-main-image"> <img src={imagePrePath+imageSelected}/> </div>
                <ul className="product-detail-select-image">
                {productImage!==null&&colorSelected!==null?productImage[colorSelected.id].map((item,index)=>{
                    return(
                        <li onClick={()=>{setImageSelected(item)}} key={index}>
                            <img src={imagePrePath+item}/>
                        </li>
                    )
                }):""
                }
                </ul>
            </div>
        </div>
        {showBuyForm?<BuyForm productQuantity={productQuantity} productSize={productSize} productColor={productColor} productImage={productImage} sizeInit={sizeSelected} colorInit={colorSelected} imageInit={imageSelected} quantityInit={quantity} setShowBuyForm={setShowBuyForm} cost={Number(productDetail.finalPrice)} />:""}
        </>
    )
}

export default ClothesDetail;