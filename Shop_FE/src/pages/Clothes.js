import React,{useEffect,useState} from "react";
import GetData from "../service/GetData";
import { useParams } from "react-router-dom";
import ClothesCard from "../components/ClothesCard";
import "../css/Clothes.css"

function Clothes() {
    let {id} = useParams()
    let [product,setProduct] = useState(null);
    const getData = new GetData();
    useEffect(()=>{
        ScrollToTop();
        console.log(id)
        getData.searchByCategory(id).then(data=>{setProduct(data.data); console.log(data)});
    },[id]);
    const ScrollToTop = () => {
        window.scrollTo(0, 0);
        return null;
    };
    return (
        <div className="clothes-container">

            <div className="clothes-items">
            {product!==null?product.map((item,index)=>{
                return(
                    <ClothesCard key={index} data={item}/>
                )}):""
            }
            </div>

       
        </div>
    )
}

export default Clothes;
