import Home from '../pages/Home.js';
import Login from '../pages/Login.js';
import UserInfor from '../pages/UserInfor.js';
import Clothes from '../pages/Clothes.js';
import ClothesDetail from '../pages/ClothesDetail.js';

export const publicRoutes = [
    {path:"/", layout:"Layout", component: Home},
    {path:"/login", layout:null, component: Login},
    {path:"/userinfor", layout:"Layout", component: UserInfor},
    {path:"/clothes/:id", layout:"Layout", component: Clothes},
    {path:"/clothesDetail/:id", layout:"Layout", component: ClothesDetail},
];