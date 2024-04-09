package com.example.mytodo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.example.mytodo.entities.Todo;

import jakarta.validation.Valid;

@Service
public class todoService {

    private static List<Todo> todos = new ArrayList<>();

    private static int todosCount = 0;

    static {
        todos.add(new Todo(todosCount++, "user", "Title1", "Contents1", LocalDate.now()));
        todos.add(new Todo(todosCount++, "user", "Title2", "Contents2", LocalDate.now()));
        todos.add(new Todo(todosCount++, "user", "Title3", "Contents3", LocalDate.now()));
    }

    public List<Todo> findByUsername(String username) {
        // todo.getUsername().equalsIgnoreCase(username) 조건을 만족하는 Todo 객체만 필터링
        // .stream().filter(predicate) 부분은 스트림 내의 각 요소에 대해 predicate를 적용하고, 조건을 만족하는
        // 요소들만을 모아 새로운 스트림을 생성
        Predicate<Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(username);
        return todos.stream().filter(predicate).toList();
    }

    public Todo findById(int id) {
        Predicate<Todo> predicate = todo -> todo.getId() == id;
        Todo todo = todos.stream().filter(predicate).findFirst().get();

        return todo;
    }

    public Todo createTodo(String username, String title, String contents, LocalDate targetDate) {

        Todo todo = new Todo(todosCount++, username, title, contents, LocalDate.now());
        todos.add(todo);
        return todo;

    }

    public void deleteTodo(int id) {

        Predicate<Todo> predicate = todo -> todo.getId() == id;
        todos.removeIf(predicate);

    }

    /*
     * @Valid 사용을 위해 의존성을 추가해주자
     * <dependency>
     * <groupId>org.springframework.boot</groupId>
     * <artifactId>spring-boot-starter-validation</artifactId>
     * </dependency>
     */
    public Todo updateTodo(@Valid Todo todo) {
        deleteTodo(todo.getId());
        todos.add(todo);

        return todo;
    }

}
