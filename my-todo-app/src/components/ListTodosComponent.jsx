import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../security/AuthContext";
import { findAllTodoApi } from "../api/TodoApiService";

const ListTodosComponent = () => {
  const [todos, setTodos] = useState();

  const authContext = useAuth();
  const username = authContext.username;
  const navigate = useNavigate();

  //useEffect로 refreshTodo를 호출해주자
  useEffect(() => refreshTodo(), []);

  const refreshTodo = () => {
    findAllTodoApi(username)
      .then((response) => {
        console.log(response.data);
        setTodos(response.data);
      })
      .catch((error) => {
        console.log(error);
        if (error.response && error.response.status === 401) {
          authContext.logout();
          navigate("/login");
        }
      });
  };

  // return을 넣어주지 않으면 코드가 onClick 했을 때 실행되는게 아니라 바로 실행되어버린다
  const updateTodo = (username, id) => {
    return () => {
      navigate(`/${username}/todos/${id}`);
    };
  };

  const addTodo = (username) => {
    return () => {
      navigate(`/${username}/todos/-1`);
    };
  };

  return (
    <div className="container">
      <h1 className="text-center">List Todos</h1>
      <table className="table">
        <thead>
          <tr>
            <th className="col-2">제목</th>
            <th className="col-6">내용</th>
            <th className="col-2 text-center">작성일</th>
            <th className="col-1 text-center">수정</th>
            <th className="col-1 text-center">삭제</th>
          </tr>
        </thead>
        <tbody className="mb-2">
          {todos &&
            todos.map((todo) => (
              <tr key={todo.id}>
                <td className="col-2 vertical-center">{todo.title}</td>
                <td className="col-6 vertical-center">{todo.contents}</td>
                <td className="col-2 text-center vertical-center">
                  {todo.targetDate}
                </td>
                <td className="col-1 text-center">
                  <button
                    className="btn btn-primary"
                    onClick={updateTodo(todo.username, todo.id)}
                  >
                    수정
                  </button>
                </td>
                <td className="col-1 text-center">
                  <button className="btn btn-warning">삭제</button>
                </td>
              </tr>
            ))}
        </tbody>
      </table>
      <div className="text-center">
        <button className="btn btn-success" onClick={addTodo(username)}>
          글쓰기
        </button>
      </div>
    </div>
  );
};

export default ListTodosComponent;
