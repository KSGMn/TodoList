import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../security/AuthContext";
import {
  addTodoApi,
  findByIdTodoApi,
  updateTodoApi,
} from "../api/TodoApiService";

const TodoComponent = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const authContext = useAuth();
  const username = authContext.username;

  const [todo, setTodo] = useState({ title: "", contents: "" });
  const [errors, setErrors] = useState();

  useEffect(() => {
    if (id !== "-1") {
      findByIdTodoApi(username, id)
        .then((response) => {
          setTodo({
            title: response.data.title,
            contents: response.data.contents,
            targetDate: response.data.targetDate,
          });
        })
        .catch((error) => console.log(error));
    }
  }, [id, username]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setTodo((prev) => ({ ...prev, [name]: value }));
  };

  const validate = () => {
    let errors = {};
    if (todo.title.length < 5) {
      errors.title = "5글자 이상이어야 합니다";
    }
    return errors;
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const formErrors = validate();
    if (Object.keys(formErrors).length === 0) {
      const todoToSave = {
        id: id,
        username: username,
        title: todo.title,
        contents: todo.contents,
        targetDate: todo.targetDate,
      };
      if (id === "-1") {
        addTodoApi(username, todoToSave)
          .then(() => navigate(`/${username}/todos`))
          .catch((error) => console.log(error));
      } else {
        updateTodoApi(username, id, todoToSave)
          .then(() => navigate(`/${username}/todos`))
          .catch((error) => console.log(error));
      }
    } else {
      setErrors(formErrors);
    }
  };

  return (
    <div className="d-flex justify-content-center">
      <div className="col">
        <h1 className="text-center mb-3">Enter Todo Details</h1>
        <form onSubmit={handleSubmit} className="container col-5">
          {/* {errors.title && (
          <div className="alert alert-warning">{errors.title}</div>
        )} */}
          <fieldset className="form-group">
            <label>Title</label>
            <input
              type="text"
              className="form-control"
              name="title"
              value={todo.title}
              onChange={handleChange}
            />
          </fieldset>
          <fieldset className="form-group">
            <label>Contents</label>
            <input
              type="text"
              className="form-control"
              name="contents"
              value={todo.contents}
              onChange={handleChange}
            />
          </fieldset>
          <div className="text-center">
            <button className="btn btn-success m-3" type="submit">
              Save
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default TodoComponent;
