import { createContext, useContext, useState } from "react";
import { JwtAuthenticationService } from "../api/AuthenticationApiService";
import { api } from "../api/ApiClient";

//컨텍스트 생성
export const Authcontext = createContext();

export const useAuth = () => useContext(Authcontext);

export default function AuthProvider({ children }) {
  const [isAuthenticated, setAuthenticated] = useState(false);

  const [username, setUsername] = useState(null);

  const [token, setToken] = useState(null);

  const login = async (username, password) => {
    console.log("로그인 시도");
    try {
      const response = await JwtAuthenticationService(username, password);
      console.log("API 응답 상태:", response.status);
      if (response.status === 200) {
        const accessToken = `Bearer ${response.data.accessToken}`;
        console.log("응답 처리 중...");
        setAuthenticated(true);
        setUsername(username);
        setToken(accessToken);
        if (!api.interceptors.request.handlers.length) {
          console.log("인터셉터 추가");
          api.interceptors.request.use((config) => {
            console.log("토큰 추가");
            config.headers.Authorization = accessToken;
            return config;
          }); //토큰을 헤더에 추가
        }
        return true;
      } else {
        logout();
        return false;
      }
    } catch (error) {
      logout();
      return false;
    }
  };

  const logout = () => {
    setAuthenticated(false);
    setToken(null);
    setUsername(null);
  };

  return (
    <Authcontext.Provider
      value={{ isAuthenticated, login, logout, username, token }}
    >
      {children}
    </Authcontext.Provider>
  );
}
