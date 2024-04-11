package com.example.mytodo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mytodo.user.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
