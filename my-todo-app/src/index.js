import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  /*<React.StrictMode> 태그로 <app/>이 감싸져있으면
개발모드에서 (개발 단계시 오류를 잘 잡기위해) 두 번씩 렌더링됩니다. */
  //<React.StrictMode>
  <App />
  //</React.StrictMode>
);
