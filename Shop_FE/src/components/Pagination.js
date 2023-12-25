import { useState,useEffect } from 'react';
import {IoIosArrowBack, IoIosArrowForward} from 'react-icons/io'
import '../css/Pagination.css'

function getArrBox(curPage,totalPages,limitBox=8){
    let arrBox = [];
    if(totalPages>limitBox){
        if(curPage<=totalPages-(limitBox-3)){
            let x=0;
            if(curPage>=2) x=-1;
            for(let i=1;i<=limitBox;i++){
                if(i===1&&curPage>2){arrBox.push({sign:"...",index:curPage+x-1});}
                else if(i===limitBox) {arrBox.push({sign:"...",index:curPage+x});}
                else{arrBox.push({sign:curPage+x,index:curPage+x});x++;}
            }
        }
        else{
            let x=2;
            for(let i=1;i<=limitBox;i++){
                if(i===1) {arrBox.push({sign:"...",index:x+totalPages-limitBox-1});}
                else{arrBox.push({sign:x+totalPages-limitBox,index:x+totalPages-limitBox});x++;}
            }
        }
    }
    else{
        for(let i=1;i<=totalPages;i++){
            arrBox.push({sign:i,index:i});
        }
    }
    return arrBox;
}

function Pagination({limitResult,totalResult,curPage,setCurPage}){
    const limitBox = 10
    const totalPages = Math.ceil(totalResult/limitResult)
    let [arrBoxPage,setArrBoxPage] = useState(getArrBox(curPage,totalPages,limitBox))
    let [inputPage,setInputPage] = useState(curPage)

    useEffect(()=>{
        setArrBoxPage(getArrBox(curPage,totalPages,limitBox))
        setInputPage(curPage)
    // eslint-disable-next-line react-hooks/exhaustive-deps 
    },[curPage])

    return(
        <div className="pagination"> 
            <button className = {`prePage ${curPage>1?"controlPageActive":"controlPageNonActive"}`} onClick={()=>{curPage>1&&setCurPage(curPage-1)}}> <IoIosArrowBack/></button>
                <div className="containerBoxPage">
                    {arrBoxPage.map((item,index)=>{
                        return <button key={index} className={item.index===curPage?"boxPageActive":"boxPageNonActive"} onClick={()=>{setCurPage(item.index)}}>{item.sign}</button>
                    })}
                </div>
            <button className = {`nextPage ${curPage<totalPages?"controlPageActive":"controlPageNonActive"}`} onClick={()=>{curPage<totalPages&&setCurPage(curPage+1)}}> <IoIosArrowForward /> </button>
            <div className="inputBoxPage">
                <input 
                    type = "text" 
                    placeholder={inputPage}
                    onChange={(e)=>{setInputPage(e.target.value)}} 
                    onKeyDown={(e)=>{
                        if(e.key==="Enter"){
                            e.target.value = "";
                            if(Number(inputPage)>0&&Number(inputPage)<=totalPages) 
                                setCurPage(Number(inputPage))
                            else setInputPage("")
                        }
                    }}
                />
                <p> / {totalPages} </p>
            </div>
        </div>
    )
}




export default Pagination