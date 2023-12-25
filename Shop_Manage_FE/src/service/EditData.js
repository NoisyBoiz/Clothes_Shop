import Notification from "../components/Notification";

const notification = new Notification();
function editData(name,data){
    return(
        fetch('http://localhost:8080/'+name+'/edit', {
            method: 'PUT',
            headers: {
                "Access-Control-Allow-Headers" : "Content-Type",
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json',
                "Access-Control-Allow-Methods": "OPTIONS,POST,GET,PATCH"
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

export default editData;