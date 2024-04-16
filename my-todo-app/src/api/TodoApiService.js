import { api } from "./ApiClient";

export const findAllTodoApi = (username) => api.get(`/${username}/todos`);

export const findByIdTodoApi = (username, id) =>
  api.get(`/${username}/todos/${id}`);

export const addTodoApi = (username, todo) =>
  api.post(`/${username}/todos`, todo);

export const deleteTodoApi = (username, id) =>
  api.delete(`/${username}/todos/${id}`);

export const updateTodoApi = (username, id, todo) =>
  api.put(`/${username}/todos/${id}`, todo);
