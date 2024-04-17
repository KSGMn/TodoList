import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../security/AuthContext";

const HeaderComponent = () => {
  const authContext = useAuth();
  const isAuthenticated = authContext.isAuthenticated;
  const username = authContext.username;

  const logout = () => {
    authContext.logout();
  };
  return (
    <header className="p-2">
      <div className="container">
        <div className="row">
          <nav className="navbar">
            <Link className="fs-4" to="">
              TodoApp
            </Link>
            <ul className="navbar-nav flex-row">
              <li className="nav-item fs-8 px-2">
                <Link className="nav-link" to={`/${username}/todos`}>
                  Todos
                </Link>
              </li>
              {!isAuthenticated && (
                <li className="nav-item fs-8 px-2">
                  <Link className="nav-link" to="/login">
                    Login
                  </Link>
                </li>
              )}
              {isAuthenticated && (
                <li className="nav-item fs-8 px-2">
                  <Link className="nav-link" to="/" onClick={logout}>
                    Logout
                  </Link>
                </li>
              )}

              <li className="nav-item fs-8 px-2">
                <Link className="nav-link" to="/admin">
                  Admin
                </Link>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </header>
  );
};

export default HeaderComponent;
