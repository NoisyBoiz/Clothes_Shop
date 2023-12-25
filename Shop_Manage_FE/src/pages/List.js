import React, { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import '../css/List.css'
import Load from '../components/Load.js'
import {HiOutlineCubeTransparent} from "react-icons/hi";
import Notification from "../components/Notification";
import Pagination from "../components/Pagination";
import DeleteData from "../service/DeleteData";
import GetData from "../service/GetData";
import TbAdmin from "../table/TbAdmin.js";
import TbCustomer from "../table/TbCustomer.js";
import TbProduct from "../table/TbProduct.js";
import TbQuantity from "../table/TbQuantity.js";
import TbColor from "../table/TbColor.js";
import TbSize from "../table/TbSize.js";
import {MdDeleteOutline} from "react-icons/md";
import {MdSelectAll,MdDeselect} from "react-icons/md";
function List(){
    let {name} = useParams();

    let allDataInit = useRef([]);
    let [allData,setAllData] = useState([]);
    let [page,setPage] = useState(1);
    let [select,setSelect] = useState([]);
    let [totalRs,setTotalRs] = useState(0);
    let [query,setQuery] = useState({
        id:null,
        isDeleted:false,
    });
    const limitRows = 10;
    const notification = new Notification();
    const getData = new GetData();

    const scrollTop = () =>{ window.scrollTo({top: 0}); }

    const handleGetData = (data)=>{
        if(data!==null&&data!==undefined){
            if(data.status==="OK"){
                if(data.data.length!==undefined&&data.data.length>1){
                    allDataInit.current = data.data;
                    setAllData(data.data.slice(0,limitRows));
                    setTotalRs(data.data.length);
                }
                else {
                    allDataInit.current = [data.data];
                    setAllData([data.data]);
                }
            }
            else{
                allDataInit.current = null;
                setAllData(null);
            }
        }
    }

    const handleSelect = (id) => {
        if(select.includes(id)) setSelect(select.filter(item=>item!==id));
        else setSelect([...select,id]);
        console.log(select);
    }
    const handleSelectAll = () => {
        let arr = [];
        allData.forEach(item=>{
            arr.push(item.id)
        });
        setSelect(arr);
    }
    const handleRemoveSelectAll = () => {
        setSelect([]);
    }
 
    const handleDeleteDataSelect = ()=>{
        if(select.length===0) return;
        notification.comfirm({
            title:"Delete Select",
            message:"Are you sure you want to delete this select?",
            handleAccept:()=>{
                select.forEach(id=>{
                    handleDeleteData(name,id)
                });
                setSelect([]);
            },
            handleCancel:()=>{console.log("cancel")},
            titleAccept:"Yes Delete It",
            titleCancel:"No"
        })
    }

    const handleDeleteData = (name,id) =>{
        DeleteData(name,id).then(rs=>{
            if(rs){
                allDataInit.current = allDataInit.current.filter(item=>item.id!==id);
                if(allDataInit.current.length===0) {
                    allDataInit.current = null;
                    setAllData(null);
                }
                else{
                    if(page>0&&allDataInit.current.slice((page-1)*limitRows,page*limitRows).length===0) {
                        setAllData(allDataInit.current.slice((page-2)*limitRows,(page-1)*limitRows));
                        setPage(page-1);
                    }
                    else{
                        setAllData(allDataInit.current.slice((page-1)*limitRows,page*limitRows));
                    }
                }
                setTotalRs(allDataInit.current.length);
                return true
            }
            return false
        })
    }
   
    const handleTableList = () => {
        if(name==="admins") return <TbAdmin data={allData} select={select} handleSelect={handleSelect} handleDeleteData={handleDeleteData}/>
        if(name==="customers") return <TbCustomer data={allData} select={select} handleSelect={handleSelect} handleDeleteData={handleDeleteData}/>
        if(name==="products") return <TbProduct data={allData} select={select} handleSelect={handleSelect} handleDeleteData={handleDeleteData}/>
        if(name==="quantities") return <TbQuantity data={allData} select={select} handleSelect={handleSelect} handleDeleteData={handleDeleteData}/>
        if(name==="colors") return <TbColor data={allData} select={select} handleSelect={handleSelect} handleDeleteData={handleDeleteData}/>
        if(name==="sizes") return <TbSize data={allData} select={select} handleSelect={handleSelect} handleDeleteData={handleDeleteData}/>
    }

    const listById = (idSearch)=>{
        console.log(query.id)
        let rs = []
        allDataInit.current.forEach(item=>{if(String(item.id).startsWith(idSearch)) rs.push(item)})
        setTotalRs(rs.length);
        return rs;
    }

    useEffect(()=>{
        console.log(query)
        if(query.id === "") console.log("empty")
        if(allDataInit.current!==null&&allDataInit.current.length>0){
            if(query.id!==undefined){
                let rs = listById(query.id);
                if(rs.length===0) setAllData(null);
                else setAllData(rs.slice((page-1)*limitRows,page*limitRows));
            }
        }
    // eslint-disable-next-line react-hooks/exhaustive-deps 
    },[query])

    useEffect(()=>{
        setAllData([]);
        setPage(1);
        setSelect([]);
        allDataInit.current = []

        if(name==="products") getData.listAllProduct().then(data=>handleGetData(data))
        else getData.listAll(name).then(data=>handleGetData(data))

        if(document.querySelector(".list-action input[name='list-box-search']")!==null)
            document.querySelector(".list-action input[name='list-box-search']").value="";
        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[name]);

    useEffect(()=>{
        scrollTop();
        setAllData(allDataInit.current.slice((page-1)*limitRows,page*limitRows));
    // eslint-disable-next-line react-hooks/exhaustive-deps 
    },[page]);

    return (
        <div className="list-content">
            <div>
                {(allDataInit.current!==null&&allDataInit.current.length>0)&&
                    <>
                        <div className="list-action">
                            <div className="list-box-select"> 
                                {allData!==null&&select.length<allData.length?<button className="select-all" onClick={()=>handleSelectAll()}> <MdSelectAll/> </button>:<button className="remove-all-select" onClick={()=>handleRemoveSelectAll()}> <MdDeselect/> </button>}
                                {select.length>0&&<button className="delete-all-select" onClick={()=>{handleDeleteDataSelect()}}> <MdDeleteOutline/> </button>}
                            </div>
                            <input name="list-box-search" 
                                placeholder="Search by Id" 
                                defaultValue={query.id}
                                onChange={e=>{
                                    setQuery({...query,id:e.target.value});
                                    //setAllData([]);
                                    setPage(1);
                            }}/>
                            {/* <button onClick={()=>{setQuery({...query,isDeleted:!query.isDeleted})}}>test</button> */}
                        </div>
                    </>
                }

                {allData!==null&&allData.length>0?
                    <> 
                        <div className="list-table"> 
                            {handleTableList()}
                        </div>
                        <Pagination limitResult={limitRows} totalResult={totalRs} curPage={page} setCurPage={setPage}/>
                    </>
                    :(allData===null?
                        (query.id===""?
                            <div className="empty-data-list"> <HiOutlineCubeTransparent/> <h1> Nothing! </h1> <h3>Look like no data in here </h3> </div>
                            :
                            <div className="notfound-data-list"> <HiOutlineCubeTransparent/> <h1> Not Found! </h1> <h3> Nothing was found with {query.id} </h3> </div>
                        )
                        :<Load/>
                    )
                }
            </div>
        </div>
    );
}

export default List