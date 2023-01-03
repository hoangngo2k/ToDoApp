package com.example.todo.dao;

import java.io.Serial;
import java.io.Serializable;

public class SearchForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String username;

    public SearchForm() {
        this.username = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
