package com.example.todo.service;

import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Specification<User> searchByUsername(String key) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + key + "%");
    }

    public Page<User> getAll(Pageable pageable, String username) {
        Specification<User> where = null;
        if(!StringUtils.isEmpty(username))
            where = searchByUsername(username);
        return repository.findAll(where, pageable);
    }

    public List<User> getUserByUsername(String username) {
        return repository.searchUserByUsernameContainingIgnoreCase(username);
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public User editUser(Long id, User user) {
        User newUser = repository.findById(id).get();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setBirthday(user.getBirthday());
        return repository.save(newUser);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
