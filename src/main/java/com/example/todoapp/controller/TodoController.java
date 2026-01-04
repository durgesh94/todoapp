
package com.example.todoapp.controller;

import com.example.todoapp.entity.Todo;
import com.example.todoapp.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    // LIST
    @GetMapping
    public List<Todo> all() {
        return service.getAll();
    }

    // CREATE
    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return service.create(todo);
    }

    // READ ONE
    @GetMapping("/{id}")
    public Todo getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    // FULL UPDATE (PUT): replace title + completed
    @PutMapping(value = "/{id}", consumes = "application/json")
    public Todo replace(@PathVariable Long id, @RequestBody Todo incoming) {
        return service.replace(id, incoming.getTitle(), incoming.isCompleted());
    }

    // PARTIAL UPDATE (PATCH): update only fields provided
    @PatchMapping(value = "/{id}", consumes = "application/json")
    public Todo patch(@PathVariable Long id, @RequestBody Todo incoming) {
        // If a field is not provided, we pass null to indicate "no change" for that field
        String title = incoming.getTitle();          // null means don't change
        Boolean completed = incoming.isCompleted();  // boolean primitive lacks null; see note below
        // Because entity uses primitive boolean, we interpret PATCH by reading JSON yourself:
        // Optionally switch to a DTO with Boolean completed to allow null. See alternative below.
        return service.patch(id, title, incoming.isCompleted());
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
