package com.example.todo_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo_project.model.ToDoProject;

public interface ToDoProjectRepository extends JpaRepository<ToDoProject, Integer> {
    // 追加のクエリメソッドが必要な場合はここに定義します
    List<ToDoProject> findByUname(String uname);
}

