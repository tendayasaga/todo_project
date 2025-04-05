package com.example.todo_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo_project.model.ToDoCategory;

public interface ToDoCategoryRepository extends JpaRepository<ToDoCategory, Integer> {
    
}

