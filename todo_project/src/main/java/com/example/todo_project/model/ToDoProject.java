package com.example.todo_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "todo_project")
public class ToDoProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "todo", nullable = false, length = 200)
    private String todo;
    
    @JsonProperty("uname")
    @Column(name = "u_name", nullable = true, length = 200)
    private String uname;
    
    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private ToDoCategory category_id;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getUser() {
        return uname;
    }

    public void setUser(String uname) {
        this.uname = uname;
    }

    public ToDoCategory getCategory(){
        return category_id;
    }

    public void setCategory(ToDoCategory category){
        this.category_id = category;
    }

    // toStringメソッド
    @Override
    public String toString() {
        return "ToDoProject{" +
                "id=" + id +
                ", todo='" + todo + '\'' +
                ", category=" + (category_id != null ? category_id.getCategory() : "null") +
                ", uname='" + uname + '\'' +
                '}';
    }

}
