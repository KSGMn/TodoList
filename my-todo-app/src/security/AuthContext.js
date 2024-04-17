import { createContext, useContext, useEffect, useState } from "react";
import {
  JwtAuthenticationService,
  JwtRefreshTokenService,
  Logout,
} from "../api/AuthenticationApiService";
import { api } from "../api/ApiClient";
import Cookies from "js-cookie";
import { Navigate } from "react-router-dom";

//컨텍스트 생성
export const Authcontext = createContext();

export const useAuth = () => useContext(Authcontext);

export default function AuthProvider({ children }) {
  const [isAuthenticated, setAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true); // 로딩 상태 추가

  const [username, setUsername] = useState(null);

  //const [token, setToken] = useState(null);

  if (!api.interceptors.request.handlers.length) {
    //인터셉터는 여기서 실행해주자 login안에서 실행했더니 새로고침 시 작동하지 않는다 (로그인할때만 작동하기때문)
    api.interceptors.request.use((config) => {
      console.log("토큰 추가");
      const accessToken = Cookies.get("accessToken"); //쿠키에서 액세스 토큰 가져오기
      if (accessToken) {
        config.headers.Authorization = `Bearer ${accessToken}`;
      }
      return config;
    }); //토큰을 헤더에 추가
  }

  const login = async (username, password) => {
    console.log("로그인 시도");
    try {
      const response = await JwtAuthenticationService(username, password);
      console.log("API 응답 상태:", response.status);
      if (response.status === 200) {
        const accessToken = response.data.accessToken;
        const refreshToken = response.data.refreshToken;
        console.log("응답 처리 중...");
        localStorage.setItem("isAuthenticated", "true");
        localStorage.setItem("username", username);
        setAuthenticated(true);
        setUsername(username);
        console.log("쿠키 생성중..");
        Cookies.set("accessToken", accessToken, { expires: 1 / 24 / 60 }); //쿠키명, 쿠키값, 유효기간
        Cookies.set("refreshToken", refreshToken, { expires: 7 }); //쿠키명, 쿠키값, 유효기간
        console.log("쿠키 생성 완료");
        //setToken(accessToken);
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

  useEffect(() => {
    const authStatus = localStorage.getItem("isAuthenticated");
    setAuthenticated(authStatus === "true");
    const storedUsername = localStorage.getItem("username");
    setUsername(storedUsername);
    setLoading(false); // 데이터 로드가 완료되면 로딩 상태 업데이트
    const refreshToken = Cookies.get("refreshToken");

    // 타이머 설정
    const refreshTokenTimer = setInterval(async () => {
      try {
        const response = await JwtRefreshTokenService(refreshToken);
        Cookies.set("accessToken", response.data.accessToken, {
          expires: 1 / 24 / 60, // 1분
        });
        console.log("리프레시 토큰 사용");
        console.log(`새로운 accessToken 발급 ${response.data.accessToken}`);
      } catch (error) {
        console.log("리프레시 토큰 사용 실패: ", error);
      }
    }, 50000); // 50초 후 실행

    // 클린업 함수: 컴포넌트 언마운트 시 실행
    return () => {
      clearInterval(refreshTokenTimer);
      console.log("인터벌 정리됨");
    };
  }, [isAuthenticated]); // 의존성 배열에 로그인 상태 포함

  const logout = async () => {
    try {
      await Logout();
      localStorage.removeItem("isAuthenticated"); // 로컬 스토리지에서 인증 상태 제거
      localStorage.removeItem("username"); // 로컬 스토리지에서 인증 상태 제거
      setAuthenticated(false);
      //setToken(null);
      setUsername(null);
      //setToken(null);
      // 쿠키 삭제
      Cookies.remove("accessToken", { path: "/" }); // 액세스 토큰 삭제
      Cookies.remove("refreshToken", { path: "/" }); // 리프레시 토큰 삭제
      console.log("로그아웃 완료");
    } catch (error) {
      console.log("로그아웃 에러: ", error);
    }
  };

  return (
    <Authcontext.Provider
      value={{ isAuthenticated, login, logout, username, loading }}
    >
      {children}
    </Authcontext.Provider>
  );
}
