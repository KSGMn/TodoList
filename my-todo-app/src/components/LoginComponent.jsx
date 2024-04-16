import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../security/AuthContext";

const LoginComponent = () => {
  const [username, setUsername] = useState("User");
  const [password, setPassword] = useState("");

  const [showErrorMessage, setShowErrorMessage] = useState(false);
  const navigate = useNavigate();
  const authContext = useAuth();

  function handleUsernameChange(event) {
    //console.log(event);
    ///console.log(event.target.value);
    setUsername(event.target.value);
  }

  const handlePasswordChange = (event) => {
    //console.log(event.target.value);
    setPassword(event.target.value);
  };

  const handleSubmit = async () => {
    // console.log(username);
    // console.log(password);
    if (await authContext.login(username, password)) {
      navigate("/");
    } else {
      setShowErrorMessage(true);
    }
  };
  return (
    <div className="Login d-flex justify-content-center">
      <div className="col-3">
        <h1 className="text-center mb-5">Login Page</h1>
        {/* showErrorMessage가 true면 뒤에 값 반환 */}
        {showErrorMessage && (
          <div className="errorMessage mb-3 text-center">인증 실패</div>
        )}
        <div className="LoginForm">
          <div className="container">
            <div className="mb-3">
              <label className="mb-2 fs-8" htmlFor="">
                User name
              </label>
              <input
                className="form-control"
                type="text"
                name="username"
                value={username}
                onChange={handleUsernameChange}
              />
            </div>
            <div className="mb-3">
              <label className="mb-2 fs-8" htmlFor="">
                PassWord
              </label>
              <input
                type="password"
                className="form-control"
                name="password"
                value={password}
                onChange={handlePasswordChange}
              />
            </div>
            <button
              type="button"
              name="login"
              className="btn btn-primary form-control"
              onClick={handleSubmit}
            >
              로그인
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginComponent;
