const localStorage = window.localStorage;
function getToken(){
    const token = localStorage.getItem("token");
    return token;
}

function setUser(data){
    let setData = {
        phone: data.phone,
        email: data.email,
        fullName: data.fullName,
        roles: data.roles,
        gender: data.gender,
        birthday: data.birthday,
        avatar: data.avatar,
    }
    localStorage.setItem("data",JSON.stringify(setData));
    localStorage.setItem("token",data.token);
}

function getUser(){
    const data = JSON.parse(localStorage.getItem("data"));
    return data;
}

function clear(){
    localStorage.removeItem("data");
    localStorage.removeItem("token");
}


class HandleStorage{
    constructor(){
        this.getToken = getToken;
        this.setUser = (data) => setUser(data);
        this.getUser = getUser;
        this.clear = clear;
    }
}

export default HandleStorage;