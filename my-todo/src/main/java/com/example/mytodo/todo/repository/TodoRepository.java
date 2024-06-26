package com.example.mytodo.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mytodo.todo.entities.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByUsername(String username);
}
