import React from "react";
import "../css/Load.css";
function Load() {
    return(
        <div className="load">
            <h1>Watting to load</h1>
            <div className="load-bar-container">
                <div className="load-bar1"></div>
                <div className="load-bar2"></div>
                <div className="load-bar3"></div>
                <div className="load-bar4"></div>
                <div className="load-bar5"></div>
                <div className="load-bar6"></div>
            </div>
        </div>
    )
}

export default Load;