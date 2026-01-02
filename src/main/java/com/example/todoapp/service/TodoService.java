
package com.example.todoapp.service;

import com.example.todoapp.entity.Todo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TodoService {

    public List<Todo> getAll() {
        // Hard-coded sample data for now
        return Arrays.asList(
                new Todo(1L, "Learn Spring Boot", true),
                new Todo(2L, "Build Todo API", false),
                new Todo(3L, "Add Security later", false)
        );
    }

    public Todo getById(Long id) {
        return getAll()
                .stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .orElse(null); // or throw exception
    }

}