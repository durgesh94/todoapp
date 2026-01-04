
package com.example.todoapp.service;

import com.example.todoapp.entity.Todo;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class TodoService {

    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public List<Todo> getAll() {
        return repo.findAll();
    }

    public Todo create(Todo todo) {
        return repo.save(todo);
    }



    public Todo getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Todo with id " + id + " not found"
                ));

    }

                        /**
                         * PUT semantics: replace all mutable fields.
                         */
    public Todo replace(Long id, String title, boolean completed) {
        Todo existing = getById(id);
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        existing.setTitle(title);
        existing.setCompleted(completed);
        return repo.save(existing);
    }

    /**
     * PATCH semantics: update only fields that are present (non-null).
     * Because your entity uses primitive boolean, completed cannot be null.
     * Consider switching to a DTO with Boolean for PATCH (see alternative below).
     */
    public Todo patch(Long id, String title, boolean completedFlagFromPayload) {
        Todo existing = getById(id);

        // Update title if provided (non-null)
        if (title != null) {
            if (title.isBlank()) throw new IllegalArgumentException("title must not be blank");
            existing.setTitle(title);
        }

        // With primitive boolean, we cannot differentiate "absent" vs "false".
        // If you want to change only when provided, use a DTO with Boolean (see below).
        existing.setCompleted(completedFlagFromPayload);

        return repo.save(existing);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Todo " + id + " not found");
        }
        repo.deleteById(id);
    }

}
