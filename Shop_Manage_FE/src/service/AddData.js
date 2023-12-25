import Notification from "../components/Notification";

const notification = new Notification()

function fetchData(path,data){
    return(
        fetch('http://localhost:8080/'+path, {
            method: 'POST',
            headers: {
                "Access-Control-Allow-Headers" : "Content-Type",
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json',
                "Access-Control-Allow-Methods": "OPTIONS,POST,GET,PATCH",
                
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => {
            if(data.status==="OK"){
                notification.success({
                    title: "Success",
                    message: data.message
                })
                return true
            }
            else{
                console.log(data)
                notification.error({
                  
                    title: data.status,
                    message: data.message
                })
                return false
            }
        })                                           
        .catch((error) => {
            notification.error({
                title: "Error",
                message: error,
            })
            return false
        })
    )
}
class addData{
    constructor(){
        this.addAccount = async (name,data) => {
            const path = name+"/register"
            return await fetchData(path,data)
        }
        this.addData = async (name,data) => {
            const path = name+"/add"
            return await fetchData(path,data)
        }
    }
}
export default addData;