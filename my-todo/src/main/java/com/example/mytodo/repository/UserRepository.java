package com.example.mytodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mytodo.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
