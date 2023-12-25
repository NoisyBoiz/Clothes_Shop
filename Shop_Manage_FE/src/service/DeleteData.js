import Notification from "../components/Notification";

const notification = new Notification()
function deleteData(name,id){
    return (
        fetch('http://localhost:8080/'+name+'/delete/'+id, {
            method: 'DELETE',
            headers: {
                "Access-Control-Allow-Headers" : "Content-Type",
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json',
                "Access-Control-Allow-Methods": "OPTIONS,POST,GET,PATCH"
            },
        })
        .then(response => response.json())
        .then(data => {
            if(data.status==="OK"){
                notification.success({
                    title:"Delete Success",
                    message:data.message
                })
                return true
            }
            else{
                notification.error({
                    title:data.status,
                    message:data.message
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

export default deleteData;