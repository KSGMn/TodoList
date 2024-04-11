package com.example.mytodo.todo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mytodo.todo.entities.Todo;
import com.example.mytodo.todo.repository.TodoRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor // Repository와 Service에 대한 생성자 주입

public class todoJpaController {

    private TodoRepository todorepository;

    @GetMapping("/{username}/todos")
    public List<Todo> findAllTodos(@PathVariable String username) {

        return todorepository.findByUsername(username);
    }

    @GetMapping("/{username}/todos/{id}")
    public Todo findOneTodo(@PathVariable String username, @PathVariable int id) {
        return todorepository.findById(id).get();
    }

    @PostMapping("/{username}/todos")
    // 클라이언트가 경로로 POST 요청을 보낼 때, 요청의 본문에 포함된
    // Todo 객체의 데이터(ex: JSON데이터)를 Java의 Todo 객체로 변환하여 todo 파라미터에 바인딩하도록 함
    public ResponseEntity<Todo> createTodo(@PathVariable String username, @Valid @RequestBody Todo todo) {
        todo.setUsername(username);
        todo.setTargetDate(LocalDate.now());
        Todo createTodo = todorepository.save(todo);

        return ResponseEntity.ok(createTodo);
    }

    @DeleteMapping("/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable int id) {

        todorepository.deleteById(id);
        return ResponseEntity.noContent().build();// 응답코드 204를 반홚
    }

    @PutMapping("/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable int id,
            @RequestBody Todo todo) {
        Todo updateTodo = todorepository.save(todo);
        return ResponseEntity.ok(updateTodo);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{username}/admin")
    public String adminPage(@PathVariable String username) {
        return "관리자 페이지";
    }
}
