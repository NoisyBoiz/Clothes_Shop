async function Authenticate(data){
    return (
        fetch('http://localhost:8080/authenticate', {
            method: 'POST',
            // mode: "no-cors",
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
            return data
        })                                           
        .catch((error) => {
            return error
        })
    )
}

export default Authenticate;