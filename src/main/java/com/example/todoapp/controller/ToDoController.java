package com.example.todoapp.controller;

import com.example.todoapp.entity.ToDo;
import com.example.todoapp.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToDoController {

    private final TodoService todoService;

    public ToDoController(TodoService todoService) {
        this.todoService = todoService;
    }
    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(@RequestParam(required = false) String title) {
        List<ToDo> toDoList;
        if(title.isEmpty()) {
            toDoList = todoService.getAllTodos();
        } else {
            toDoList = todoService.getTodosByTitle(title);
        }
        return ResponseEntity.ok(toDoList);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getTodosById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getToDoById(id));
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody ToDo toDo) {
        return ResponseEntity.ok(todoService.createTodo(toDo));
    }

    @PutMapping("/todos")
    public ResponseEntity<?> editTodo(@RequestBody ToDo toDo) {
        return ResponseEntity.ok(todoService.editTodo(toDo));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) {
        return ResponseEntity.ok("OK");
    }
}
