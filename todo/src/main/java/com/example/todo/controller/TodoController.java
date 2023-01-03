package com.example.todo.controller;

import com.example.todo.entity.Todo;
import com.example.todo.exception.EntityNotFoundException;
import com.example.todo.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping("/todos")
    public ResponseEntity<?> getAll(@RequestParam(name = "title", defaultValue = "", required = false) String title) {
        if(!title.isEmpty()) {
            return ResponseEntity.ok(service.getTodosByTitle(title));
        } else {
            return ResponseEntity.ok(service.getAll());
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getByTitle(@PathVariable Long id) throws EntityNotFoundException {
        return ResponseEntity.ok(service.getTodoById(id));
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody Todo todo){
        return ResponseEntity.ok(service.createTodo(todo));
    }

    @PutMapping("/todos")
    public ResponseEntity<?> editTodo(@RequestBody Todo todo) throws EntityNotFoundException{
        return ResponseEntity.ok(service.editTodo(todo));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id) throws EntityNotFoundException {
        service.deleteTodo(id);
        return ResponseEntity.ok("OK!");
    }
}
