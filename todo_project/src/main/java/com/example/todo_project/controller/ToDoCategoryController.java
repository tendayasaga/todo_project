package com.example.todo_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo_project.model.ToDoCategory;
import com.example.todo_project.repository.ToDoCategoryRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/categories")
public class ToDoCategoryController {

    @Autowired
    ToDoCategoryRepository categoryRepository;

    @GetMapping
    public List<ToDoCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ToDoCategory getCategoryById(@PathVariable Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ToDoCategory createCategory(@RequestBody ToDoCategory category) {
        return categoryRepository.save(category);
    }
}

