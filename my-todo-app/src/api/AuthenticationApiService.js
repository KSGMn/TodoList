import { api } from "./ApiClient";

export const JwtAuthenticationService = (username, password) =>
  api.post("/authenticate", {
    username: username,
    password: password,
  });

export const JwtRefreshTokenService = (refreshToken) =>
  api.post("/token/refresh", {
    refreshToken: refreshToken,
  });

export const Logout = () => api.post("/logout");
