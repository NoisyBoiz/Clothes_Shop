import * as React from "react";
import * as ReactDOM from "react-dom/client";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {publicRoutes} from './routers/routes.js';
import Layout from './layouts/index.js';
import ErrorPage from './pages/Error.js';
const routes = [] 
publicRoutes.forEach((route)=>{
    const LayoutPage = route.layout===null?React.Fragment:Layout;
    routes.push(
      {path:route.path, element:<LayoutPage><route.component/></LayoutPage>, errorElement: <ErrorPage />}
    )
  }
)

function Main(){
  return(
    <React.StrictMode>
      <RouterProvider router={createBrowserRouter(routes)}/>
    </React.StrictMode>
  )
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<Main/>);