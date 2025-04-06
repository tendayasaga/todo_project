package com.example.todo_project.controller;

import java.util.List;
import java.util.Map;

//ログ出力用
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo_project.model.ToDoCategory;
import com.example.todo_project.model.ToDoProject;
import com.example.todo_project.repository.ToDoCategoryRepository;
import com.example.todo_project.repository.ToDoProjectRepository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/todos")
public class ToDoProjectController {

    @Autowired
    ToDoProjectRepository todoRepository;

    @Autowired
    ToDoCategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<ToDoProject>> getAllTodos() {
        List<ToDoProject> todos = todoRepository.findAll();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<ToDoProject>> getTodosByUsername(@PathVariable String username) {
        List<ToDoProject> todos = todoRepository.findByUname(username);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<ToDoProject> getTodoById(@PathVariable Integer id) {
        return todoRepository.findById(id)
                .map(todo -> new ResponseEntity<>(todo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ToDoProject> createTodo(@RequestBody ToDoProject todo) {
        Logger logger = LoggerFactory.getLogger(ToDoProjectController.class);
        logger.info("Received request to create todo: {}", todo);

        // todoがnullでないことを確認
        if (todo == null) {
            logger.error("Received null todo object");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // categoryがnullでないことを確認
        if (todo.getCategory() == null || todo.getCategory().getId() == null) {
            logger.error("Category is null or category ID is null for todo: {}", todo);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ToDoCategory category = categoryRepository.findById(todo.getCategory().getId()).orElse(null);
        if (category != null) {
            todo.setCategory(category);

            // unameがnullまたは空でないことを確認
            if (todo.getUser() == null || todo.getUser().isEmpty()) {
                logger.error("Uname is null or empty for todo: {}", todo);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            todo.setUser(todo.getUser()); // 必要に応じて他のフィールドも設定
            ToDoProject savedTodo = todoRepository.save(todo);
            logger.info("Todo created successfully: {}", savedTodo);
            return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
        }
        logger.warn("Category not found for todo: {}", todo);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoProject> updateTodo(@PathVariable Integer id, @RequestBody ToDoProject todoDetails) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setTodo(todoDetails.getTodo());
                    ToDoCategory category = categoryRepository.findById(todoDetails.getCategory().getId()).orElse(null);
                    if (category != null) {
                        todo.setCategory(category);
                    }
                    ToDoProject updatedTodo = todoRepository.save(todo);
                    return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Integer id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/completion")
    public ResponseEntity<ToDoProject> updateCompletionStatus(@PathVariable Integer id, @RequestBody Map<String, Boolean> completedStatus) {
        return todoRepository.findById(id)
            .map(todo -> {
                todo.setCompleted(completedStatus.get("completed"));  // completedの値を取得
                ToDoProject updatedTodo = todoRepository.save(todo);
                return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}


