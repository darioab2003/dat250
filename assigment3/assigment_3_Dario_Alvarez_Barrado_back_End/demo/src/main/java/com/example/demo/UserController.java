package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PollManager pollManager;

    @GetMapping
    public ResponseEntity<Map<String, User>> getAllUsers() {
        return ResponseEntity.ok(pollManager.getUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        String id = user.getUsername();
        pollManager.addUser(id, user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = pollManager.getUsers().get(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        pollManager.getUsers().put(id, updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        pollManager.getUsers().remove(id);
        return ResponseEntity.noContent().build();
    }
}
