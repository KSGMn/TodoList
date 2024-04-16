import { api } from "./ApiClient";

export const JwtAuthenticationService = (username, password) =>
  api.post("/authenticate", {
    username: username,
    password: password,
  });
