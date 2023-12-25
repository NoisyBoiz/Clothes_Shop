import Home from '../pages/Home.js';
import List from '../pages/List.js';
import Add from '../pages/Add.js';
import Edit from '../pages/Edit.js';
import Delete from '../pages/Delete.js';
import Login from '../pages/Login.js';
import UserInfor from '../pages/UserInfor.js';

export const publicRoutes = [
    {path:"/", layout:"Layout", component: Home},
    {path:"/list/:name", layout:"Layout", component: List},
    {path:"/add/:name", layout:"Layout", component: Add},
    {path:"/edit/:name", layout:"Layout", component: Edit},
    {path:"/edit/:name/:id", layout:"Layout", component: Edit},
    {path:"/delete/:name", layout:"Layout", component: Delete},
    {path:"/login", layout:null, component: Login},
    {path:"/userinfor", layout:"Layout", component: UserInfor},
];