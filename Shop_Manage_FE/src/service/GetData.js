
async function fetchData(url){
    try{
        const response = await 
            fetch('http://localhost:8080/'+url, {
                method: 'GET',
                headers: {
                    "Access-Control-Allow-Headers" : "Content-Type",
                    "Access-Control-Allow-Origin": "*",
                    'Content-Type': 'application/json',
                    "Access-Control-Allow-Methods": "OPTIONS,POST,GET,PATCH"
                }
            })    
            return await response.json()
    }
    catch(err){
        console.log(err);
        return false;
    }
}

class getData{
    constructor(){
        this.listAll = async (name) => {
            const url = name+"/list"
            return await fetchData(url);
        }
        this.searchById = async (name,id) => {
            const url = name+"/searchById?id="+id
            return await fetchData(url);
        }
        this.listAllProduct = async () => {
            const url = "products/searchName?q=&_page=-11&_limit=-1&_order=&_sort="
            return await fetchData(url);
        }
    }
}
export default getData