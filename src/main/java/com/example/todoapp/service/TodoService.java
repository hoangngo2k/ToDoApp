package com.example.todoapp.service;

import com.example.todoapp.entity.ToDo;
import com.example.todoapp.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private ToDoRepository toDoRepository;

    public List<ToDo> getAllTodos() {
        return toDoRepository.findAll();
    }

    public List<ToDo> getTodosByTitle(String title) {
        return toDoRepository.findToDoByTitleLike(title);
    }

    public ToDo getToDoById(Long id) {
        return toDoRepository.findById(id).get();
    }

    public ToDo createTodo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public ToDo editTodo(ToDo toDo) {
        ToDo newToDo = toDoRepository.findById(toDo.getId()).get();
        newToDo.setTitle(toDo.getTitle());
        return toDoRepository.save(newToDo);
    }

    public void deleteTodo(Long id) {
        toDoRepository.deleteById(id);
    }
}
