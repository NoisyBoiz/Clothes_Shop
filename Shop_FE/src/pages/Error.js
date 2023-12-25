import { useRouteError,Link } from "react-router-dom";
import {BiSearchAlt} from "react-icons/bi";
import "../css/ErrorPage.css";
export default function ErrorPage() {
  const error = useRouteError();

  return (
    <div className="error-page">
      <div className="error-page-box">
        <BiSearchAlt/>
        <p>
          <h1>Oops!</h1>
          Sorry, an unexpected error has occurred.
        </p>
        <h3>{error.statusText || error.message}</h3>
        <Link to={"/"}> Back to Home</Link>
      </div>
    </div>
  );
}