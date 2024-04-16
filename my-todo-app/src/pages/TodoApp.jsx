import React from "react";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import HeaderComponent from "../layout/Header";
import FooterComponent from "../layout/Footer";
import LoginComponent from "../components/LoginComponent";
import "./TodoApp.css";
import ListTodosComponent from "../components/ListTodosComponent";
import AuthProvider, { useAuth } from "../security/AuthContext";
import WelcomeComponent from "../components/WelcomeComponent";
import TodoComponent from "../components/TodoComponent";

const AuthenticatedRoute = ({ children }) => {
  const authContext = useAuth();

  if (authContext.isAuthenticated) return children;

  return <Navigate to="/login" />;
};

const TodoApp = () => {
  return (
    <div className="TodoApp">
      <AuthProvider>
        <BrowserRouter>
          <HeaderComponent />
          <Routes>
            <Route
              path="/"
              element={
                <AuthenticatedRoute>
                  <WelcomeComponent />
                </AuthenticatedRoute>
              }
            />
            <Route
              path="/:username/todos"
              element={
                <AuthenticatedRoute>
                  <ListTodosComponent />
                </AuthenticatedRoute>
              }
            />
            <Route
              path="/:username/todos/:id"
              element={
                <AuthenticatedRoute>
                  <TodoComponent />
                </AuthenticatedRoute>
              }
            />
            <Route path="/login" element={<LoginComponent />} />
          </Routes>
          <FooterComponent />
        </BrowserRouter>
      </AuthProvider>
    </div>
  );
};

export default TodoApp;
