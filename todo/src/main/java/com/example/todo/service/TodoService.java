package com.example.todo.service;
import com.example.todo.exception.EntityNotFoundException;
import com.example.todo.repository.TodoRepository;
import com.example.todo.entity.Todo;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getAll() {
        return repository.findAll();
    }

    public List<Todo> getTodosByTitle(String title) {
        return repository.searchTodoByTitleContainingIgnoreCase(title);
    }

    public Todo getTodoById(Long id) {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Todo.class, "id", id.toString()));
        return todo;
    }

    public Todo createTodo(Todo todo) {
        return repository.save(todo);
    }

    public Todo editTodo(Todo todo) {
        Todo newTodo = repository.findById(todo.getId()).get();
        newTodo.setTitle(todo.getTitle());
        return repository.save(newTodo);
    }

    public void deleteTodo(Long id) {
        repository.deleteById(id);
    }
}
